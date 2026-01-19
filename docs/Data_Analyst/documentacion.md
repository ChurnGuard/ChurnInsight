# 📈 Documentación del rol Data Analyst

## 📚 Visión General del Proyecto
Este rol presenta un análisis exhaustivo de datos de clientes, con el objetivo de proporcionar *insights* accionables para la optimización de estrategias de negocio. A través de diversas técnicas de análisis de datos y *machine learning*, exploramos perfiles demográficos, segmentamos clientes por comportamiento de compra, analizamos el *churn* y su impacto, evaluamos la efectividad de las promociones, y desarrollamos un `Economic Value Score` para la clasificación de clientes.

## 📋 Tabla de Contenidos
1.  Configuración Inicial y Carga de Datos
2.  Análisis de Perfil de Clientes por Demografía (Issue 1)
3.  Segmentación de Clientes por Comportamiento de Compra (Issue 2)
4.  Análisis de Churn y Factores Predictivos (Issue 3)
5.  Impacto de Promociones en Ventas y Fidelización (Issue 4)
6.  Análisis de Correlaciones entre Variables Numéricas (Issue 5)
7.  Implementación de la Métrica Economic Value Score (Issue 6)
8.  Visualización Interactiva de Insights Clave (Issue 7)

## 1. Configuración Inicial y Carga de Datos
Se comienza importando un conjunto robusto de librerías esenciales para análisis de datos, visualización y *machine learning* (Pandas, NumPy, Matplotlib, Seaborn, Plotly, Scipy, Scikit-learn). Se carga un dataset de clientes (`dataset_analyst_by_customer.csv`) desde GitHub, proporcionando una vista previa inicial y estadísticas descriptivas.

## 2. Análisis de Perfil de Clientes por Demografía (Issue 1)
En esta sección, se realiza un análisis profundo de los clientes basándose en sus características demográficas. Se crean grupos de edad y se ordenan variables categóricas para facilitar el análisis. Se examina la distribución de género, grupos de edad y niveles de ingresos, así como el comportamiento de compra (ventas, valor promedio de compra, frecuencia) en función de estas variables. Incluye visualizaciones interactivas y pruebas estadísticas (ANOVA, correlación) para identificar diferencias significativas.

### 💡 **Insights Clave de Demografía:**
*   **Grupo de edad más valioso**: Clientes de 26-35 años (con un promedio de \$9,242 en ventas).
*   **Mayor frecuencia de compra**: Clientes con nivel de ingresos 'Medium'.
*   **Mayor valor de compra**: Clientes con nivel educativo 'PhD'.
*   **Ocupación más rentable**: Clientes 'Employed'.
*   **Diferencia por género**: El género 'Other' gasta aproximadamente 1.0x más que 'Male'.

## 3. Segmentación de Clientes por Comportamiento de Compra (Issue 2)
Se utilizan técnicas de *clustering* para segmentar a los clientes basándose en su comportamiento de compra. Las variables incluyen ventas totales, valor promedio de compra, frecuencia, descuentos utilizados y tipos de compra (online/tienda). Se aplica Análisis de Componentes Principales (PCA) para la reducción de dimensionalidad y se determina el número óptimo de *clusters* (k=3) utilizando los métodos del codo y Silhouette. Finalmente, se aplica K-Means y se perfila cada *cluster* con nombres descriptivos.

### 💡 **Insights Clave de Segmentación:**
*   **Cluster dominante**: El segmento 'Compradores Esenciales Moderados' es el más grande con 2994 clientes.
*   **Diferencia de valor**: Los 'Compradores de Alto Valor con Descuento Online' generan en promedio \$10439 en ventas, mientras que los 'Compradores Esenciales Moderados' generan \$8852.
*   **Uso de descuentos**: El segmento 'Cazadores de Ofertas en Tienda' muestra el uso más alto de descuentos (10.3%).

