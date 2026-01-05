# 📌 Grocery Customer Churn (Abandono de Clientes de Supermercado)

## Customer Transactions, Data Engineering & Data Quality Pipeline

Proyecto de análisis, validación y saneamiento de un dataset transaccional de clientes, enfocado en detección de errores sistémicos, reglas de negocio y preparación para analítica avanzada.

---

## Acerca del conjunto de datos

Este proyecto trabaja con un conjunto de datos que representa transacciones, comportamiento de compra y atributos demográficos de clientes de una tienda de comestibles. El dataset combina información realista con datos parcialmente sintéticos, diseñados para simular escenarios comunes en sistemas transaccionales y analíticos a escala.

El conjunto de datos está orientado a casos de uso analíticos y predictivos, como la predicción de abandono de clientes (churn) y el análisis del Customer Lifetime Value (CLV), y presenta intencionalmente desafíos típicos de calidad de datos que requieren validación y saneamiento previo al modelado.

La variable objetivo para el análisis de abandono es churn, que indica si un cliente ha abandonado el servicio (1) o permanece activo (0). A partir de esta variable, el dataset permite analizar patrones de retención, comportamiento de compra y evolución del valor del cliente en el tiempo.

### Principales grupos de variables

* Información del cliente:
Incluye atributos demográficos como edad, antigüedad de membresía, número de hijos y otras características utilizadas para segmentación y análisis descriptivo.

* Datos transaccionales:
Contiene información detallada de las compras realizadas, como fechas de transacción, cantidades, precios unitarios y métricas agregadas de ventas.

* Métricas de comportamiento del cliente:
Variables derivadas como valor promedio de compra, frecuencia de compra y días desde la última transacción, utilizadas para capturar hábitos y patrones de consumo.

* Datos promocionales:
Información asociada al uso de descuentos y promociones, diseñada para evaluar su impacto en el comportamiento del cliente.

* Churn:
Columna objetivo que identifica el estado de abandono del cliente (1 = churn, 0 = activo).

### Dimensión del conjunto de datos

* Filas: 357.590

* Columnas: 26 (incluida la variable objetivo churn)

---

## Objetivo y alcance del proyecto

Este proyecto se centra en tareas propias del rol de Data Engineer, con foco en la evaluación de la calidad de datos, la detección de inconsistencias sistémicas y la preparación del dataset para analítica avanzada y modelado predictivo.

El alcance del estudio incluye:

* Ingesta y tratamiento de datos provenientes de una API externa, considerando escenarios reales de datos incompletos o inconsistentes.

* Diseño e implementación de un proceso ETL para la limpieza, validación y organización de la información, priorizando reglas reproducibles y escalables.

* Ejecución de un EDA técnico orientado a calidad de datos, enfocado en la detección de valores faltantes, valores negativos inválidos, outliers y errores derivados de lógica de negocio.

* Identificación y clasificación de problemas de datos, diferenciando errores sistémicos, errores puntuales y comportamientos válidos del cliente.

* Desarrollo de visualizaciones clave como soporte para la toma de decisiones de limpieza y validación, evitando transformaciones automáticas sin criterio de negocio.

* Elaboración de un reporte técnico de hallazgos, dejando el dataset preparado para:

1. Análisis exploratorio profundo.

2. Feature engineering.

3. Modelos futuros de churn, CLV y segmentación de clientes.

---

## Dataset overview

