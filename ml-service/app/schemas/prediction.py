from pydantic import BaseModel, Field


class PredictionRequest(BaseModel):

    age: int = Field(..., gt=0, description="Edad del cliente")

    class Config:
        json_schema_extra = {
            "example": {
                "age": 34
            }
        }
