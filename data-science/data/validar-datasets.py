import pandas as pd


# Carga de datasets
df_raw = pd.read_csv('data-science/data/Grocery_Customer_Churn_Data.csv')
df_analyst = pd.read_csv('data-science/data/dataset_analyst_by_customer.csv')
df_ml = pd.read_csv('data-science/data/customer_dataset_for_ml.csv')

print("--- Revision de datasets ---")


# Verificamos la unicidad para cada cliente
raw_unique = df_raw['customer_id'].nunique()
analyst_rows = df_analyst.shape[0]
print(f"Clientes Unicos: {raw_unique}")
print(f"Filas en el Dataset del Analista: {analyst_rows}")

if raw_unique == analyst_rows:
    print("Todo OK. Una fila por cliente.")
else:
    print(f"Error. Los numeros no coinciden, faltan datos o estan duplicados. Diferencia: {raw_unique - analyst_rows}")

# Verificamos si hay valores nulos en el dataset del analista
nulls_analyst = df_analyst.isna().sum().sum()

if nulls_analyst == 0:
    print("Todo OK. No hay valores nulos")
else:
    print(f"Error. Se detectaron {nulls_analyst} valores nulos")

# Verificamos logica de negocio para edades negativas o valores desconocidos

negativos = df_analyst[(df_analyst['age'] < 0) | (df_analyst['income_bracket'] == 'Unknown')]

if negativos.empty:
    print("Todo OK. No se encontraron edades negativas ni datos sucios")
else:
    print("Error. Se encontraron valores que no tienen sentido")

# Breve analisis de la tasa de churn en el dataset de ml
churn_rate = df_ml['churn'].mean()
print(f"Porcentaje de Churn en el Dataset de ML: {churn_rate:.2%}")