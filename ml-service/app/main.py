from fastapi import FastAPI
from app.schemas.prediction import PredictionRequest, PredictionResponse
import random

app = FastAPI(title="ChurnInsight ML Service", version="1.0")


@app.get("/")
def health_check():
    return {"status": "ok", "service": "ml-service-v1"}


@app.post("/predict/{customer_id}", response_model=PredictionResponse)
def predict_churn(customer_id: str, data: PredictionRequest):
    """
    Endpoint temporal para simular la prediccion del modelo.
    """

    # LOGICA FALSA DE PREDICCION (se debe modificar por el modelo real, cuando el mismo este listo)
    # simulamos que si hace mas de 60 dias no compra (recency > 60)
    # y su frecuencia es baja, es probable que haga Churn.

    risk_score = 0.0

    if data.recency > 60:
        risk_score += 0.4

    if data.frequency < 3:
        risk_score += 0.3

    # si el cliente es un cazaofertas, el riesgo es alto si no hay ninguna ofertas
    if data.discount_usage_rate > 0.5:
        risk_score += 0.2

    # Anadimos un poco de aleatoriedad para pruebas
    noise = random.uniform(-0.1, 0.1)
    final_prob = min(max(risk_score + noise, 0.0), 1.0)

    is_churn = 1 if final_prob > 0.5 else 0

    return {
        "customer_id": customer_id,
        "prediction": is_churn,
        "probability": round(final_prob, 4),
        "status": "success",
    }
