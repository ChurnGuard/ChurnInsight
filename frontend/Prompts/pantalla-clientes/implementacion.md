# Implementación de Interfaz: Detalle de Cliente ChurnInsight (P4)

Actúa como desarrollador Senior Frontend para implementar la pantalla de detalle individual del cliente siguiendo estas directrices técnicas:

## Estilo y Layout
- **Tecnologías:** React, Tailwind CSS, Lucide-react (iconos), Recharts (para gráficos).
- **Consistencia:** Mantener el Sidebar lateral izquierdo fijo y el fondo `bg-slate-950`.
- **Estructura:** El contenido principal debe tener un Header de perfil y un layout de rejilla (Grid) de 2 columnas para los análisis.

## Componentes de la Pantalla
1. **Header de Perfil:**
   - Nombre del cliente en `text-3xl font-bold`.
   - Badge de estado grande: si el riesgo es > 75%, fondo `bg-rose-500/10` y texto `text-rose-500` con el texto "Riesgo Crítico".
2. **Sección de Análisis de IA (Columna Izquierda):**
   - **Gauge de Riesgo:** Un gráfico circular de progreso que ocupe espacio visual, mostrando el porcentaje de riesgo en el centro (ej: 85%).
   - **Desglose de Factores (SHAP):** Una lista de barras horizontales que expliquen qué variables sumaron al riesgo (ej: "Cargos Mensuales: +40%"). Usa `bg-rose-500` para factores negativos.
3. **Información del Cliente (Columna Derecha):**
   - Organiza los datos en tarjetas `bg-slate-900` con bordes `border-slate-800`.
   - Divide en mini-secciones: 'Demografía', 'Uso del Servicio' y 'Finanzas'.
4. **Panel de Acciones de Retención:**
   - Crea una tarjeta destacada con fondo `bg-slate-900` y un borde izquierdo de color `border-l-4 border-emerald-500`.
   - Título: "Acción Sugerida por IA".
   - Incluye botones de acción clara (ej: "Ofrecer Cupón de 20%") con `bg-emerald-500`.

## Detalles de UI
- Usa `rounded-xl` para todos los contenedores y componentes.
- Los iconos deben ser minimalistas y de color `text-slate-400`.
- Asegura que las etiquetas de datos (labels) sean legibles y usen `text-slate-400`.
- El espaciado entre componentes debe ser `gap-8`.