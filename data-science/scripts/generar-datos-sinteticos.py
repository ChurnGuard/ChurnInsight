import pandas as pd
import numpy as np
import uuid

# Cargamos el dataset original (crudo, no hay limpieza previa)
df_original = pd.read_csv("data-science/data/Grocery_Customer_Churn_Data.csv")


def generar_datos_sinteticos(df, objetivo_clientes=5000):

    # Atributos fijos por cliente (Demograficos y Churn)
    customer_cols = [
        "customer_id",
        "age",
        "gender",
        "income_bracket",
        "loyalty_program",
        "membership_years",
        "marital_status",
        "number_of_children",
        "education_level",
        "occupation",
        "churn",
        "purchase_frequency",
        "avg_purchase_value",
        "avg_discount_used",
        "online_purchases",
        "in_store_purchases",
        "total_sales",
        "total_transactions",
        "total_items_purchased",
        "days_since_last_purchase",
    ]

    categorias_producto = df["product_category"].dropna().unique()
    tipo_promocion = df["promotion_type"].dropna().unique()
    efectividad_promocion = df["promotion_effectiveness"].dropna().unique()

    # Obtenemos perfiles de los clientes reales
    cant_clientes_reales = df[customer_cols].sort_values('churn', ascending=False).drop_duplicates("customer_id")
    cant_a_anadir = objetivo_clientes - len(cant_clientes_reales)

    transacciones_sinteticas = []

    print(f"Generando {cant_a_anadir} clientes nuevos...")
    print(f"Tasa de Churn en perfiles base: {cant_clientes_reales['churn'].mean():.2%}")
    for i in range(cant_a_anadir):
        # Creamos ids unicos para los clietes generados
        id_nuevo = f"C_SYNTH_{3000 + i}"

        # Seleccionamos un perfil como plantilla de los clientes originales (asi las combinaciones son realistas)
        plantilla_perfil = cant_clientes_reales.sample(1).iloc[0].to_dict()
        plantilla_perfil["customer_id"] = id_nuevo
        es_churn = plantilla_perfil['churn'] == 1

        # Determinamos la cantidad de transacciones que tendra el nuevo cliente (en promedio 72)
        num_transacciones = np.random.randint(71, 73)

        # Validamos que se respete el churn cuando generamos las fechas de compra
        if es_churn:
            # Si es churn debe tener compras antiguas
            dias_atras = np.random.randint(120, 730, size=num_transacciones)
        else:
            # Si no es Churn debe tener compras recientes
            dias_atras = np.random.randint(0, 365, size=num_transacciones)

        # Generamos fechas de transaccion usando un punto de referencia
        fecha_ref = pd.to_datetime("2024-12-31")

        # Generamos una lista de fechas para este cliente en los ultimos 2 años
        fechas_base = fecha_ref - pd.to_timedelta(dias_atras, unit='D')
        fechas = fechas_base.strftime("%Y-%m-%d")

        # La ultima fecha de compra sera la mayor
        fecha_ultima_compra_dt = fechas_base.max()
        fecha_ultima_compra = fecha_ultima_compra_dt.strftime("%Y-%m-%d")

        nuevos_dias_ultima_compra = (fecha_ref - fecha_ultima_compra_dt).days

        for idx in range(num_transacciones):
            # Copiamos los datos del perfil del cliente original
            transaccion = plantilla_perfil.copy()

            # Generamos datos unicos para la transaccion
            transaccion["transaction_id"] = f"T_SYNTH_{uuid.uuid4().hex[:8]}"
            transaccion["transaction_date"] = fechas[idx]
            transaccion["last_purchase_date"] = fecha_ultima_compra
            
            transaccion["days_since_last_purchase"] = nuevos_dias_ultima_compra
            transaccion["quantity"] = max(1, int(np.random.normal(3, 1)))
            transaccion["unit_price"] = round(abs(np.random.normal(50, 20)), 2)

            # Asiganamos categorias y promociones
            transaccion["product_category"] = np.random.choice(categorias_producto)
            transaccion["promotion_type"] = np.random.choice(tipo_promocion)
            transaccion["promotion_effectiveness"] = np.random.choice(efectividad_promocion)

            transacciones_sinteticas.append(transaccion)

    # Unificamos con el dataset original
    df_sintetico = pd.DataFrame(transacciones_sinteticas)
    df_final = pd.concat([df, df_sintetico], ignore_index=True)

    return df_final


# Ejecutamos la funcion y guardamos el dataset con los datos sinteticos
df_augmented = generar_datos_sinteticos(df_original)
df_augmented.to_csv(
    "data-science/data/Grocery_Customer_Churn_Data_Augmented.csv", index=False
)

print("Dataset aumentado guardado como 'Grocery_Customer_Churn_Data_Augmented.csv'")
