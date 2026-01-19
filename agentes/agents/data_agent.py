# agents/data_agent.py

import pandas as pd
from typing import List, Dict

CSV_PATH = "Agentes/data/customers_with_churn_prob.csv"


def load_customers() -> List[Dict]:

    df = pd.read_csv(CSV_PATH)
    
    df["customer_id"] = [f"CUST_{i+1}" for i in range(len(df))]

    customers = df.to_dict(orient="records")

    return customers
