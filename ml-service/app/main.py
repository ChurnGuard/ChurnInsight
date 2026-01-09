from fastapi import FastAPI
#from app.schemas.prediction import PredictionRequest
from schemas.prediction import PredictionRequest
import random

app = FastAPI(title="ChurnInsight ML Service", version="1.0")

@app.get("/")
def health_check():
    return {"status": "ok", "service": "ml-service-v1"}

@app.post("/predict", response_model=float)
def predict_churn(data: PredictionRequest):
    """
    Endpoint temporal para simular la prediccion del modelo.
    """
    print(data.age)

    probability = round(random.random(), 2)
    print(probability)

    return float(probability)
