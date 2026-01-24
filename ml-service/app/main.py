from fastapi import FastAPI, HTTPException
from schemas.prediction import PredictionRequest
from contextlib import asynccontextmanager
import joblib
import pandas as pd
import os

# Carga del modelo
MODEL_PATH = "model_assets/rf_v1_baseline.joblib"
model_artifact = None

@asynccontextmanager
async def lifespan(app: FastAPI):
    """Carga el modelo al iniciar el servicio"""
    global model_artifact
    
    if not os.path.exists(MODEL_PATH):
        print(f"Modelo no encontrado en {MODEL_PATH}")
        print("El servicio iniciara sin modelo cargado.")
        model_artifact = None
    else:
        try:
            model_artifact = joblib.load(MODEL_PATH)
            print(f"Modelo cargado exitosamente: {model_artifact['model_name']}")
            print(f"Features esperadas: {len(model_artifact['features'])}")
        except Exception as e:
            print(f"Error al cargar el modelo: {e}")
            model_artifact = None
    
    yield
    # Aqui iria codigo de cleanup si fuera necesario

app = FastAPI(title="ChurnInsight ML Service", version="1.0", lifespan=lifespan)

@app.get("/")
def health_check():
    model_status = "loaded" if model_artifact else "not_loaded"
    return {
        "status": "ok",
        "service": "ml-service-v1",
        "model_status": model_status
    }

@app.post("/predict")
def predict_churn(data: PredictionRequest):
    """
    Endpoint para predecir la probabilidad de churn de un cliente.
    
    Retorna probability: Probabilidad de churn (0-1)
    """
    
    # Validar que el modelo este cargado
    if model_artifact is None:
        raise HTTPException(
            status_code=503,
            detail="Modelo no disponible. Por favor, asegurate de que el archivo del modelo este en model_assets/"
        )
    
    try:
        # Convertir los datos de entrada a DataFrame
        input_dict = data.model_dump(by_alias=True)
        input_df = pd.DataFrame([input_dict])
        
        # Reordenar columnas segun el orden esperado por el modelo
        expected_features = model_artifact['features']
        input_df = input_df[expected_features]
        
        # Realizar la prediccion
        model = model_artifact['model']
        
        # Probabilidad de churn (clase 1)
        probability = float(model.predict_proba(input_df)[0][1])
        
        return probability
        
    except KeyError as e:
        raise HTTPException(
            status_code=400,
            detail=f"Feature faltante o invalida: {str(e)}"
        )
    except Exception as e:
        raise HTTPException(
            status_code=500,
            detail=f"Error en la prediccion: {str(e)}"
        )
