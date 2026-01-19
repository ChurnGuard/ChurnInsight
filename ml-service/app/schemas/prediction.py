from pydantic import BaseModel, Field


class PredictionRequest(BaseModel):
    # Caracteristicas demograficas
    age: int = Field(..., gt=0, description="Edad del cliente")
    membership_years: int = Field(..., ge=0, description="Años de membresia")
    number_of_children: int = Field(..., ge=0, description="Numero de hijos")
    
    # Caracteristicas de transaccion
    quantity: float = Field(..., gt=0, description="Cantidad promedio por transaccion")
    unit_price: float = Field(..., gt=0, description="Precio unitario promedio")
    avg_purchase_value: float = Field(..., gt=0, description="Valor promedio de compra")
    purchase_frequency: float = Field(..., ge=0, description="Frecuencia de compra")
    avg_discount_used: float = Field(..., ge=0, le=1, description="Descuento promedio usado")
    
    # Canales de compra
    online_purchases: int = Field(..., ge=0, description="Numero de compras online")
    in_store_purchases: int = Field(..., ge=0, description="Numero de compras en tienda")
    
    # Metricas agregadas
    total_transactions: int = Field(..., gt=0, description="Total de transacciones")
    total_items_purchased: int = Field(..., gt=0, description="Total de items comprados")
    promotion_effectiveness: float = Field(..., ge=0, le=1, description="Efectividad de promociones")
    days_since_last_purchase: int = Field(..., ge=0, description="Dias desde ultima compra")
    
    # Programa de lealtad
    loyalty_program: int = Field(..., ge=0, le=1, description="Participa en programa de lealtad (0 o 1)")
    promo_flag: int = Field(..., ge=0, le=1, description="Flag de promocion (0 o 1)")
    
    # Variables categoricas: Gender (one-hot encoded)
    gender_Female: int = Field(..., ge=0, le=1, description="Genero: Femenino")
    gender_Male: int = Field(..., ge=0, le=1, description="Genero: Masculino")
    gender_Other: int = Field(..., ge=0, le=1, description="Genero: Otro")
    
    # Income Bracket (one-hot encoded)
    income_bracket_High: int = Field(..., ge=0, le=1, description="Ingreso: Alto")
    income_bracket_Low: int = Field(..., ge=0, le=1, description="Ingreso: Bajo")
    income_bracket_Medium: int = Field(..., ge=0, le=1, description="Ingreso: Medio")
    
    # Marital Status (one-hot encoded)
    marital_status_Divorced: int = Field(..., ge=0, le=1, description="Estado civil: Divorciado")
    marital_status_Married: int = Field(..., ge=0, le=1, description="Estado civil: Casado")
    marital_status_Single: int = Field(..., ge=0, le=1, description="Estado civil: Soltero")
    
    # Education Level (one-hot encoded)
    education_level_Bachelors: int = Field(..., ge=0, le=1, description="Educacion: Licenciatura", alias="education_level_Bachelor's")
    education_level_High_School: int = Field(..., ge=0, le=1, description="Educacion: Preparatoria", alias="education_level_High School")
    education_level_Masters: int = Field(..., ge=0, le=1, description="Educacion: Maestria", alias="education_level_Master's")
    education_level_PhD: int = Field(..., ge=0, le=1, description="Educacion: Doctorado")
    
    # Occupation (one-hot encoded)
    occupation_Employed: int = Field(..., ge=0, le=1, description="Ocupacion: Empleado")
    occupation_Retired: int = Field(..., ge=0, le=1, description="Ocupacion: Retirado")
    occupation_Self_Employed: int = Field(..., ge=0, le=1, description="Ocupacion: Autoempleado", alias="occupation_Self-Employed")
    occupation_Student: int = Field(..., ge=0, le=1, description="Ocupacion: Estudiante")
    occupation_Unemployed: int = Field(..., ge=0, le=1, description="Ocupacion: Desempleado")
    
    # Product Category (one-hot encoded)
    product_category_Beauty: int = Field(..., ge=0, le=1, description="Categoria: Belleza")
    product_category_Books: int = Field(..., ge=0, le=1, description="Categoria: Libros")
    product_category_Clothing: int = Field(..., ge=0, le=1, description="Categoria: Ropa")
    product_category_Electronics: int = Field(..., ge=0, le=1, description="Categoria: Electronicos")
    product_category_Groceries: int = Field(..., ge=0, le=1, description="Categoria: Abarrotes")
    product_category_Home: int = Field(..., ge=0, le=1, description="Categoria: Hogar")
    product_category_Home_Goods: int = Field(..., ge=0, le=1, description="Categoria: Articulos del hogar", alias="product_category_Home Goods")
    product_category_Sports: int = Field(..., ge=0, le=1, description="Categoria: Deportes")
    product_category_Toys: int = Field(..., ge=0, le=1, description="Categoria: Juguetes")
    
    # Promotion Type (one-hot encoded)
    promotion_type_20_Off: int = Field(..., ge=0, le=1, description="Promocion: 20% Off", alias="promotion_type_20% Off")
    promotion_type_BOGO: int = Field(..., ge=0, le=1, description="Promocion: BOGO")
    promotion_type_Buy_One_Get_One_Free: int = Field(..., ge=0, le=1, description="Promocion: Compra uno lleva otro", alias="promotion_type_Buy One Get One Free")
    promotion_type_Discount: int = Field(..., ge=0, le=1, description="Promocion: Descuento")
    promotion_type_Seasonal_Discount: int = Field(..., ge=0, le=1, description="Promocion: Descuento de temporada", alias="promotion_type_Seasonal Discount")

    class Config:
        populate_by_name = True
        json_schema_extra = {
            "example": {
                "age": 33,
                "membership_years": 1,
                "number_of_children": 2,
                "quantity": 3.125,
                "unit_price": 54.51,
                "avg_purchase_value": 167.76,
                "purchase_frequency": 72.0,
                "avg_discount_used": 0.31,
                "online_purchases": 173,
                "in_store_purchases": 57,
                "total_transactions": 3023,
                "total_items_purchased": 4184,
                "promotion_effectiveness": 0.54,
                "days_since_last_purchase": 927,
                "loyalty_program": 1,
                "promo_flag": 1,
                "gender_Female": 0,
                "gender_Male": 0,
                "gender_Other": 1,
                "income_bracket_High": 0,
                "income_bracket_Low": 0,
                "income_bracket_Medium": 1,
                "marital_status_Divorced": 1,
                "marital_status_Married": 0,
                "marital_status_Single": 0,
                "education_level_Bachelor's": 0,
                "education_level_High School": 0,
                "education_level_Master's": 1,
                "education_level_PhD": 0,
                "occupation_Employed": 0,
                "occupation_Retired": 1,
                "occupation_Self-Employed": 0,
                "occupation_Student": 0,
                "occupation_Unemployed": 0,
                "product_category_Beauty": 0,
                "product_category_Books": 0,
                "product_category_Clothing": 0,
                "product_category_Electronics": 1,
                "product_category_Groceries": 0,
                "product_category_Home": 0,
                "product_category_Home Goods": 0,
                "product_category_Sports": 0,
                "product_category_Toys": 0,
                "promotion_type_20% Off": 0,
                "promotion_type_BOGO": 0,
                "promotion_type_Buy One Get One Free": 0,
                "promotion_type_Discount": 0,
                "promotion_type_Seasonal Discount": 1
            }
        }
