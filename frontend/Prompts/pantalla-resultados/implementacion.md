# Implementación de Interfaz: Formulario de Predicción ChurnInsight (P3)

Actúa como desarrollador Senior Frontend para implementar la pantalla de generación de predicciones siguiendo estas directrices técnicas:

## Estilo y Layout
- **Tecnologías:** React, Tailwind CSS, Lucide-react (iconos).
- **Consistencia:** Mantener el Sidebar lateral izquierdo fijo y el fondo `bg-slate-950`.
- **Estructura de Pantalla:** Divide el área de contenido principal en dos columnas:
  1. **Lado Izquierdo (60%):** Formulario con scroll independiente.
  2. **Lado Derecho (40%):** Panel de resultados fijo con borde izquierdo `border-slate-800`.

## Componentes del Formulario (Izquierda)
- Agrupa los campos en 3 tarjetas (`bg-slate-900`) con los títulos: "Demografía", "Uso de Servicios" e "Historial Financiero".
- **Campo Especial:** El selector de `promotion_type` debe tener como opción predeterminada y técnica el valor `No_Promotion` (respetando mayúsculas y guion bajo).
- **Botón de Acción:** Al final del formulario, incluye un botón "Generar Predicción" que use `bg-emerald-500` con efecto hover.

## Panel de Resultados (Derecha)
- Título: "Análisis de Riesgo IA".
- **Medidor de Riesgo:** Implementa un componente de tipo "Gauge" circular o una barra de progreso radial que muestre un porcentaje. 
  - Si el riesgo es > 70%, usa `text-rose-500`.
  - Si es < 40%, usa `text-emerald-500`.
- **Factores de Impacto:** Crea una lista de variables que afectaron el resultado (ej: "Baja antigüedad: +20%"). Usa barras horizontales delgadas para representar el peso de cada factor.
- **Acciones:** Botón de "Guardar en Historial" y "Descargar PDF" en la parte inferior del panel.

## Detalles de UI
- Todos los inputs deben ser `bg-slate-950` con `border-slate-800` y texto `text-slate-100`.
- Asegura un espaciado consistente `gap-6` entre secciones.
- El panel derecho debe tener un "estado vacío" (Empty State) que diga "Completa el formulario para ver el análisis" antes de procesar los datos.