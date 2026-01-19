# utils/flags_utils.py
from typing import Dict

INACTIVITY_THRESHOLD = 377               # days since last purchase
HIGH_VALUE_THRESHOLD = 516               # total sales to consider a high-value customer
FINANCIAL_RISK_THRESHOLD = 0.65          # high churn probability for low-income customers
PROMO_ANOMALY_THRESHOLD_FREQ = 3         # minimum purchase frequency for anomaly
PROMO_ANOMALY_THRESHOLD_DISC = 0.2       # minimum average discount for anomaly

# Funciones

def assign_flags(customer_data: Dict, churn_prob: float) -> Dict[str, bool]:
    """
    Assign risk flags to the customer for the decision agent to use.
    customer_data: dictionary with customer data
    churn_prob: pre-calculated churn probability
    """
    return {
        "INACTIVITY_RISK": customer_data.get("days_since_last_purchase", 0) > INACTIVITY_THRESHOLD,
        "HIGH_VALUE_CUSTOMER": customer_data.get("total_sales", 0) > HIGH_VALUE_THRESHOLD,
        "FINANCIAL_RISK": churn_prob > FINANCIAL_RISK_THRESHOLD
                          and customer_data.get("income_bracket") == "Low",
        "PROMO_ANOMALY": (
            customer_data.get("purchase_frequency", 0) > PROMO_ANOMALY_THRESHOLD_FREQ
            and customer_data.get("avg_discount_used", 0) > PROMO_ANOMALY_THRESHOLD_DISC
        )
    }