## 4. Análisis de Churn y Factores Predictivos (Issue 3)
Esta sección se centra en comprender el *churn* (abandono de clientes). Se analiza la distribución del *churn*, se comparan las características de los clientes que abandonan frente a los que permanecen, y se examina el *churn* por variables demográficas. También se incluye un análisis de supervivencia (curva de Kaplan-Meier) para entender la retención de clientes a lo largo del tiempo.

## 5. Impacto de Promociones en Ventas y Fidelización (Issue 4)
Aquí se investiga cómo las promociones influyen en el comportamiento de compra y la lealtad del cliente. Se analiza el uso de promociones, el impacto de diferentes tipos de promociones en métricas como ventas, valor de compra y frecuencia. Se evalúa la efectividad de las promociones y se segmentan los clientes por su sensibilidad a las mismas.

## 6. Análisis de Correlaciones entre Variables Numéricas (Issue 5)
Se construye y visualiza una matriz de correlación para identificar las relaciones entre las variables numéricas clave del dataset. Se detectan correlaciones fuertes y se analiza la multicolinealidad mediante el Factor de Inflación de Varianza (VIF). Se utiliza PCA nuevamente para la reducción de dimensionalidad, identificando los componentes principales y las variables que más contribuyen a cada uno.

### 💡 **Insights Clave de Correlaciones:**
*   **Correlación más fuerte**: `avg_purchase_value` y `total_sales` (r=0.997), indicando una relación casi perfecta.
*   **Variable más relacionada con ventas**: `avg_purchase_value` (r=0.997).
*   **Posible multicolinealidad**: 10 variables con VIF > 5, sugiriendo la necesidad de consideración en modelos predictivos.
*   **Reducción dimensional posible**: 10 componentes explican el 95% de la varianza total de los datos numéricos.

## 7. Implementación de la Métrica Economic Value Score (Issue 6)
Se desarrollan e implementan tres métodos diferentes para calcular un `Economic Value Score` para cada cliente:
1.  **Puntuación Normalizada (0-100)**: Basada en la normalización min-max de ventas, valor promedio de compra y transacciones.
2.  **Score Percentil (Ranking Relativo)**: Utiliza percentiles para una medida más robusta a *outliers*.
3.  **Score RFM Adaptado**: Incorpora Recencia, Frecuencia y Monto para una segmentación más tradicional y orientada al marketing.

Se realiza un análisis comparativo de los métodos y se selecciona el score percentil como el más óptimo debido a su mayor correlación con las ventas totales y su robustez.

## 8. Visualización Interactiva de Insights Clave (Issue 7)
Finalmente, se crean **tres dashboards interactivos** utilizando Plotly para resumir y presentar los hallazgos más importantes de manera visual y accesible:
*   **Dashboard 1: Datos Generales**: Incluye distribuciones demográficas, ventas por grupos de edad y ocupación, gráficos de scree plot y silhouette para segmentación, y visualización 2D de clusters, además de la distribución del *Economic Value Score*.
*   **Dashboard 2: Análisis de Churn y Retención**: Muestra la distribución del *churn*, la tasa de *churn* por género y la correlación de variables con el *churn*.
*   **Dashboard 3: Resumen de Métricas Clave (KPI)**: Un tablero con los principales Indicadores Clave de Rendimiento del negocio (ventas totales, promedio por cliente, tasa de *churn*, etc.).

## 9. Tecnologías Utilizadas
*   **Python 3.x**
*   **Pandas**: Manipulación y análisis de datos.
*   **NumPy**: Computación numérica.
*   **Matplotlib, Seaborn, Plotly**: Visualización de datos interactiva y estática.
*   **SciPy**: Análisis estadístico (ttest_ind, f_oneway, chi2_contingency).
*   **Scikit-learn**: *Machine Learning* (StandardScaler, PCA, KMeans, DBSCAN, AgglomerativeClustering, silhouette_score, calinski_harabasz_score).
*   **Statsmodels**: Análisis estadístico (pairwise_tukeyhsd, variance_inflation_factor).

---