| Tipo            | Columna                    | Tipo          | Descripción                                                     |
| --------------- | -------------------------- | ------------- | --------------------------------------------------------------- |
| Identificador   | `customer_id`              | Identificador | Identificador único del cliente                                 |
| Identificador   | `transaction_id`           | Identificador | Identificador único de la transacción                           |
| Temporales      | `transaction_date`         | Fecha         | Fecha en la que se realizó la transacción                       |
| Temporales      | `last_purchase_date`       | Fecha         | Fecha de la última compra registrada del cliente                |
| Demográficas    | `age`                      | Numérica      | Edad del cliente                                                |
| Demográficas    | `membership_years`         | Numérica      | Antigüedad del cliente en años                                  |
| Demográficas    | `number_of_children`       | Numérica      | Número de hijos del cliente                                     |
| Demográficas    | `gender`                   | Categórica    | Género del cliente                                              |
| Demográficas    | `income_bracket`           | Categórica    | Rango de ingresos del cliente                                   |
| Demográficas    | `marital_status`           | Categórica    | Estado civil del cliente                                        |
| Demográficas    | `education_level`          | Categórica    | Nivel educativo alcanzado                                       |
| Demográficas    | `occupation`               | Categórica    | Ocupación del cliente                                           |
| Demográficas    | `loyalty_program`          | Categórica    | Indica si el cliente pertenece a un programa de fidelización    |
| Transaccionales | `product_category`         | Categórica    | Categoría del producto adquirido                                |
| Transaccionales | `promotion_type`           | Categórica    | Tipo de promoción aplicada                                      |
| Transaccionales | `quantity`                 | Numérica      | Cantidad de unidades compradas en la transacción                |
| Transaccionales | `unit_price`               | Numérica      | Precio unitario del producto                                    |
| Transaccionales | `total_sales`              | Numérica      | Monto total de la venta                                         |
| Derivadas       | `avg_purchase_value`       | Numérica      | Valor promedio de compra del cliente                            |
| Derivadas       | `purchase_frequency`       | Numérica      | Frecuencia de compra del cliente                                |
| Derivadas       | `online_purchases`         | Numérica      | Número de compras realizadas por canal online                   |
| Derivadas       | `in_store_purchases`       | Numérica      | Número de compras realizadas en tienda física                   |
| Derivadas       | `avg_discount_used`        | Numérica      | Promedio de descuento utilizado por el cliente                  |
| Derivadas       | `total_transactions`       | Numérica      | Total de transacciones realizadas por el cliente                |
| Derivadas       | `total_items_purchased`    | Numérica      | Total de ítems comprados por el cliente                         |
| Derivadas       | `promotion_effectiveness`  | Numérica      | Métrica de efectividad de promociones                           |
| Derivadas       | `days_since_last_purchase` | Numérica      | Días transcurridos desde la última compra                       |
| Derivadas       | `churn`                    | Categórica    | Variable objetivo: abandono del cliente (1 = churn, 0 = activo)|

---

## Data Quality Assessment, Key Findings & Engineering Decisions

### Conteo de nulos

Se identifican valores faltantes principalmente en variables relacionadas con promociones, frecuencia de compra y métricas agregadas.

Las columnas con mayor cantidad de nulos son:

* promotion_type: 14,241 registros
* avg_purchase_value: 36,355 registros
* purchase_frequency: 19,941 registros
* total_sales: 34,785 registros


Esto indica que no todas las transacciones estuvieron asociadas a promociones, y que algunas métricas agregadas no se calcularon para todos los clientes. En el caso de total_sales, los nulos podrían reflejar inconsistencias o la ausencia de historial consolidado de ciertos clientes.

Estos valores faltantes no deben considerarse errores automáticamente, sino que requieren una decisión de negocio sobre su tratamiento: imputación, categorización explícita o exclusión según corresponda.

![Cantidad de ceros por variable](../ChurnInsight/docs/images/data engineer/cantidad de ceros por variable.png)

### Valores negativos

Al revisar las métricas del dataset, se identifican algunos valores negativos en variables que, en principio, deberían ser no negativas:

* days_since_last_purchase presenta 318 valores negativos, con un mínimo de -318.
* total_sales tiene 1,524 registros negativos, con un mínimo de -896.23.
* avg_purchase_value incluye 2 valores negativos, con un mínimo de -1.77.

Estos valores negativos podrían deberse a errores de registro, ajustes contables (como devoluciones o cancelaciones) o cálculos incorrectos en métricas derivadas.

No todas las variables muestran negativos, los valores negativos se concentran en métricas relacionadas con ventas, compras y tiempo desde la última transacción. Antes de su uso en análisis o modelos, se decidirá cómo tratarlos: corregirlos, imputarlos, excluirlos o interpretarlos según la lógica de negocio.

---

## Critical Data Quality Issues

1. Error sistémico

Son problemas recurrentes que afectan un conjunto amplio de datos de manera consistente y que requieren intervención en el cálculo o en la lógica de negocio.

* days_since_last_purchase: presenta 318 valores negativos y outliers extremos, indicando un error en el cálculo que debe corregirse usando transaction_date y last_purchase_date.
* purchase_frequency y promotion_effectiveness: valores nulos casi en su totalidad (≈100%), reflejando que la información no se generó; requieren recalculo o eliminación.

2. Error puntual

Problemas aislados que afectan algunos registros, generalmente por errores de ingreso o ajustes mal aplicados.

* avg_purchase_value: 36,355 valores nulos y 2 registros negativos (mín = -1.77), probablemente errores de cálculo o ajustes puntuales.
* total_sales: 34,785 nulos y 1,524 negativos (mín = -896.23), probablemente derivados de errores de registro o devoluciones mal modeladas.

