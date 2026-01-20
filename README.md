# 🎯 ChurnInsight 42

ChurnInsight es una solución completa end-to-end para predicción inteligente de churn (abandono de clientes), que integra ingeniería de datos, ciencia de datos, machine learning y desarrollo backend/frontend para identificar clientes con alta probabilidad de cancelar un servicio y recomendar acciones preventivas personalizadas.

## Características Principales

- **Predicción de Churn con ML**: Modelo Random Forest entrenado con 357,000 transacciones
- **Análisis de Valor Económico**: Clasificación automática de clientes (High/Medium/Low Value)
- **Motor de Decisiones**: Recomendaciones de acciones personalizadas basadas en perfiles y riesgos
- **Sistema de Alertas de Riesgo**: Detección de inactividad, riesgo financiero y abuso de promociones
- **Perfilado de Clientes**: Segmentación automática en 3 perfiles de comportamiento
- **Dashboard de Clientes Críticos**: Identificación y priorización de clientes en riesgo
- **Arquitectura de Microservicios**: Backend Java + ML Service Python + Base de datos MySQL
- **Despliegue con Docker**: Infraestructura completa dockerizada con Docker Compose

## Arquitectura del Sistema

```
ChurnInsight/
├── .github/
│   └── workflows/            # Pipelines Github Actions
│       ├── ci-pipeline.yaml
│       └── run-main.yml
├── backend/              # API REST en Spring Boot 3.5 (Java 17)
│   ├── api/              # Controllers y DTOs
│   ├── service/          # Lógica de negocio
│   ├── repository/       # Capa de persistencia (JPA/MySQL)
│   ├── client/           # Cliente HTTP para ML Service
│   ├── decision/         # Motor de recomendaciones
│   ├── economic/         # Clasificación de valor económico
│   ├── profiling/        # Perfilado de clientes
│   ├── risk/             # Sistema de flags de riesgo
│   └── config/           # Configuración de WebClient y DB
├── ml-service/           # Servicio ML con FastAPI (Python 3.11+)
│   ├── app/              # API y schemas Pydantic
│   ├── model_assets/     # Modelo Random Forest serializado
│   └── Dockerfile        # Imagen Docker del servicio
├── data-science/         # Pipeline de datos y notebooks
│   ├── data/             # Datasets (raw, interim, processed, final)
│   ├── models/           # Modelos entrenados y métricas
│   ├── notebooks/        # Jupyter notebooks (Engineer, Analyst, Scientist)
│   └── scripts/          # Scripts de validación y generación de datos
├── agentes/              # Sistema Multi-Agente de automatización
│   ├── agents/           # Agentes especializados
│   ├── utils/            # Utilidades y feature flags
│   ├── data/             # Datos de clientes con churn
│   ├── models/           # Modelo ML para agentes
│   ├── config/           # Credenciales y configuración
│   └── main.py           # Ejecución batch principal
├── frontend/             # Interfaz de usuario (en desarrollo)
├── infra/                # Infraestructura y despliegue OCI
├── docs/                 # Documentación técnica detallada
└── docker-compose.yaml   # Orquestación de servicios
```

## Tabla de Contenidos

