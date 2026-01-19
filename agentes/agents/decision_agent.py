# agents/decision_agent.py

from typing import Dict, Any, List
from datetime import datetime

# Decision matrix with business rules

DECISION_MATRIX = [
    # HIGH RISK + HIGH VALUE (CRITICAL)
    {"churn_min": 0.7, "value": "HIGH", "flags": ["INACTIVITY_RISK"],
     "action": "Llamada urgente + beneficio exclusivo + gestor de cuenta dedicado",
     "urgency": "CRITICAL"},
    {"churn_min": 0.6, "value": "HIGH", "flags": ["FINANCIAL_RISK"],
     "action": "Reunión con gestor + plan de valor personalizado + descuento estratégico temporal",
     "urgency": "CRITICAL"},
    {"churn_min": 0.6, "value": "HIGH", "flags": ["PROMO_ABUSE"],
     "action": "Llamada de retención + migración a programa de fidelización premium sin descuentos",
     "urgency": "HIGH"},

    # HIGH RISK + MEDIUM VALUE
    {"churn_min": 0.6, "value": "MEDIUM", "flags": ["INACTIVITY_RISK"],
     "action": "Email personalizado + cupón de reactivación + recordatorio de beneficios",
     "urgency": "HIGH"},
    {"churn_min": 0.6, "value": "MEDIUM", "flags": ["FINANCIAL_RISK"],
     "action": "Email automatizado + oferta de financiamiento/cuotas + productos de entrada",
     "urgency": "HIGH"},
    {"churn_min": 0.6, "value": "MEDIUM", "flags": ["PROMO_ABUSE"],
     "action": "Email con programa de puntos + beneficios no monetarios",
     "urgency": "MEDIUM"},

    # HIGH RISK + LOW VALUE
    {"churn_min": 0.6, "value": "LOW", "flags": ["INACTIVITY_RISK"],
     "action": "Email automatizado de reactivación + descuento moderado",
     "urgency": "MEDIUM"},
    {"churn_min": 0.5, "value": "LOW", "flags": ["FINANCIAL_RISK"],
     "action": "Email con productos económicos + programa de referidos",
     "urgency": "MEDIUM"},
    {"churn_min": 0.6, "value": "LOW", "flags": ["PROMO_ABUSE"],
     "action": "Email con educación de producto + descuento único limitado",
     "urgency": "MEDIUM"},

    # LOW RISK + HIGH VALUE (PROACTIVE FIDELIZATION)
    {"churn_min": 0.0, "churn_max": 0.3, "value": "HIGH", "flags": [],
     "action": "Programa VIP automático + acceso anticipado + eventos exclusivos",
     "urgency": "LOW"},
    {"churn_min": 0.3, "churn_max": 0.5, "value": "HIGH", "flags": [],
     "action": "Check-in proactivo + beneficio sorpresa + solicitud de feedback",
     "urgency": "LOW"},
]

# Derive value and flags from the client

def derive_customer_flags(customer: Dict[str, Any]):
    flags = []

    avg_purchase = float(customer.get("avg_purchase_value", 0))

    if avg_purchase > 300: 
        value = "HIGH"
    elif avg_purchase > 100:
        value = "MEDIUM"
    else:
        value = "LOW"

    # Risk flags
    if int(customer.get("days_since_last_purchase", 0)) > 90:
        flags.append("INACTIVITY_RISK")

    if int(customer.get("income_bracket_Low", 0)) == 1:
        flags.append("FINANCIAL_RISK")

    if int(customer.get("promo_flag", 0)) == 1 or float(customer.get("avg_discount_used", 0)) > 0.4:
        flags.append("PROMO_ABUSE")

    return value, flags

# Main decision function

def decide_action(customer_data: Dict[str, Any]) -> Dict[str, Any]:
    churn_prob = float(customer_data.get("churn_prob", 0))
    value, flags = derive_customer_flags(customer_data)
    customer_data["value"] = value
    customer_data["flags"] = flags

    action_suggestion = "Revisión estándar"
    urgency = "LOW"

    for rule in DECISION_MATRIX:
        churn_min = rule.get("churn_min", 0)
        churn_max = rule.get("churn_max", 1.0)
        if not (churn_min <= churn_prob <= churn_max):
            continue

        if rule["value"] != "ANY" and rule["value"] != value:
            continue

        if all(f in flags for f in rule.get("flags", [])):
            action_suggestion = rule["action"]
            urgency = rule["urgency"]
            break

    if urgency == "CRITICAL":
        decision_type = "REQUIRES_HUMAN_CONTACT"
    elif urgency == "HIGH":
        decision_type = "AUTOMATED_PROMO"
    elif urgency == "MEDIUM":
        decision_type = "LOYALTY_ENGAGEMENT"
    else:
        decision_type = "NO_ACTION"

    return {
        "customer_id": customer_data.get("customer_id"),
        "churn_prob": round(churn_prob, 3),
        "decision_type": decision_type,
        "action_suggestion": action_suggestion,
        "urgency": urgency,
        "value": value,
        "flags": flags
    }

def batch_decisions(customers: List[Dict[str, Any]]):
    actions = []
    summary = {"REQUIRES_HUMAN_CONTACT": 0, "AUTOMATED_PROMO": 0,
               "LOYALTY_ENGAGEMENT": 0, "NO_ACTION": 0}

    for c in customers:
        decision = decide_action(c)
        actions.append(decision)
        summary[decision["decision_type"]] += 1

    # Detect if there is a critical event
    critical_event = next(
        (d for d in actions if d["urgency"] == "CRITICAL"), None
    )

    return {
        "customer_actions": actions,
        "manager_summary": summary,
        "critical_event": critical_event
    }