3. Dato válido pero extremo

Valores que son técnicamente correctos pero representan casos atípicos o comportamiento esperable en ciertas situaciones.

* number_of_children: sin nulos ni negativos; alta proporción de ceros (≈61k), consistente con clientes sin hijos.
* quantity y unit_price: pocos outliers, plausibles por compras grandes o productos premium.
* online_purchases e in_store_purchases: alto número de ceros, reflejando clientes que compran solo por un canal.
* avg_discount_used: ceros y algunos outliers moderados, coherente con clientes que no aplican descuentos.

4. Variable inutilizable

Columnas que, en su estado actual, no aportan información y no pueden ser usadas sin recalculo.

* purchase_frequency y promotion_effectiveness: nulos en casi todos los registros, necesitan ser reconstruidas a partir de fechas y transacciones.
* days_since_last_purchase: requiere recalculo por errores negativos y outliers extremos.

---

## Análisis de Variables Categóricas

El dataset presenta una diversidad equilibrada en sus variables categóricas, lo que es favorable para análisis posteriores y modelado predictivo.

En cuanto a género, la distribución es bastante pareja: 121,562 clientes son Male, 118,996 Other y 116,999 Female. No existe un sesgo fuerte hacia un género específico, lo que permite análisis equilibrados sin preocupación por distorsión en resultados.

El nivel de ingreso (Income Bracket) muestra predominio del nivel High (138,458), seguido por Low (113,281) y Medium (105,818). Esto indica que la base de clientes tiene una proporción relativamente alta de ingresos elevados, lo que puede influir en patrones de consumo, preferencias de productos y respuesta a promociones.

Respecto al programa de fidelidad (Loyalty Program), la mayoría de los clientes participa (187,812), aunque una proporción significativa (169,745) no lo hace. Esta información es clave para análisis de retención y predicción de churn.

El estado civil (Marital Status) muestra que hay más clientes Single (133,430), mientras que los clientes Divorced (111,921) y Married (112,206) se encuentran en proporciones similares. Esto puede impactar en hábitos de compra según la etapa de vida de los clientes.

El nivel educativo (Education Level) está concentrado en educación avanzada, predominando Master’s (95,960) y PhD (91,025), seguidos de Bachelor’s (91,770) y High School (78,802). Esta variable podría correlacionarse con ingresos altos y determinados patrones de consumo.

La ocupación (Occupation) es diversa: la mayoría de los clientes son Employed (79,019) o Self-Employed (76,141), seguidos por Unemployed (71,424), Retired (70,667) y Student (60,306). Esta diversidad puede influir en frecuencia de compra y preferencias de productos.

En cuanto a categorías de productos (Product Category), las más frecuentes son Electronics, Home, Books y Sports, mientras que Groceries, Toys y Home Goods son menos frecuentes. Esto refleja un enfoque del negocio hacia bienes duraderos y consumo discrecional.

Respecto a tipo de promoción (Promotion Type), aproximadamente 14,241 transacciones no tienen promoción registrada, mientras que entre las transacciones promocionadas predominan Discount y BOGO. Esto indica que las promociones siguen siendo un factor relevante para evaluar su efecto en ventas y churn.

Finalmente, la variable churn muestra que la mayoría de los clientes permanecen activos: 265,903 no abandonaron, mientras que 91,654 sí lo hicieron. La tasa de churn es relativamente baja, lo que sugiere estabilidad en la base de clientes y un buen desempeño en retención.

![Variables categoricas](../ChurnInsight/docs/images/data engineer/variables categoricas.png)

![Churn por loyalty program](../ChurnInsight/docs/images/data engineer/churn por loyalty program.png)

---

## Engineering Decisions Taken

Durante la preparación del dataset se tomaron varias decisiones de ingeniería de datos con el objetivo de mejorar la consistencia, calidad y utilidad de las variables a nivel cliente:

1. Columnas derivadas y recalculadas

* total_sales: calculada como el producto de quantity por unit_price, representando el monto total de cada transacción.
* avg_purchase_value: promedio de total_sales por cliente, proporcionando una medida representativa del valor promedio de compra.
* purchase_frequency: número de transacciones por cliente dividido por los años de membresía (membership_years), reflejando la frecuencia relativa de compra.
* promo_flag: indicador binario de si la transacción incluyó promoción (1) o no (0), utilizado para medir efectividad de promociones.
* promotion_effectiveness: proporción de transacciones con promoción por cliente, evaluando la respuesta a incentivos de marketing.
* total_purchases: suma de online_purchases e in_store_purchases.
* online_ratio: proporción de compras online sobre el total de compras, identificando el canal predominante de cada cliente.