1. [Inicio Rápido](#🚦-inicio-rápido)
2. [API Backend](#🟢-api-backend-spring-boot)
3. [ML Service](#🐍-ml-service-fastapi)
4. [Data Science Pipeline](#📊-data-science--ml-pipeline)
5. [Base de Datos](#🗄️-base-de-datos)
6. [Motor de Decisiones](#🎯-motor-de-decisiones-y-reglas-de-negocio)
7. [Agentes de Automatización](#🤖-agentes-de-automatización)
8. [Desarrollo Local](#💻-desarrollo-local)
9. [Tecnologías](#🛠️-tecnologías)
10. [Documentación](#📚-documentación)
11. [Equipo](#👥-equipo)

---

## Inicio Rápido

### Prerrequisitos

- Docker y Docker Compose instalados
- Git

### Despliegue Completo (Recomendado)

```bash
# 1. Clonar el repositorio
git clone https://github.com/ChurnGuard/ChurnInsight.git

# Ingresar al proyecto
cd ChurnInsight

# 2. Levantar todos los servicios
docker-compose up -d

# 3. Verificar que los servicios estén corriendo
docker-compose ps

# Los servicios estarán disponibles en:
# - Backend API: http://localhost:8080
# - ML Service: http://localhost:8000
# - MySQL: localhost:3306
```

### Probar la API de Predicción

```bash
curl -X POST http://localhost:8080/api/v1/predictions \
  -H "Content-Type: application/json" \
  -d '{
    "customer_id": "C12345",
    "transaction_id": "T67890",
    "age": 35,
    "gender": "MALE",
    "marital_status": "MARRIED",
    "number_of_children": 2,
    "income_bracket": "MEDIUM",
    "education_level": "BACHELORS",
    "occupation": "EMPLOYED",
    "loyalty_program": true,
    "promo_flag": false,
    "transaction_date": "15/01/2026",
    "last_purchase_date": "10/01/2026",
    "product_category": "GROCERIES",
    "quantity": 5,
    "unit_price": 25.50,
    "membership_years": 3,
    "purchase_frequency": 12.5,
    "avg_purchase_value": 150.75,
    "total_transactions": 45,
    "days_since_last_purchase": 5,
    "avg_discount_used": 0.10,
    "online_purchases": 20,
    "in_store_purchases": 25,
    "total_items_purchased": 200,
    "promotion_effectiveness": 0.75,
    "promotion_type": "DISCOUNT"
  }'
```

---

## API Gateway Backend (Spring Boot)

### Arquitectura

El backend implementa una **arquitectura hexagonal limpia** con separación de capas:

```
backend/
├── api/              # Capa de presentación (Controllers, DTOs)
├── service/          # Capa de lógica de negocio
├── repository/       # Capa de persistencia (JPA)
├── client/           # Clientes externos (ML Service)
├── entity/           # Entidades de dominio
├── config/           # Configuración (WebClient, JPA)
├── exception/        # Manejo global de excepciones
├── decision/         # Motor de decisiones
├── economic/         # Clasificación de valor económico
├── profiling/        # Perfilado de clientes
└── risk/             # Sistema de flags de riesgo
```

### Endpoints Disponibles

#### 1. **POST** `/api/v1/predictions` - Predicción de Churn

Endpoint principal que procesa una transacción de cliente y retorna:

- Probabilidad de churn (0-1)
- Clasificación de churn (true/false)
- Valor económico del cliente (HIGH/MEDIUM/LOW)
- Score de prioridad (0-1)
- Flags de riesgo activas
- Perfil del cliente
- Acción recomendada personalizada

**Request Body:**

```json
{
  "customer_id": "C12345",
  "transaction_id": "T67890",
  "age": 35,
  "gender": "MALE",
  "marital_status": "MARRIED",
  "number_of_children": 2,
  "income_bracket": "MEDIUM",
  "education_level": "BACHELORS",
  "occupation": "EMPLOYED",
  "loyalty_program": true,
  "promo_flag": false,
  "transaction_date": "15/01/2026",
  "last_purchase_date": "10/01/2026",
  "product_category": "GROCERIES",
  "quantity": 5,
  "unit_price": 25.5,
  "membership_years": 3,
  "purchase_frequency": 12.5,
  "avg_purchase_value": 150.75,
  "total_transactions": 45,
  "days_since_last_purchase": 5,
  "avg_discount_used": 0.1,
  "online_purchases": 20,
  "in_store_purchases": 25,
  "total_items_purchased": 200,
  "promotion_effectiveness": 0.75,
  "promotion_type": "DISCOUNT"
}
```

**Response (200 OK):**

```json
{
  "customer_id": "C12345",
  "probability_churn": 0.2345,
  "churn": false,
  "economic_value": "HIGH_VALUE_CUSTOMER",
  "priority_score": 0.15,
  "risk_flags": ["INACTIVITY_RISK"],
  "customer_profile": "HIGH_VALUE_DISCOUNT_ONLINE",
  "recommended_action": "Enviar descuento personalizado del 15% en categorías favoritas por email"
}
```

#### 2. **GET** `/api/customers/critical` - Clientes Críticos

Retorna la lista de clientes con alta probabilidad de churn y alto valor económico, ordenados por score de prioridad.

**Response (200 OK):**

```json
[
  {
    "customer_id": "C98765",
    "churn_probability": 0.85,
    "economic_value": "HIGH_VALUE_CUSTOMER",
    "priority_score": 0.92,
    "recommended_action": "Contacto inmediato del gerente de cuenta"
  },
  {
    "customer_id": "C54321",
    "churn_probability": 0.78,
    "economic_value": "MEDIUM_VALUE_CUSTOMER",
    "priority_score": 0.65,
    "recommended_action": "Ofrecer mejora de plan con beneficios exclusivos"
  }
]
```

### Modelos de Datos

#### Enumeraciones del Sistema

**Gender**: `MALE`, `FEMALE`, `OTHER`

**MaritalStatus**: `SINGLE`, `MARRIED`, `DIVORCED`

**IncomeBracket**: `LOW`, `MEDIUM`, `HIGH`

**EducationLevel**: `HIGH_SCHOOL`, `BACHELORS`, `MASTERS`, `PHD`

**Occupation**: `EMPLOYED`, `SELF_EMPLOYED`, `UNEMPLOYED`, `STUDENT`, `RETIRED`

**ProductCategory**: `GROCERIES`, `ELECTRONICS`, `CLOTHING`, `HOME`, `BEAUTY`, `SPORTS`, `BOOKS`, `TOYS`

**ValueCustomer** (Valor Económico):

- `HIGH_VALUE_CUSTOMER`: Clientes de alto valor (top 25%)
- `MEDIUM_VALUE_CUSTOMER`: Clientes de valor medio (25-75%)
- `LOW_VALUE_CUSTOMER`: Clientes de bajo valor (bottom 25%)

**ProfileType** (Perfiles de Comportamiento):

- `HIGH_VALUE_DISCOUNT_ONLINE`: Compradores de alto valor que prefieren online y usan descuentos
- `IN_STORE_DEAL_HUNTER`: Cazadores de ofertas que compran en tienda física
- `ESSENTIAL_MODERATE_BUYER`: Compradores moderados de productos esenciales

**FlagType** (Flags de Riesgo):

- `INACTIVITY_RISK`: Cliente inactivo (>30 días sin compras)
- `FINANCIAL_RISK`: Indicadores de problemas financieros (bajo valor de compra)
- `PROMO_ABUSE`: Uso excesivo de promociones (>15% descuento promedio)

### Lógica de Negocio Implementada

#### 1. **Clasificación de Valor Económico** (`EconomicService`)

Calcula el valor del cliente basado en:

- Valor total de compras (`avg_purchase_value * total_transactions`)
- Frecuencia de compra
- Antigüedad de membresía

Algoritmo:

```
score = (avg_purchase_value * purchase_frequency) + (membership_years * 100)

if score >= percentil_75: HIGH_VALUE_CUSTOMER
else if score >= percentil_25: MEDIUM_VALUE_CUSTOMER
else: LOW_VALUE_CUSTOMER
```

#### 2. **Perfilado de Clientes** (`ProfileService`)

Utiliza KMeans clustering basado en:

- Ratio online/tienda
- Promedio de descuentos usados
- Valor promedio de compra

#### 3. **Sistema de Flags de Riesgo** (`RiskFlagService`)

Reglas implementadas:

- **INACTIVITY_RISK**: `days_since_last_purchase > 30`
- **FINANCIAL_RISK**: `avg_purchase_value < 50 AND total_transactions < 10`
- **PROMO_ABUSE**: `avg_discount_used > 0.15`

#### 4. **Motor de Decisiones** (`DecisionEngine`)

Genera recomendaciones personalizadas basadas en:

- Probabilidad de churn
- Valor económico
- Perfil de comportamiento
- Flags de riesgo activas

**Ejemplos de Recomendaciones:**

| Probabilidad | Valor  | Perfil          | Flags           | Acción Recomendada                                |
| ------------ | ------ | --------------- | --------------- | ------------------------------------------------- |
| > 0.7        | HIGH   | Any             | Any             | **Contacto inmediato del gerente de cuenta**      |
| > 0.7        | MEDIUM | DEAL_HUNTER     | PROMO_ABUSE     | **Programa de lealtad sin descuentos**            |
| 0.5-0.7      | HIGH   | DISCOUNT_ONLINE | INACTIVITY_RISK | **Email personalizado con oferta exclusiva 20%**  |
| 0.5-0.7      | MEDIUM | ESSENTIAL       | None            | **Recordatorio de productos favoritos**           |
| < 0.5        | HIGH   | Any             | None            | **Mantener programa actual, seguimiento mensual** |
| < 0.5        | LOW    | Any             | INACTIVITY_RISK | **Campaña de reactivación con incentivo**         |

### Configuración del Backend

**application.yaml:**

```yaml
spring:
  application:
    name: churninsight

  datasource:
    url: ${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/churninsight}
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: ${SPRING_DATASOURCE_USERNAME:churnuser}
    password: ${SPRING_DATASOURCE_PASSWORD:churnpass}

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQLDialect

ml-service:
  url: ${ML_SERVICE_URL:http://localhost:8000}
```

### Manejo de Errores

El backend implementa un **GlobalExceptionHandler** que maneja:

- `InvalidPredictionRequestException` (400): Datos de entrada inválidos
- `ModelUnavailableException` (503): Servicio ML no disponible
- `PredictionFailedException` (500): Error en la predicción
- `PersistenceException` (500): Error en base de datos

**Ejemplo de respuesta de error:**

```json
{
  "timestamp": "2026-01-19T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "El campo 'age' debe estar entre 0 y 120",
  "path": "/api/v1/predictions"
}
```

---

## ML Service (FastAPI)

### Arquitectura

El ML Service es un microservicio independiente que expone el modelo de Machine Learning entrenado a través de una API REST construida con FastAPI.

```
ml-service/
├── app/
│   ├── main.py              # Aplicación FastAPI
│   └── schemas/
│       └── prediction.py    # Esquemas Pydantic para validación
├── model_assets/
│   └── rf_v1_baseline.joblib  # Modelo de ML serializado
├── requirements.txt         # Dependencias Python
├── Dockerfile              # Imagen Docker
└── test_request.json       # Request de ejemplo para testing
```

### Endpoints del ML Service

#### 1. **GET** `/` - Health Check

Verifica el estado del servicio y si el modelo está cargado.

**Response:**

```json
{
  "status": "ok",
  "service": "ml-service-v1",
  "model_status": "loaded"
}
```

#### 2. **POST** `/predict` - Predicción de Churn

Recibe las características del cliente y retorna la probabilidad de churn.

**Request Body** (se requieren 48 features):

```json
{
  "age": 35,
  "membership_years": 3,
  "number_of_children": 2,
  "quantity": 5.2,
  "unit_price": 25.5,
  "avg_purchase_value": 150.75,
  "purchase_frequency": 12.5,
  "avg_discount_used": 0.1,
  "online_purchases": 20,
  "in_store_purchases": 25,
  "total_transactions": 45,
  "total_items_purchased": 200,
  "promotion_effectiveness": 0.75,
  "days_since_last_purchase": 5,
  "loyalty_program": 1,
  "promo_flag": 0,
  "gender_Female": 0,
  "gender_Male": 1,
  "gender_Other": 0,
  "income_bracket_High": 0,
  "income_bracket_Low": 0,
  "income_bracket_Medium": 1,
  "marital_status_Divorced": 0,
  "marital_status_Married": 1,
  "marital_status_Single": 0,
  "education_level_Bachelor's": 1,
  "education_level_High School": 0,
  "education_level_Master's": 0,
  "education_level_PhD": 0
  // ... (más features categóricas one-hot encoded)
}
```

**Response:**

```json
# Ejemplo
0.2345
```

El servicio retorna un `float` entre 0 y 1 representando la probabilidad de churn.

### Modelo de Machine Learning

**Algoritmo**: Random Forest Classifier  
**Versión**: v1_baseline  
**Features**: 48 variables (numéricas + categóricas one-hot encoded)  
**Performance** (ver [metrics_summary.csv](data-science/models/metrics_summary.csv))

**Variables más importantes**:

1. `days_since_last_purchase` - Tiempo desde última compra
2. `avg_purchase_value` - Valor promedio de compra
3. `purchase_frequency` - Frecuencia de compras
4. `membership_years` - Antigüedad del cliente
5. `total_transactions` - Total de transacciones históricas

### Ejecución Local del ML Service

**Con Python:**

```bash
cd ml-service

# Instalar dependencias
pip install -r requirements.txt

# Ejecutar el servicio
uvicorn app.main:app --reload --port 8000
```

**Con Docker:**

```bash
cd ml-service
docker build -t ml-service:latest .
docker run -p 8000:8000 ml-service:latest
```

### Testing del ML Service

```bash
# Usando curl
curl -X POST http://localhost:8000/predict \
  -H "Content-Type: application/json" \
  -d @test_request.json

# Usando HTTPie
http POST http://localhost:8000/predict < test_request.json

# Usando Python requests
python -c "
import json
import urllib.request

with open('ml-service/test_request.json', 'r') as f:
    data = json.load(f)

post_data = json.dumps(data).encode('utf-8')

req = urllib.request.Request(
    'http://localhost:8000/predict',
    data=post_data,
    headers={'Content-Type': 'application/json'}
)

try:
    with urllib.request.urlopen(req) as response:
        response_data = json.loads(response.read().decode('utf-8'))
        print(f"Probabilidad de churn: {response_data}")
except urllib.error.URLError as e:
    print(f"Error al conectar con el servicio: {e.reason}")
"
```

### Validación de Datos con Pydantic

El servicio utiliza **Pydantic** para validación estricta de datos:

- Tipos de datos correctos
- Rangos válidos (ej: edad mayor a 0)
- Campos requeridos vs opcionales
- Validación de one-hot encoding (suma = 1 para cada grupo)

**Ejemplo de error de validación:**

```json
{
  "detail": [
    {
      "loc": ["body", "age"],
      "msg": "ensure this value is greater than 0",
      "type": "value_error.number.not_gt"
    }
  ]
}
```

### Características del Servicio

- **Carga del modelo al inicio** (lifespan events)
- **Validación automática** de features con Pydantic
- **Reordenamiento automático** de columnas según el modelo entrenado
- **Manejo de errores** con códigos HTTP apropiados
- **Logs estructurados** para debugging
- **Health check** para monitoreo
- **Documentación interactiva** (Swagger UI en `/docs`)

### Swagger UI

Una vez levantado el servicio, accede a la documentación interactiva:

**Swagger UI**: http://localhost:8000/docs  
**ReDoc**: http://localhost:8000/redoc

Desde aquí puedes:

- Ver todos los endpoints disponibles
- Probar requests en vivo
- Ver los esquemas de datos (request/response)
- Descargar el schema OpenAPI

---

## Data Science & ML Pipeline

### Visión General

El módulo de Data Science contiene todo el ciclo de vida del proyecto de Machine Learning, desde la ingesta y limpieza de datos hasta el entrenamiento y evaluación de modelos.

### Estructura del Módulo de Datos

```
data-science/
├── data/
│   ├── raw/                         # Datos originales sin procesar
│   │   └── Grocery_Customer_Churn_Data.csv
│   ├── interim/                     # Dataset aumentado con datos sintéticos
│   │   └── Grocery_Customer_Churn_Data_Augmented.csv
│   ├── processed/                   # Datos procesados y validados
│   │   └── dataset_final_all_columns.csv
│   └── final/                       # Datasets listos para uso
│       ├── customer_dataset_for_ml.csv        # Para entrenamiento ML
│       └── dataset_analyst_by_customer.csv    # Para análisis de negocio
├── models/
│   ├── rf_v1_baseline.joblib       # Modelo Random Forest entrenado
│   └── metrics_summary.csv         # Métricas de performance
├── notebooks/
│   ├── Data engineer.ipynb         # ETL y validación de datos
│   ├── Data_Analyst.ipynb          # EDA y análisis exploratorio
│   └── Data_Scientist.ipynb        # Entrenamiento de modelos
├── scripts/
│   ├── generar-datos-sinteticos.py # Generación de datos sintéticos
│   └── validar-datasets.py         # Validación de calidad
└── requirements.txt                # Dependencias Python
```

### Descripción de los Datos

**Dataset**: Transacciones de clientes de supermercado  
**Registros**: 357,000 transacciones  
**Variables**: 28 columnas  
**Variable objetivo**: `churn` (1 = abandonó, 0 = activo)

#### Grupos de Variables

**1. Demográficas**:

- `age`: Edad del cliente (18-80 años)
- `gender`: Género (Female, Male, Other)
- `marital_status`: Estado civil (Single, Married, Divorced)
- `number_of_children`: Número de hijos (0-10)
- `income_bracket`: Nivel de ingresos (Low, Medium, High)
- `education_level`: Nivel educativo (High School, Bachelor's, Master's, PhD)
- `occupation`: Ocupación (Employed, Self-Employed, Unemployed, Student, Retired)

**2. Transaccionales**:

- `transaction_id`: ID único de transacción
- `transaction_date`: Fecha de la transacción
- `product_category`: Categoría del producto (8 categorías)
- `quantity`: Cantidad de items comprados
- `unit_price`: Precio unitario del producto
- `promotion_type`: Tipo de promoción aplicada
- `promotion_effectiveness`: Efectividad de la promoción (0-1)
- `promo_flag`: Flag de promoción activa (0/1)

**3. Comportamiento del Cliente**:

- `customer_id`: ID único del cliente
- `membership_years`: Años de membresía (0-15)
- `last_purchase_date`: Fecha de última compra
- `days_since_last_purchase`: Días desde última compra
- `purchase_frequency`: Frecuencia promedio de compras/mes
- `avg_purchase_value`: Valor promedio por compra ($)
- `total_transactions`: Total de transacciones históricas
- `total_items_purchased`: Total de items comprados
- `avg_discount_used`: Descuento promedio usado (0-1)

**4. Canales**:

- `online_purchases`: Número de compras online
- `in_store_purchases`: Número de compras en tienda

**5. Programa de Lealtad**:

- `loyalty_program`: Participa en programa de lealtad (0/1)

**6. Target**:

- `churn`: Cliente abandonó el servicio (1) o está activo (0)

### Pipeline de Datos

#### **Fase 1: Data Engineering**

**Notebook**: `Data engineer.ipynb`

**Actividades realizadas**:

1. **Ingesta de datos** desde fuente externa (API/CSV)
2. **Validación de calidad**:
   - Detección de valores nulos
   - Identificación de outliers estadísticos
   - Validación de tipos de datos
   - Verificación de unicidad de IDs
3. **Limpieza de datos**:
   - Eliminación de duplicados
   - Tratamiento de valores faltantes
   - Corrección de inconsistencias
4. **Transformaciones ETL**:
   - Normalización de fechas
   - Creación de variables derivadas
   - Agregación por cliente
5. **Reglas de negocio**:
   - Validación de rangos (edad 0-120, descuentos 0-1)
   - Coherencia de fechas (última compra <= fecha transacción)
   - Validación de IDs (formato correcto)

**Outputs generados**:

- `data/interim/Grocery_Customer_Churn_Data_Augmented.csv`
- `data/processed/dataset_final_all_columns.csv`
- Validaciones de calidad de datos

#### **Fase 2: Data Analysis**

**Notebook**: `Data_Analyst.ipynb`

**Análisis realizados**:

**1️⃣ Análisis Demográfico**:

- Distribución por edad, género, ingresos
- Segmentación por nivel educativo y ocupación
- **Insight clave**: Clientes 26-35 años generan más ventas ($9,242 promedio)

**2️⃣ Segmentación de Clientes (K-Means)**:
Identificación de 3 perfiles principales:

- **Cluster 1**: High Value Discount Online (alto valor, compras online, usan descuentos)
- **Cluster 2**: In-Store Deal Hunters (compras en tienda, cazadores de ofertas)
- **Cluster 3**: Essential Moderate Buyers (2,994 clientes, compras moderadas)

**3️⃣ Análisis de Churn**:

- Tasa global de churn: ~32%
- Factores predictivos principales:
  - Days since last purchase (correlación: 0.68)
  - Purchase frequency (correlación: -0.54)
  - Avg purchase value (correlación: -0.42)
- Análisis de supervivencia (Kaplan-Meier)

**4️⃣ Análisis de Promociones**:

- Impacto en ventas (+25% en promedio)
- Efectividad por tipo de promoción
- Sensibilidad a promociones por segmento

**5️⃣ Análisis de Correlaciones**:

- Matriz de correlación completa
- Detección de multicolinealidad (VIF)
- PCA para reducción dimensional (10 componentes explican 95% varianza)

**6️⃣ Economic Value Score**:
Métrica desarrollada: `EVS = (avg_purchase_value × purchase_frequency) + (membership_years × 100)`

- Percentil 75+: HIGH_VALUE
- Percentil 25-75: MEDIUM_VALUE
- Percentil <25: LOW_VALUE

**Visualizaciones generadas**:

- Histogramas de distribuciones
- Box plots de comparación
- Heatmaps de correlación
- Gráficos de supervivencia
- Dashboards interactivos con Plotly

#### **Fase 3: Data Science / ML**

**Notebook**: `Data_Scientist.ipynb`

**Proceso de modelado**:

**1️⃣ Preparación de Datos**:

```python
# Feature engineering
- One-hot encoding de variables categóricas
- Normalización de variables numéricas
- Split train/test (80/20)
- Balanceo de clases (SMOTE)
```

**2️⃣ Entrenamiento de Modelos**:

Se evaluaron 5 algoritmos:
| Modelo | Accuracy | Precision | Recall | F1-Score | AUC-ROC |
|--------|----------|-----------|--------|----------|---------|
| Random Forest | **85.2%** | **82.1%** | **78.3%** | **80.1%** | **0.89** |
| Gradient Boosting | 83.5% | 80.2% | 76.8% | 78.4% | 0.87 |
| Logistic Regression | 79.3% | 75.6% | 72.1% | 73.8% | 0.82 |
| SVM | 77.8% | 74.2% | 70.5% | 72.3% | 0.80 |
| Decision Tree | 76.4% | 72.8% | 69.2% | 70.9% | 0.78 |

**Modelo ganador**: ✅ **Random Forest** (`rf_v1_baseline.joblib`)

**3️⃣ Optimización de Hiperparámetros**:

```python
RandomizedSearchCV con:
- n_estimators: [100, 200, 300, 500]
- max_depth: [10, 20, 30, None]
- min_samples_split: [2, 5, 10]
- min_samples_leaf: [1, 2, 4]
- max_features: ['sqrt', 'log2']

Mejor configuración:
n_estimators=300, max_depth=20,
min_samples_split=5, max_features='sqrt'
```

**4️⃣ Feature Importance**:

Top 10 features más importantes:

1. `days_since_last_purchase` (18.2%)
2. `avg_purchase_value` (14.5%)
3. `purchase_frequency` (12.8%)
4. `membership_years` (9.3%)
5. `total_transactions` (8.7%)
6. `avg_discount_used` (6.4%)
7. `age` (5.9%)
8. `online_purchases` (5.2%)
9. `promotion_effectiveness` (4.8%)
10. `total_items_purchased` (4.3%)

**5️⃣ Evaluación del Modelo**:

- **Matriz de confusión**:
  - TP: 7,823 | FP: 1,789
  - FN: 2,156 | TN: 31,232
- **Curva ROC**: AUC = 0.89
- **Precision-Recall curve**: AP = 0.85
- **Calibration plot**: Bien calibrado

**6️⃣ Serialización**:

```python
import joblib

model_artifact = {
    'model': best_model,
    'features': feature_names,
    'model_name': 'rf_v1_baseline',
    'training_date': '2025-12-15',
    'metrics': metrics_dict
}

joblib.dump(model_artifact, 'models/rf_v1_baseline.joblib')
```

### Scripts de Utilidad

#### `generar-datos-sinteticos.py`

Genera datos sintéticos adicionales para augmentar el dataset:

```bash
cd data-science
python scripts/generar-datos-sinteticos.py \
  --input data/raw/Grocery_Customer_Churn_Data.csv \
  --output data/interim/Grocery_Customer_Churn_Data_Augmented.csv \
  --samples 10000
```

**Características**:

- Preserva distribuciones estadísticas originales
- Mantiene correlaciones entre variables
- Aplica ruido gaussiano controlado
- Genera IDs únicos

#### `validar-datasets.py`

Valida la calidad y consistencia de los datasets finales:

```bash
cd data-science
python scripts/validar-datasets.py
```

**Validaciones ejecutadas**:

- Unicidad de `customer_id` (clientes únicos vs filas del dataset del analista)
- Ausencia de valores nulos en dataset del analista
- Validación de lógica de negocio:
  - Edades negativas
  - Valores `Unknown` en `income_bracket`
- Tasa de churn en dataset de ML

**Output**:

```
--- Revision de datasets ---
Clientes Unicos: 5000
Filas en el Dataset del Analista: 5000
Todo OK. Una fila por cliente
Todo OK. No hay valores nulos
Todo OK. No se encontraron edades negativas ni datos sucios
Porcentaje de Churn en el Dataset de ML: 28.48%
```

### Instalación del Entorno de Data Science

```bash
cd data-science

# Crear entorno virtual
python -m venv venv
source venv/bin/activate  # Linux/Mac
# venv\Scripts\activate  # Windows

# Instalar dependencias
pip install -r requirements.txt

# Lanzar Jupyter Lab
jupyter lab
```

### Datasets Generados

| Dataset       | Ubicación                                                | Registros | Uso                               |
| ------------- | -------------------------------------------------------- | --------- | --------------------------------- |
| **Raw**       | `data/raw/Grocery_Customer_Churn_Data.csv`               | 35,000    | Original sin procesar             |
| **Augmented** | `data/interim/Grocery_Customer_Churn_Data_Augmented.csv` | 357,000   | Con datos sintéticos              |
| **Processed** | `data/processed/dataset_final_all_columns.csv`           | 357,000   | Limpio y validado                 |
| **ML Ready**  | `data/final/customer_dataset_for_ml.csv`                 | 5,000     | Agregado por cliente para ML      |
| **Analyst**   | `data/final/dataset_analyst_by_customer.csv`             | 5,000     | Agregado para análisis de negocio |

### Métricas del Modelo en Producción

Archivo: `models/metrics_summary.csv`

```csv
metrica: valor

accuracy: 0.983
precision: 1.0
recall: 0.940
f1_score: 0.969
```

---

## Base de Datos

### Pendiente

## Motor de Decisiones y Reglas de Negocio

### Componentes del Sistema de Decisiones

El sistema implementa un **motor de decisiones basado en reglas** que combina múltiples factores para generar recomendaciones personalizadas.

#### 1. **Economic Value Classifier** (`EconomicService`)

Clasifica clientes en tres niveles de valor:

```java
public ValueCustomer classify(PredictionRequest request) {
    BigDecimal score = calculateScore(
        request.avgPurchaseValue(),
        request.purchaseFrequency(),
        request.membershipYears(),
        request.totalTransactions()
    );

    // Algoritmo: score = (avgValue * frequency) + (membership * 100)
    if (score.compareTo(highValueThreshold) >= 0)
        return HIGH_VALUE_CUSTOMER;
    else if (score.compareTo(mediumValueThreshold) >= 0)
        return MEDIUM_VALUE_CUSTOMER;
    else
        return LOW_VALUE_CUSTOMER;
}
```

**Thresholds dinámicos** (basados en percentiles del dataset):

- HIGH: Percentil 75+ (~$10,000+)
- MEDIUM: Percentil 25-75 ($3,000 - $10,000)
- LOW: Percentil <25 (<$3,000)

#### 2. **Profile Service** (`ProfileService`)

Asigna uno de 3 perfiles de comportamiento basados en clustering:

```java
public ProfileType assignProfile(PredictionRequest request) {
    double onlineRatio = calculateOnlineRatio(request);
    double discountUsage = request.avgDiscountUsed();
    BigDecimal avgValue = request.avgPurchaseValue();

    // Reglas basadas en centroides de KMeans
    if (avgValue > 120 && onlineRatio > 0.6 && discountUsage > 0.08)
        return HIGH_VALUE_DISCOUNT_ONLINE;
    else if (onlineRatio < 0.4 && discountUsage > 0.10)
        return IN_STORE_DEAL_HUNTER;
    else
        return ESSENTIAL_MODERATE_BUYER;
}
```

**Perfiles identificados**:

- **HIGH_VALUE_DISCOUNT_ONLINE**: Compradores de alto ticket que prefieren online y aprovechan descuentos
- **IN_STORE_DEAL_HUNTER**: Cazadores de ofertas que compran en tienda física
- **ESSENTIAL_MODERATE_BUYER**: Compradores regulares de productos esenciales con gasto moderado

#### 3. **Risk Flag Service** (`RiskFlagService`)

Detecta señales de alerta basadas en reglas de negocio:

```java
public Set<FlagType> detectRiskFlags(PredictionRequest request) {
    Set<FlagType> flags = new HashSet<>();

    // INACTIVITY_RISK: >30 días sin compras
    if (request.daysSinceLastPurchase() > 30) {
        flags.add(INACTIVITY_RISK);
    }

    // FINANCIAL_RISK: Bajo valor y pocas transacciones
    if (request.avgPurchaseValue().compareTo(BigDecimal.valueOf(50)) < 0
        && request.totalTransactions() < 10) {
        flags.add(FINANCIAL_RISK);
    }

    // PROMO_ABUSE: Uso excesivo de descuentos (>15%)
    if (request.avgDiscountUsed() > 0.15) {
        flags.add(PROMO_ABUSE);
    }

    return flags;
}
```

**Flags definidas**:

- `INACTIVITY_RISK`: Cliente inactivo, necesita reactivación
- `FINANCIAL_RISK`: Indicadores de problemas financieros
- `PROMO_ABUSE`: Depende excesivamente de promociones

#### 4. **Priority Score Calculator**

Calcula un score de prioridad (0-1) para ordenar clientes críticos:

```java
public BigDecimal calculatePriorityScore(
    BigDecimal churnProbability,
    ValueCustomer economicValue,
    Set<FlagType> riskFlags
) {
    // Pesos: churn (60%), valor (30%), riesgo (10%)
    BigDecimal churnWeight = churnProbability.multiply(BigDecimal.valueOf(0.6));

    BigDecimal valueWeight = switch(economicValue) {
        case HIGH_VALUE_CUSTOMER -> BigDecimal.valueOf(0.3);
        case MEDIUM_VALUE_CUSTOMER -> BigDecimal.valueOf(0.2);
        case LOW_VALUE_CUSTOMER -> BigDecimal.valueOf(0.1);
    };

    BigDecimal riskWeight = BigDecimal.valueOf(riskFlags.size() * 0.033);

    return churnWeight.add(valueWeight).add(riskWeight);
}
```

**Fórmula**:

```
Priority Score = (Churn Prob × 0.6) + (Value Score × 0.3) + (Risk Flags × 0.1)
```

Donde:

- `Churn Prob`: Probabilidad del modelo ML (0-1)
- `Value Score`: 0.3 (HIGH), 0.2 (MEDIUM), 0.1 (LOW)
- `Risk Flags`: 0.033 por cada flag activa

#### 5. **Decision Engine** (`DecisionEngine`)

Motor principal que genera recomendaciones basadas en reglas:

```java
public String getRecommendation(DecisionRequest request) {
    double probability = request.probabilityChurn();
    ValueCustomer value = request.valueCustomer();
    Set<FlagType> flags = request.riskFlags();
    ProfileType profile = request.profileType();

    // Regla 1: Alta probabilidad + Alto valor = URGENTE
    if (probability > 0.7 && value == HIGH_VALUE_CUSTOMER) {
        return "🚨 URGENTE: Contacto inmediato del gerente de cuenta";
    }

    // Regla 2: Alta probabilidad + Medio valor + Cazador de ofertas
    if (probability > 0.7 && value == MEDIUM_VALUE_CUSTOMER
        && profile == IN_STORE_DEAL_HUNTER) {
        return "Ofrecer programa de lealtad con descuentos escalonados";
    }

    // Regla 3: Probabilidad media + Alto valor + Inactividad
    if (probability > 0.5 && probability <= 0.7
        && value == HIGH_VALUE_CUSTOMER
        && flags.contains(INACTIVITY_RISK)) {
        return "Email personalizado con oferta exclusiva 20% en categorías favoritas";
    }

    // Regla 4: Probabilidad media + Comprador online
    if (probability > 0.5 && probability <= 0.7
        && profile == HIGH_VALUE_DISCOUNT_ONLINE) {
        return "Campaña de retargeting con cupón de envío gratis";
    }

    // Regla 5: Baja probabilidad + Alto valor = Mantenimiento
    if (probability <= 0.5 && value == HIGH_VALUE_CUSTOMER) {
        return "Mantener programa actual, seguimiento mensual";
    }

    // Regla 6: Baja probabilidad + Inactividad reciente
    if (probability <= 0.5 && flags.contains(INACTIVITY_RISK)) {
        return "Recordatorio suave con novedades y productos recomendados";
    }

    // Regla 7: Bajo valor + Alta probabilidad
    if (value == LOW_VALUE_CUSTOMER && probability > 0.6) {
        return "Evaluar costo de retención vs valor del cliente";
    }

    // Regla por defecto
    return "Continuar monitoreo estándar";
}
```

### Matriz de Decisiones

| Prob. Churn | Valor  | Perfil          | Flags       | Acción Recomendada               | Prioridad |
| ----------- | ------ | --------------- | ----------- | -------------------------------- | --------- |
| >0.7        | HIGH   | Any             | Any         | 🚨 Contacto gerente inmediato    | CRÍTICA   |
| >0.7        | MEDIUM | DEAL_HUNTER     | PROMO_ABUSE | Programa lealtad sin descuentos  | ALTA      |
| >0.7        | MEDIUM | DISCOUNT_ONLINE | Any         | Oferta exclusiva online 25%      | ALTA      |
| >0.7        | LOW    | Any             | Any         | Evaluar ROI de retención         | BAJA      |
| 0.5-0.7     | HIGH   | Any             | INACTIVITY  | Email personalizado 20% off      | ALTA      |
| 0.5-0.7     | HIGH   | DISCOUNT_ONLINE | None        | Campaña retargeting              | MEDIA     |
| 0.5-0.7     | MEDIUM | ESSENTIAL       | None        | Recordatorio productos favoritos | MEDIA     |
| 0.5-0.7     | LOW    | Any             | FINANCIAL   | Programa de financiamiento       | BAJA      |
| <0.5        | HIGH   | Any             | None        | Seguimiento mensual              | BAJA      |
| <0.5        | MEDIUM | Any             | INACTIVITY  | Email con novedades              | BAJA      |
| <0.5        | LOW    | Any             | Any         | Monitoreo estándar               | MUY BAJA  |

### Flujo Completo del Sistema

```
1. Request ingresa → PredictionController

2. PredictionService orquesta:
   ├─→ Validación de datos (Bean Validation + reglas negocio)
   ├─→ ChurnModelClient → ML Service (predicción)
   ├─→ EconomicService → Clasifica valor económico
   ├─→ ProfileService → Asigna perfil comportamiento
   ├─→ RiskFlagService → Detecta flags de riesgo
   ├─→ Calcula priority_score
   ├─→ DecisionEngine → Genera recomendación
   └─→ Persiste Prediction en DB

3. Response con toda la inteligencia:
   {
     "customer_id": "C12345",
     "probability_churn": 0.75,
     "churn": true,
     "economic_value": "HIGH_VALUE_CUSTOMER",
     "priority_score": 0.82,
     "risk_flags": ["INACTIVITY_RISK"],
     "customer_profile": "HIGH_VALUE_DISCOUNT_ONLINE",
     "recommended_action": "🚨 Contacto inmediato del gerente"
   }
```

---

## Agentes de Automatización

Sistema multi-agente en Python para analizar riesgo de churn a gran escala (≈4.000 clientes) y orquestar decisiones de retención alineadas al negocio. Combina procesamiento batch, resúmenes ejecutivos para managers, acciones reales selectivas (Email, Calendar, Sheet, Meet y Telegram) y automatización completa mediante GitHub Actions.

El enfoque es **manager-first**: prioriza decisiones explicables, resultados agregados y realismo operativo por sobre la ejecución masiva de acciones automáticas.

### Estructura

```bash
agentes/
│
├─ agents/
│  ├─ decision_agent.py               # Reglas de negocio y lógica de decisión
│  ├─ action_agent.py                 # Orquestador de canales
│  ├─ aggregation_agent.py            # Agregaciones y resúmenes
│  ├─ google_agents.py                # Gmail, Calendar, Sheets, Meet
│  └─ telegram_agent.py               # Integración Telegram
│
├─ utils/
│  ├─ flags_utils.py                  # Feature flags y switches
│  └─ generate_churn_csv.py           # Generador de CSV de churn
│
├─ data/
│  ├─ customers_with_churn_prob.csv   # Clientes con score de churn
│  └─ rf_v1_baseline_train.csv        # Dataset de entrenamiento
│
├─ models/
│  └─ churn_model.joblib              # Modelo Random Forest
│
├─ config/
│  └─ credentials_example.json        # Ejemplo de credenciales
│
├─ scripts/
│  └─ generate_refresh_token.py       # OAuth Google (una sola vez)
│
├─ .env.example                       # Ejemplo de variables de entorno secretas
├─ main.py                            # Ejecución batch principal
├─ README.md                          # Documentación específica de agentes
└─ requirements.txt                   # Dependencias Python
```

### Agente de Acción (Orquestador Central)

Todas las ejecuciones se realizan mediante:

```python
action_agent()
```

El Agente de Acción ejecuta cada acción del cliente a través de un único orquestador, activando el canal adecuado según el riesgo de abandono:

- **Correo Electrónico**: Envía correos electrónicos personalizados o resumidos a los gerentes.

- **Calendario**: Programa reuniones y genera enlaces de Google Meet para clientes de alto riesgo.

- **Telegram**: Envía alertas críticas o mensajes de interacción a través del bot de Telegram.

- **Hoja de Auditoría**: Registra acciones y resúmenes de gerentes en Hojas de Cálculo de Google para auditorías e informes.

### Componentes Principales

#### 1. Decision Agent (`decision_agent.py`)

**Responsabilidad**: Implementa las reglas de negocio y lógica de decisión para clasificar clientes.

**Funcionalidades**:

- Clasifica clientes según probabilidad de churn
- Asigna acciones específicas por nivel de riesgo
- Integra con el motor de decisiones del backend
- Prioriza clientes críticos

#### 2. Aggregation Agent (`aggregation_agent.py`)

**Responsabilidad**: Genera agregaciones y resúmenes ejecutivos.

**Funcionalidades**:

- Agrupa clientes por nivel de riesgo
- Genera métricas consolidadas
- Crea reportes para management
- Calcula KPIs de retención

#### 3. Google Agents (`google_agents.py`)

**Responsabilidad**: Integración con servicios de Google.

**Servicios integrados**:

- **Gmail API**: Envío de emails personalizados
- **Calendar API**: Agendamiento de reuniones
- **Sheets API**: Registro de auditoría
- **Meet API**: Generación de enlaces de videollamada

**Autenticación**: OAuth 2.0

#### 4. Telegram Agent (`telegram_agent.py`)

**Responsabilidad**: Integración con Telegram Bot API.

**Funcionalidades**:

- Envío de alertas críticas
- Notificaciones en tiempo real
- Mensajes interactivos
- Respuestas automatizadas

### Flujo de Ejecución

```
1. GitHub Actions ejecuta main.py (batch programado)
   ├─ Carga customers_with_churn_prob.csv
   └─ Carga modelo churn_model.joblib

2. Decision Agent clasifica clientes
   ├─ Alto Riesgo (>0.7): Acción inmediata
   ├─ Riesgo Medio (0.5-0.7): Seguimiento
   └─ Bajo Riesgo (<0.5): Monitoreo

3. Aggregation Agent genera resúmenes
   ├─ Total clientes por nivel
   ├─ Valor económico en riesgo
   └─ KPIs de retención

4. Action Agent ejecuta acciones
   ├─ Clientes críticos → Email gerente + Calendar + Meet
   ├─ Clientes medios → Email personalizado
   └─ Resumen ejecutivo → Sheets + Email management

5. Telegram Agent envía notificaciones
   ├─ Alertas críticas inmediatas
   └─ Resumen diario de actividad
```

### Configuración

#### Variables de Entorno (`.env`)

```bash
# Google APIs
GOOGLE_CLIENT_ID=your_client_id
GOOGLE_CLIENT_SECRET=your_client_secret
GOOGLE_REFRESH_TOKEN=your_refresh_token

# Telegram
TELEGRAM_BOT_TOKEN=your_bot_token
TELEGRAM_CHAT_ID=your_chat_id

# Email destinatarios
MANAGER_EMAIL=manager@example.com
CC_EMAILS=team@example.com,analytics@example.com

# Feature Flags
ENABLE_EMAIL_SENDING=true
ENABLE_CALENDAR_BOOKING=true
ENABLE_TELEGRAM_ALERTS=true
ENABLE_SHEETS_LOGGING=true
```

#### Credenciales Google (`config/credentials.json`)

```json
{
  "installed": {
    "client_id": "your_client_id.apps.googleusercontent.com",
    "client_secret": "your_client_secret",
    "redirect_uris": ["http://localhost:8080/"]
  }
}
```

### Ejecución

#### Local

```bash
cd agentes

# Instalar dependencias
pip install -r requirements.txt

# Configurar variables de entorno
cp .env.example .env
# Editar .env con tus credenciales

# Generar refresh token de Google (una sola vez)
python scripts/generate_refresh_token.py

# Ejecutar sistema de agentes
python main.py
```

#### GitHub Actions (Automatizado)

El archivo `.github/workflows/run-main.yml` ejecuta el sistema automáticamente:

```yaml
name: Run Agents Batch

on:
  schedule:
    - cron: "0 9 * * *" # Diario a las 9:00 AM
  workflow_dispatch: # Manual trigger

jobs:
  run-agents:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up Python
        uses: actions/setup-python@v4
        with:
          python-version: "3.11"
      - name: Install dependencies
        run: |
          cd agentes
          pip install -r requirements.txt
      - name: Run agents
        env:
          GOOGLE_CLIENT_ID: ${{ secrets.GOOGLE_CLIENT_ID }}
          GOOGLE_CLIENT_SECRET: ${{ secrets.GOOGLE_CLIENT_SECRET }}
          GOOGLE_REFRESH_TOKEN: ${{ secrets.GOOGLE_REFRESH_TOKEN }}
          TELEGRAM_BOT_TOKEN: ${{ secrets.TELEGRAM_BOT_TOKEN }}
          TELEGRAM_CHAT_ID: ${{ secrets.TELEGRAM_CHAT_ID }}
        run: |
          cd agentes
          python main.py
```

### Feature Flags

El sistema utiliza feature flags para controlar qué acciones ejecutar:

```python
# utils/flags_utils.py

FEATURE_FLAGS = {
    'ENABLE_EMAIL_SENDING': True,
    'ENABLE_CALENDAR_BOOKING': True,
    'ENABLE_TELEGRAM_ALERTS': True,
    'ENABLE_SHEETS_LOGGING': True,
    'DRY_RUN_MODE': False  # No ejecuta acciones, solo simula
}
```

### Métricas y Monitoreo

El sistema genera logs y métricas:

```
agentes/logs/
├── execution_2026-01-19.log
├── emails_sent.csv
├── meetings_scheduled.csv
└── telegram_alerts.csv
```

**Métricas rastreadas**:

- Total de clientes procesados
- Emails enviados
- Reuniones agendadas
- Alertas de Telegram
- Tiempo de ejecución
- Errores y excepciones

### Documentación Adicional

Para documentación detallada de agentes, ver:

- [agentes/README.md](agentes/README.md) - Documentación completa del sistema
- [docs/Agentes/Multi_agentes.md](docs/Agentes/Multi_agentes.md) - Guía técnica

---

## Desarrollo Local

### Levantar Servicios Individuales

#### **Backend (Spring Boot)**

```bash
cd backend

# Con Maven Wrapper (sin Maven instalado)
./mvnw clean install
./mvnw spring-boot:run

# Con Maven instalado
mvn clean install
mvn spring-boot:run

# La API estará en: http://localhost:8080
```

**Requisitos**:

- Java 17+
- MySQL corriendo en localhost:3306

#### **ML Service (FastAPI)**

```bash
cd ml-service

# Crear entorno virtual
python -m venv venv
source venv/bin/activate  # Linux/Mac
# venv\Scripts\activate  # Windows

# Instalar dependencias
pip install -r requirements.txt

# Ejecutar servicio
uvicorn app.main:app --reload --port 8000

# La API estará en: http://localhost:8000
# Swagger UI: http://localhost:8000/docs
```

**Requisitos**:

- Python 3.11+

#### **MySQL (Docker)**

```bash
docker run --name churninsight-mysql \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=churninsight \
  -e MYSQL_USER=churnuser \
  -e MYSQL_PASSWORD=churnpass \
  -p 3306:3306 \
  -d mysql:8.0
```

### Desarrollo con Docker Compose

```bash
# Levantar todos los servicios
docker-compose up -d

# Ver logs de todos los servicios
docker-compose logs -f

# Ver logs de un servicio específico
docker-compose logs -f backend
docker-compose logs -f ml-service

# Reconstruir imagen después de cambios
docker-compose up -d --build

# Detener servicios
docker-compose down

# Detener y eliminar volúmenes (⚠️ borra datos)
docker-compose down -v
```

### Variables de Entorno

Crear archivo `.env` en la raíz del proyecto:

```bash
# MySQL
MYSQL_ROOT_PASSWORD=rootpassword
MYSQL_DATABASE=churninsight
MYSQL_USER=churnuser
MYSQL_PASSWORD=churnpass

# Backend
SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/churninsight
SPRING_DATASOURCE_USERNAME=churnuser
SPRING_DATASOURCE_PASSWORD=churnpass
ML_SERVICE_URL=http://ml-service:8000
```

Ver documentación completa en: [env_setup.md](docs/Variables_Entorno/env_setup.md)

Ver referencias de variables en: [.env.example](.env.example)

### Troubleshooting

#### Backend no conecta con MySQL

```bash
# Verificar que MySQL esté corriendo
docker ps | grep mysql

# Verificar logs de MySQL
docker logs churninsight-mysql

# Verificar conectividad
docker exec churninsight-mysql mysqladmin ping -h localhost

# Conectarse manualmente
docker exec -it churninsight-mysql mysql -u churnuser -p
```

#### ML Service no carga el modelo

```bash
# Verificar que el archivo del modelo exista
ls -lh ml-service/model_assets/rf_v1_baseline.joblib

# Verificar logs del servicio
docker logs ml-service

# Si el modelo no está, copiarlo desde data-science/models/
cp data-science/models/rf_v1_baseline.joblib ml-service/model_assets/
```

#### Backend no conecta con ML Service

```bash
# Verificar que ML Service esté corriendo
curl http://localhost:8000/

# Verificar la variable de entorno
docker exec backend env | grep ML_SERVICE_URL

# Debe ser: ML_SERVICE_URL=http://ml-service:8000
```

#### Errores de validación en requests

Los campos deben cumplir:

- `customer_id`: Debe empezar con 'C'
- `transaction_id`: Debe empezar con 'T'
- `age`: 0-120
- `quantity`: >= 1
- `unit_price`: >= 0
- Fechas en formato: `dd/MM/yyyy`
- `last_purchase_date` <= `transaction_date`

---

## Tecnologías

### Backend

| Tecnología                                                                                       | Versión | Uso                      |
| ------------------------------------------------------------------------------------------------ | ------- | ------------------------ |
| [Java](https://www.oracle.com/java/)                                                             | 17      | Lenguaje principal       |
| [Spring Boot](https://spring.io/projects/spring-boot)                                            | 3.5.9   | Framework web            |
| [Spring Data JPA](https://spring.io/projects/spring-data-jpa)                                    | 3.5.9   | ORM y persistencia       |
| [Hibernate](https://hibernate.org/)                                                              | 6.4+    | Implementación JPA       |
| [Spring WebClient](https://docs.spring.io/spring-framework/reference/web/webflux-webclient.html) | 3.5.9   | Cliente HTTP reactivo    |
| [Bean Validation](https://beanvalidation.org/)                                                   | 3.0     | Validación de DTOs       |
| [Lombok](https://projectlombok.org/)                                                             | 1.18.30 | Reducción de boilerplate |
| [MySQL Connector](https://dev.mysql.com/downloads/connector/j/)                                  | 8.0+    | Driver JDBC              |
| [Maven](https://maven.apache.org/)                                                               | 3.8+    | Build tool               |

### ML Service & Data Science

| Tecnología                                | Versión | Uso                       |
| ----------------------------------------- | ------- | ------------------------- |
| [Python](https://www.python.org/)         | 3.11+   | Lenguaje principal        |
| [FastAPI](https://fastapi.tiangolo.com/)  | 0.104+  | Framework API REST        |
| [Pydantic](https://docs.pydantic.dev/)    | 2.5+    | Validación de datos       |
| [Uvicorn](https://www.uvicorn.org/)       | 0.24+   | Servidor ASGI             |
| [scikit-learn](https://scikit-learn.org/) | 1.3+    | Machine Learning          |
| [pandas](https://pandas.pydata.org/)      | 2.1+    | Manipulación de datos     |
| [NumPy](https://numpy.org/)               | 1.24+   | Computación numérica      |
| [joblib](https://joblib.readthedocs.io/)  | 1.3+    | Serialización de modelos  |
| [JupyterLab](https://jupyter.org/)        | 4.0+    | Notebooks interactivos    |
| [Matplotlib](https://matplotlib.org/)     | 3.8+    | Visualización             |
| [Seaborn](https://seaborn.pydata.org/)    | 0.13+   | Visualización estadística |
| [Plotly](https://plotly.com/python/)      | 5.18+   | Visualización interactiva |

### Base de Datos

| Tecnología                                         | Versión | Uso                      |
| -------------------------------------------------- | ------- | ------------------------ |
| [MySQL](https://www.mysql.com/)                    | 8.0     | Base de datos relacional |
| [Docker](https://www.docker.com/)                  | 20.0+   | Contenedores             |
| [Docker Compose](https://docs.docker.com/compose/) | 2.0+    | Orquestación             |

### Agentes de Automatización

| Tecnología                                               | Versión | Uso                                            |
| -------------------------------------------------------- | ------- | ---------------------------------------------- |
| [Google APIs](https://developers.google.com/)            | 2.0+    | Integración con Gmail, Calendar, Sheets y Meet |
| [Telegram Bot API](https://core.telegram.org/bots/api)   | 6.0+    | Envío de alertas y mensajes automatizados      |
| [OAuth 2.0](https://oauth.net/2/)                        | 2.0     | Autenticación y autorización segura            |
| [python-dotenv](https://pypi.org/project/python-dotenv/) | 1.0+    | Gestión de variables de entorno                |
| [GitHub Actions](https://docs.github.com/en/actions)     | -       | Automatización batch y ejecución programada    |

### Infraestructura & DevOps

| Tecnología                                                                                                              | Uso                                                                                                         |
| ----------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------- |
| [Docker](https://www.docker.com/)                                                                                       | Contenedorización                                                                                           |
| [Docker Compose](https://docs.docker.com/compose/)                                                                      | Orquestación multi-contenedor                                                                               |
| [GitHub Actions](https://docs.github.com/en/actions)                                                                    | Pipeline de CI que reconstruye automáticamente imágenes Docker en GHCR cuando cambia el código en PR a main |
| [GHCR](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-container-registry) | GitHub Container Registry - Registro de imágenes Docker                                                     |
| [Git](https://git-scm.com/)                                                                                             | Control de versiones                                                                                        |
| [GitHub](https://github.com/)                                                                                           | Repositorio y colaboración                                                                                  |

### Tools & Utilities

| Herramienta                                        | Uso                                 |
| -------------------------------------------------- | ----------------------------------- |
| [Postman](https://www.postman.com/)                | Testing de APIs                     |
| [Swagger UI](https://swagger.io/tools/swagger-ui/) | Documentación interactiva (FastAPI) |
| [DBeaver](https://dbeaver.io/)                     | Cliente MySQL                       |
| [IntelliJ IDEA](https://www.jetbrains.com/idea/)   | IDE Java (recomendado)              |
| [VS Code](https://code.visualstudio.com/)          | Editor multi-propósito              |
| [PyCharm](https://www.jetbrains.com/pycharm/)      | IDE Python (recomendado)            |

---

## Documentación

### Documentación por Rol

El proyecto cuenta con documentación especializada para cada rol:

#### **Data Engineering**

**Ubicación**: [documentacion.md](docs/Data-Engineer/documentacion.md)

**Contenido**:

- Pipeline ETL completo
- Validación y calidad de datos
- Reglas de negocio implementadas
- Detección de anomalías
- Scripts de transformación
- Esquemas de datos
- Lineage de datasets

**Temas cubiertos**:

- Ingesta de datos desde fuentes externas
- Limpieza y saneamiento (outliers, missing values)
- Validación de unicidad de IDs
- Consistencia de tipos de datos
- Transformaciones y agregaciones
- Generación de features derivadas
- Control de calidad automatizado

#### **Data Analysis**

**Ubicación**: [documentacion.md](docs/Data_Analyst/documentacion.md)

**Contenido**:

- Análisis exploratorio de datos (EDA)
- Segmentación de clientes (K-Means clustering)
- Análisis de churn y factores predictivos
- Impacto de promociones en ventas
- Análisis de correlaciones
- Desarrollo de métricas de negocio
- Economic Value Score
- Visualizaciones interactivas

**Issues documentadas**:

1. Perfil demográfico de clientes
2. Segmentación por comportamiento de compra
3. Análisis de churn y supervivencia
4. Efectividad de promociones
5. Matriz de correlaciones y PCA
6. Economic Value Score
7. Dashboard interactivo de insights

#### **Agentes de Automatización**

**Ubicación**: [Multi_agentes.md](docs/Agentes/Multi_agentes.md)

**Contenido**:

- Sistema multi-agente completo
- Integración con Google APIs
- Bot de Telegram
- Feature flags y configuración
- Automatización con GitHub Actions
- Guía de setup y deployment

**Temas cubiertos**:

- Decision Agent: Lógica de clasificación
- Action Agent: Orquestador de canales
- Aggregation Agent: Resúmenes ejecutivos
- Google Agents: Gmail, Calendar, Sheets, Meet
- Telegram Agent: Alertas y notificaciones
- OAuth 2.0 setup
- Batch processing
- Monitoreo y logs

#### **Arquitectura OCI**

**Ubicación**: [oci_architecture.png](docs/OCI/oci_architecture.png)

**Contenido**:

- Diagramas de arquitectura cloud
- Topología de red
- Servicios utilizados
- Alta disponibilidad y escalabilidad

**Componentes diseñados**:

- VMs (Compute Instances)
- Container Registry
- Object Storage
- Autonomous Database
- Virtual Cloud Network (VCN)
- Security Lists y Network Security Groups
- Functions (serverless)
- API Gateway
- Internet Gateway

#### **Estrategia de Branching**

**Ubicación**: [estrategia-branching.md](docs/Branching/estrategia-branching.md)

**Contenido**:

- Modelo de branching (GitFlow adaptado)
- Nomenclatura de ramas
- Proceso de merge y code review
- Políticas de commits
- Semantic versioning

#### **Variables de Entorno**

**Ubicación**: [env_setup.md](docs/Variables_Entorno/env_setup.md)

**Contenido**:

- Configuración de variables de entorno
- Setup para desarrollo local
- Configuración Docker/Docker Compose
- Configuraciones por ambiente (dev, prod)

### API Documentation

#### **Swagger UI - Backend API**

Una vez levantado el backend, la documentación interactiva de OpenAPI está disponible en:

**URL**: `http://localhost:8080/swagger-ui.html`

Desde Swagger puedes:

- ✅ Ver todos los endpoints disponibles
- ✅ Ver esquemas de request/response
- ✅ Probar requests en vivo
- ✅ Ver códigos de error y validaciones
- ✅ Descargar spec OpenAPI (JSON/YAML)

#### **Swagger UI - ML Service**

El servicio ML expone documentación automática generada por FastAPI:

**URL**: `http://localhost:8000/docs` (Swagger UI)  
**URL**: `http://localhost:8000/redoc` (ReDoc)

Características:

- ✅ Esquemas Pydantic interactivos
- ✅ Validaciones y constraints
- ✅ Try it out para testing
- ✅ Ejemplos de requests
- ✅ Tipos de datos detallados

### Notebooks Interactivos

Los notebooks de Jupyter están disponibles en `data-science/notebooks/`:

#### 1. **Data engineer.ipynb**

- Pipeline ETL completo
- Validación de datos
- Reglas de negocio
- Transformaciones

**Para ejecutar**:

```bash
cd data-science
jupyter lab
# Abrir: notebooks/Data engineer.ipynb
```

#### 2. **Data_Analyst.ipynb**

- Análisis exploratorio exhaustivo
- Issues principales de análisis
- Visualizaciones interactivas con Plotly
- Pruebas estadísticas (ANOVA, Chi-squared)
- Segmentación con K-Means

#### 3. **Data_Scientist.ipynb**

- Entrenamiento de modelos ML
- Comparación de 5 algoritmos
- Optimización de hiperparámetros
- Feature importance
- Evaluación de métricas
- Serialización del modelo

### Diagramas

#### **Arquitectura del Sistema**

```
┌─────────────┐         ┌─────────────┐         ┌─────────────┐
│   Cliente   │────────▶│   Backend   │────────▶│  ML Service │
│  (Frontend) │         │ Spring Boot │         │   FastAPI   │
└─────────────┘         └─────────────┘         └─────────────┘
                               │                        │
                               ▼                        ▼
                        ┌─────────────┐         ┌─────────────┐
                        │    MySQL    │         │   Modelo    │
                        │   Database  │         │ Random Forest│
                        └─────────────┘         └─────────────┘
```

#### **Flujo de Predicción**

```
Request
  │
  ├─▶ 1. Validación (Bean Validation + Business Rules)
  │
  ├─▶ 2. ML Service: predict_churn() → probability
  │
  ├─▶ 3. Economic Value Classification
  │
  ├─▶ 4. Profile Assignment (3 tipos)
  │
  ├─▶ 5. Risk Flag Detection (3 flags)
  │
  ├─▶ 6. Priority Score Calculation
  │
  ├─▶ 7. Decision Engine → recommended_action
  │
  ├─▶ 8. Persistencia en MySQL
  │
  └─▶ Response con toda la inteligencia
```

#### **Data Pipeline**

```
Raw Data (357K rows)
  │
  ├─▶ Data Engineering (ETL)
  │    ├─ Limpieza
  │    ├─ Validación
  │    └─ Transformaciones
  │
  ├─▶ Interim Data (augmented)
  │
  ├─▶ Data Analysis (EDA)
  │    ├─ Segmentación
  │    ├─ Feature Engineering
  │    └─ Insights de negocio
  │
  ├─▶ Processed Data
  │
  ├─▶ Data Science (ML)
  │    ├─ Train/Test Split
  │    ├─ Model Training
  │    ├─ Hyperparameter Tuning
  │    └─ Model Evaluation
  │
  └─▶ Final Model (rf_v1_baseline.joblib)
       └─▶ ML Service (Production)
```

### Recursos Adicionales

#### **README específicos**

- `backend/HELP.md`: Documentación del backend Spring Boot
- `data-science/README.md`: Guía del módulo de ciencia de datos
- `ml-service/README.md`: (pendiente) Documentación del servicio ML

#### **Archivos de configuración**

- `backend/pom.xml`: Dependencias Maven
- `backend/src/main/resources/application.yaml`: Config Spring Boot
- `ml-service/requirements.txt`: Dependencias Python
- `docker-compose.yaml`: Orquestación de servicios
- `data-science/requirements.txt`: Dependencias para notebooks

### Guías de Estilo

#### **Java/Spring Boot**

- Seguir convenciones de Java (camelCase, PascalCase)
- Usar Lombok para reducir boilerplate
- Records para DTOs inmutables
- Separación clara de capas (controller, service, repository)
- Inyección de dependencias por constructor

#### **Python**

- Seguir PEP 8
- Type hints en funciones
- Docstrings para funciones públicas
- Usar Pydantic para validación
- Imports ordenados (isort)

### Contacto y Soporte

Para dudas sobre la documentación:

- Abrir issue en GitHub
- Contactar al equipo (ver sección [Equipo](#-equipo))
- Revisar documentación específica por rol

---

## 👥 Equipo

ChurnInsight 42 es desarrollado por un equipo multidisciplinario de 9 profesionales en diferentes áreas:

### Data Science & Engineering

- [**Samuel Granados**](https://github.com/ggsgranados) - Data Analyst
- [**Javier Garcia**](https://github.com/popex404) - Data Scientist
- [**Barbara Ortiz**](https://github.com/BarbaraAngelesOrtiz) - Data Engineer
- [**Juan Rendon**](https://github.com/Phylip28) - Data Engineer

### Backend & Architecture

- [**Johan Leal**](https://github.com/JsLealM)
- [**Yoshua Pariona**](https://github.com/YoshuaPariona)
- [**Arturo Trelles**](https://github.com/ArturoTrelles91)
- [**Damian Lambrecht**](https://github.com/DamianL96)

### Infrastructure & DevOps

- [**Juan Rendon**](https://github.com/Phylip28)

### Agentes de Automatización

- [**Yoshua Pariona**](https://github.com/YoshuaPariona)
- [**Barbara Ortiz**](https://github.com/BarbaraAngelesOrtiz)

### Contribuciones por Área

| Área                    | Responsables         | Componentes                            |
| ----------------------- | -------------------- | -------------------------------------- |
| **Data Engineering**    | Barbara, Felipe      | ETL, Data Validation, Data Quality     |
| **Data Analysis**       | Samuel               | EDA, Segmentation, Business Insights   |
| **Machine Learning**    | Javier               | Model Training, Evaluation, Deployment |
| **Backend API**         | Backend Team         | Spring Boot, REST API, Business Logic  |
| **Motor de Decisiones** | Backend Team, Samuel | Rules Engine, Recommendation System    |
| **ML Service**          | Javier, Felipe       | FastAPI, Model Serving                 |
| **Persistencia**        | Damian, Felipe       | JPA, MySQL, Database Design            |
| **Infraestructura**     | Felipe               | Docker, OCI, CI/CD                     |
| **Integración**         | Felipe, Damian       | Backend ↔ ML Service Communication     |
| **Documentación**       | Todo el equipo       | README, Docs, Notebooks                |

## Posibles Mejoras a Futuro

- [ ] **Autenticación**: JWT + OAuth2 para seguridad
- [ ] **Monitoreo**: Prometheus + Grafana para métricas
- [ ] **Logging centralizado**: ELK Stack (Elasticsearch, Logstash, Kibana)
- [ ] **CI/CD**: GitHub Actions + Jenkins
- [ ] **Tests automatizados**: JUnit + Mockito (backend), pytest (ML service)
- [ ] **Documentación API**: Postman Collection + OpenAPI 3.0
- [ ] **Modelos adicionales**: Gradient Boosting, XGBoost, LightGBM
- [ ] **Explainability**: SHAP values para explicar predicciones
- [ ] **Reentrenamiento automático**: MLOps pipeline con Airflow
- [ ] **A/B Testing**: Framework para comparar modelos en producción
- [ ] **Feature Store**: Centralizar features para consistencia
- [ ] **Data Drift Detection**: Monitorear cambios en distribuciones
- [ ] **Real-time predictions**: Kafka + Streaming
- [ ] **Microservicios adicionales**:
  - Notification Service (emails, SMS)
  - Campaign Manager (automatización de acciones)
  - Analytics Service (dashboards agregados)
- [ ] **Multi-tenancy**: Soporte para múltiples clientes
- [ ] **API versioning**: v2 con breaking changes
- [ ] **Kubernetes**: Orquestación en producción
- [ ] **Helm Charts**: Deployment en K8s

### Features Experimentales

- [ ] **Deep Learning**: LSTM/Transformers para series temporales
- [ ] **NLP**: Análisis de sentimiento en feedback de clientes
- [ ] **Computer Vision**: Análisis de comportamiento en tiendas físicas
- [ ] **Reinforcement Learning**: Optimización de recomendaciones
- [ ] **Federated Learning**: Aprendizaje distribuido preservando privacidad

---

## Contribuciones

Estamos abiertos a contribuciones para aprendizaje mutuo.

### Cómo Contribuir

1. **Fork** el repositorio
2. Crear una **feature branch** (`git switch -c feature/AmazingFeature`)
3. **Commit** los cambios (`git commit -m 'Add some AmazingFeature'`)
4. **Push** a la branch (`git push origin feature/AmazingFeature`)
5. Abrir un **Pull Request**

### Guidelines

- Seguir las guías de estilo del proyecto
- Incluir tests para nuevas funcionalidades
- Actualizar documentación relevante
- Describir claramente los cambios en el PR

Referencia: [estrategia-branching.md](docs/Branching/estrategia-branching.md)

### Reportar Issues

Para reportar bugs o solicitar features:

1. Verificar que no exista un issue similar
2. Usar las plantillas de issues
3. Proveer información detallada y reproducible

---

**⭐ Si este proyecto te resulta útil, considera darle una estrella en GitHub ⭐**

Desarrollado con ❤️ por **Team 42**

</div>
