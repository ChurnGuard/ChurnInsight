from pydantic import BaseModel, Field


class PredictionRequest(BaseModel):
    # Datos demograficos
    age: int = Field(..., gt=0, description="Edad del cliente")
    income_bracket: int = Field(..., description="Nivel de ingresos")

    # Comportamiento de compra
    recency: int = Field(..., description="Dias desde la ultima compra")
    frequency: int = Field(..., description="Frecuencia de compra")
    monetary: float = Field(..., description="Gasto total")
    total_transactions: int = Field(
        ..., description="Total de transacciones históricas"
    )
    avg_transaction_value: float = Field(
        ..., description="Valor promedio por transacción"
    )

    # Variables calculadas
    discount_usage_rate: float = Field(
        ..., ge=0, le=1, description="% de uso de descuentos"
    )
    return_rate: float = Field(default=0.0, description="Tasa de devoluciones")

    class Config:
        json_schema_extra = {
            "example": {
                "age": 34,
                "income_bracket": 3,
                "recency": 12,
                "frequency": 5,
                "monetary": 1250.50,
                "total_transactions": 22,
                "avg_transaction_value": 56.8,
                "discount_usage_rate": 0.15,
                "return_rate": 0.02,
            }
        }
