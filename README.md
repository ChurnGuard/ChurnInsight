# ChurnInsight 42

ChurnInsight es una solución completa de predicción de churn (abandono de clientes), que combina ingeniería de datos, ciencia de datos y desarrollo backend para identificar clientes con alta probabilidad de cancelar un servicio.

La solución está pensada para empresas con modelos de suscripción o contratos recurrentes (fintech, telecomunicaciones, streaming, e-commerce) que desean anticiparse a la cancelación de clientes y tomar acciones preventivas.

## Estructura del Proyecto

```
ChurnInsight/
├── backend/          # API REST en Spring Boot (Java)
├── data-science/     # Pipeline de datos y análisis exploratorio
├── ml-service/       # Servicio de ML con FastAPI
├── frontend/         # Interfaz de usuario
├── infra/           # Infraestructura y despliegue
└── docs/            # Documentación técnica
```

## Instalación

```bash
git clone https://github.com/ChurnGuard/ChurnInsight.git
```

## Data Science & ML Pipeline

### Estructura del Módulo de Datos

El directorio `data-science/` contiene todo el pipeline de ingeniería y análisis de datos:

```
data-science/
├── data/
│   ├── raw/                    # Datos originales sin procesar
│   ├── interim/                # Datos en proceso de transformación
│   ├── processed/              # Datos procesados y validados
│   └── final/                  # Datasets listos para ML y análisis
├── notebooks/
│   ├── Data engineer.ipynb     # ETL, limpieza y validación de datos
│   └── Data_Analyst.ipynb      # EDA y análisis exploratorio
├── scripts/
│   ├── generar-datos-sinteticos.py  # Generación de datos sintéticos
│   └── validar-datasets.py          # Validación de calidad de datos
└── requirements.txt            # Dependencias de Python
```

### Pipeline de Datos

#### 1. Ingesta y Generación de Datos

El proyecto trabaja con un dataset de transacciones de clientes de supermercado que incluye:

- **357,590 transacciones** con 26 variables
- Datos demográficos (edad, género, educación, ocupación)
- Datos transaccionales (compras, frecuencia, categorías de productos)
- Métricas de comportamiento (valor promedio, descuentos, días desde última compra)
- Variable objetivo: `churn` (1 = abandonó, 0 = activo)

**Script de generación de datos sintéticos:**

```bash
cd data-science
python scripts/generar-datos-sinteticos.py
```

#### 2. Validación y Calidad de Datos

**Script de validación:**

```bash
python scripts/validar-datasets.py
```

Este script verifica:

- ✅ Unicidad de clientes
- ✅ Ausencia de valores nulos
- ✅ Consistencia entre datasets

#### 3. Notebooks de Análisis

**Data Engineer Notebook** (`Data engineer.ipynb`):

- Limpieza y saneamiento de datos
- Detección de outliers y valores inconsistentes
- Validación de reglas de negocio
- Transformaciones ETL
- Preparación de datasets finales

**Data Analyst Notebook** (`Data_Analyst.ipynb`):

- EDA (Exploratory Data Analysis)
- Análisis de patrones de churn
- Segmentación de clientes
- Visualizaciones y métricas clave
- Feature engineering

### Datasets Generados

| Dataset       | Ubicación                                                | Descripción                                |
| ------------- | -------------------------------------------------------- | ------------------------------------------ |
| **Raw**       | `data/raw/Grocery_Customer_Churn_Data.csv`               | Dataset original sin procesar              |
| **Augmented** | `data/interim/Grocery_Customer_Churn_Data_Augmented.csv` | Dataset con datos sintéticos agregados     |
| **Analyst**   | `data/final/dataset_analyst_by_customer.csv`             | Dataset agregado por cliente para análisis |
| **ML Ready**  | `data/final/customer_dataset_for_ml.csv`                 | Dataset preparado para modelos de ML       |

### Instalación del Entorno de Data Science

```bash
cd data-science
pip install -r requirements.txt
```

**Dependencias principales:**

- pandas >= 2.0
- numpy >= 1.24
- jupyterlab >= 4.0
- ipykernel >= 6.25
- pyyaml >= 6.0

### ML Service (FastAPI)

El servicio de ML expone el modelo de predicción mediante una API REST construida con FastAPI.

**Estructura:**

```
ml-service/
├── app/
│   ├── main.py              # API FastAPI
│   └── schemas/
│       └── prediction.py    # Esquemas Pydantic
├── model_assets/            # Modelos entrenados
└── requirements.txt         # Dependencias
```

**Instalación y ejecución:**

```bash
cd ml-service
pip install -r requirements.txt
uvicorn app.main:app --reload --port 8001
```

**Endpoint de predicción:**

```http
POST /predict
Content-Type: application/json

{
  "age": 35,
  "membership_years": 3,
  "purchase_frequency": 15,
  "avg_purchase_value": 125.50,
  ...
}
```

**Respuesta:**

```json
0.81
```

**Health check:**

```http
GET /
```

```json
{
  "status": "ok",
  "service": "ml-service-v1"
}
```

## API Backend (Spring Boot)

### Endpoints Backend

**POST** `/predict`

Recibe los datos de un cliente y devuelve la predicción de churn (cancelación del servicio) junto con su probabilidad.

#### Request

```json
{
  "tiempo_contrato_meses": 12,
  "retrasos_pago": 2,
  "uso_mensual": 14.5,
  "plan": "Premium"
}
```

#### Response

```json
{
  "prevision": "Va a cancelar",
  "probabilidad": 0.81
}
```

## Tecnologías

### Backend

- [Spring Boot](https://spring.io/) - Framework Java
- [Hibernate](https://hibernate.org/) - ORM

### Data Science & ML

- [Python](https://www.python.org/) - Lenguaje principal para análisis y ML
- [Pandas](https://pandas.pydata.org/) - Manipulación y análisis de datos
- [NumPy](https://numpy.org/) - Computación científica
- [JupyterLab](https://jupyter.org/) - Notebooks interactivos
- [FastAPI](https://fastapi.tiangolo.com/) - Framework para ML service
- [Pydantic](https://docs.pydantic.dev/) - Validación de datos

## Documentación

### Documentación Técnica

- **Data Engineering**: Ver [`docs/Data-Engineer/documentacion.md`](docs/Data%20Engineer.md) para detalles del pipeline ETL, validación de datos y reglas de negocio
- **Arquitectura OCI**: Diagramas de infraestructura en [`docs/OCI/`](docs/OCI/)
- **Estrategia de Branching**: Ver [`docs/branching/estrategia-branching.md`](docs/branching/estrategia-branching.md)

### API Documentation (Swagger)

La documentación interactiva está disponible vía Swagger (OpenAPI) una vez levantada la aplicación.

Desde allí se pueden:

- Ver los endpoints disponibles
- Probar requests en vivo
- Consultar modelos de datos y schemas

## 👥 Participantes

- [Samuel Granados](https://github.com/ggsgranados)
- [Johan Leal](https://github.com/JsLealM)
- [Javier Garcia](https://github.com/popex404)
- [Barbara Ortiz](https://github.com/BarbaraAngelesOrtiz)
- [Yoshua Pariona](https://github.com/YoshuaPariona)
- [Arturo Trelles](https://github.com/ArturoTrelles91)
- [Jhony Rodriguez](https://github.com/jhonyaldo)
- [Juan Rendon](https://github.com/Phylip28)
- [Cristian Pinzon](https://github.com/Crispis723)
- [Damian Lambrecht](https://github.com/DamianL96)