2. Limpieza y normalización de variables categóricas

* Se agruparon las categorías raras (menos del 1% de frecuencia) de todas las variables categóricas en la categoría "other", reduciendo la dispersión y mejorando la estabilidad de análisis y modelos predictivos.
* Los valores nulos en promotion_type se completaron con "no_promotion", garantizando consistencia en el análisis de promociones.

Estas decisiones transforman el dataset de transacciones individuales en un dataset enriquecido y consistente a nivel cliente, listo para exploración, segmentación y modelado predictivo, con variables derivadas significativas y categorías normalizadas.

---

## Dataset final

### Dataset final para Data analyst: Dataset resumido por cliente

Para facilitar el análisis y modelado a nivel cliente, se generó un dataset agregado por customer_id, consolidando la información de múltiples transacciones individuales en métricas resumidas. Este enfoque permite al equipo de Data Analyst trabajar con una fila por cliente, simplificando análisis de comportamiento, segmentación y métricas de retención.

1. Variables numéricas

Se aplicaron agregaciones típicas para capturar la información central de cada cliente:

* Valores estáticos o constantes por cliente: age, membership_years, number_of_children (se tomó el primer valor disponible).
* Promedios de comportamiento: quantity, unit_price, avg_purchase_value, purchase_frequency, avg_discount_used, promotion_effectiveness.
* Totales: online_purchases, in_store_purchases, total_sales, total_transactions, total_items_purchased.
* Fechas clave: days_since_last_purchase (mínimo), transaction_date y last_purchase_date (máximo).

2. Variables categóricas

Para variables como gender, income_bracket, marital_status, education_level, occupation, product_category y promotion_type, se seleccionó la categoría más frecuente (moda) de cada cliente. Esto permite conservar la categoría representativa de cada cliente sin perder consistencia.

3. Flags binarios

Variables como loyalty_program, churn y promo_flag se consolidaron usando el valor máximo, asegurando que si el cliente participó en el programa de fidelidad, realizó churn o tuvo alguna promoción, esto quede reflejado en el dataset agregado.

**Resultado**

El resultado es un dataset compacto y listo para análisis, con una fila por cliente y variables que resumen tanto el comportamiento de compra como las características demográficas y de fidelidad. Este dataset se exportó a dataset_analyst_by_customer.csv y está listo para:

* Análisis de comportamiento y segmentación de clientes.
* Modelos de churn y retención.
* Evaluación de efectividad de promociones y preferencias por canal de compra.
* dataset_analyst_by_customer.csv

---

### Dataset final para Data Scientist

Para habilitar el uso del dataset en modelos de machine learning, se generó un dataset final codificado, a partir del dataset agregado por cliente. El objetivo fue transformar las variables categóricas y flags en un formato numérico adecuado para algoritmos de aprendizaje automático, manteniendo toda la información relevante sobre comportamiento y características de los clientes.

1. One-Hot Encoding de variables categóricas:

Las columnas categóricas gender, income_bracket, marital_status, education_level, occupation, product_category y promotion_type fueron codificadas mediante one-hot encoding. Cada categoría se convierte en una columna binaria, donde un valor de 1 indica la presencia de esa categoría para el cliente y 0 su ausencia. Esto permite que los modelos interpreten correctamente las variables categóricas sin introducir orden o jerarquía artificial.

2. Mapeo de flags binarios:

La columna loyalty_program fue normalizada y convertida a formato binario: primero se estandarizó el texto (yes/no → minúsculas y sin espacios), y luego se mapeó a valores numéricos (yes = 1, no = 0). Esto asegura compatibilidad con algoritmos de machine learning y mantiene consistencia con otros flags como churn y promo_flag.

3. Preservación de métricas y variables derivadas

Todas las variables numéricas agregadas previamente (total_sales, avg_purchase_value, purchase_frequency, online_ratio, promotion_effectiveness, entre otras) se conservaron, asegurando que los modelos tengan acceso a métricas de comportamiento y transacciones de cada cliente.

**Resultado**

El dataset final, llamado customer_dataset_for_ml.csv, está listo para:

* Entrenamiento de modelos predictivos, como churn prediction o segmentación de clientes.
* Análisis de comportamiento mediante algoritmos de clustering o scoring.
* Evaluación de efectividad de promociones y preferencias de canal en modelos supervisados y no supervisados.
