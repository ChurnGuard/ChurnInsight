import joblib
import pandas as pd
from pathlib import Path

# Paths

PROJECT_ROOT = Path(__file__).resolve().parents[1]
MODEL_PATH = PROJECT_ROOT / "models" / "churn_model.joblib"
INPUT_CSV = PROJECT_ROOT / "data" / "rf_v1_baseline_train.csv"
OUTPUT_CSV = PROJECT_ROOT / "data" / "customers_with_churn_prob.csv"

# Load model bundle

bundle = joblib.load(MODEL_PATH)
model = bundle["model"]
features = bundle["features"]

print(f"✅ Model loaded: {bundle['model_name']}")
print(f"🔢 Expected features: {len(features)}")

# Load dataset

df = pd.read_csv(INPUT_CSV)
print(f"📄 Charged customers: {len(df)}")

# Validate columns

missing_cols = set(features) - set(df.columns)
if missing_cols:
    raise ValueError(f"❌ Missing columns in CSV: {missing_cols}")

# Predict churn

X = df[features]
df["churn_prob"] = model.predict_proba(X)[:, 1]

# Save

df.to_csv(OUTPUT_CSV, index=False)

print(f"\n✅ CSV generated with churn_prob:")
print(OUTPUT_CSV)
