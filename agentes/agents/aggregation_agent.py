# agents/aggregation_agent.py

from collections import Counter
from typing import List, Dict, Any


def aggregate_decisions(decisions: List[Dict[str, Any]]) -> Dict[str, Any]:
    """
    It aggregates individual decisions into metrics useful for managers.
    It doesn't execute actions.
    It's unaware of channels.
    """

    aggregation = {
        "total_customers": len(decisions),
        "decision_counts": Counter(),
        "urgency_counts": Counter(),
        "critical_churn": 0,
        "high_churn": 0,
    }

    for d in decisions:
        decision_type = d.get("decision_type")
        urgency = d.get("urgency")
        churn_prob = d.get("churn_prob")

        # Count by decision type
        if decision_type:
            aggregation["decision_counts"][decision_type] += 1

        # Count by urgency
        if urgency:
            aggregation["urgency_counts"][urgency] += 1

        # Churn severity
        if churn_prob is not None:
            if churn_prob >= 0.8:
                aggregation["critical_churn"] += 1
            elif churn_prob >= 0.6:
                aggregation["high_churn"] += 1

    return aggregation
