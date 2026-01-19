# main.py

from agents.data_agent import load_customers
from agents.action_agent import execute_actions

def run_churn_pipeline():
    # Load data
    customers = load_customers()  

    # Execute all actions (including decisions)
    results = execute_actions(customers)

    return results

if __name__ == "__main__":
    run_churn_pipeline()

