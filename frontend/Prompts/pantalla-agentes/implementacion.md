# Implementación de Interfaz: Clientes Críticos ChurnInsight (P5)

Actúa como desarrollador Senior Frontend para implementar la pantalla de gestión de clientes críticos siguiendo estas directrices técnicas:

## Estilo y Layout
- **Tecnologías:** React, Tailwind CSS, Lucide-react (iconos).
- **Consistencia:** Mantener el Sidebar lateral izquierdo fijo y el fondo `bg-slate-950`.
- **Estructura:** El área principal debe mostrar una cuadrícula (Grid) de tarjetas en lugar de una tabla, para enfatizar la urgencia de cada caso.

## Componentes de la Pantalla
1. **Filtros de Prioridad:**
   - Implementa una barra de pestañas (Tabs) superior para filtrar por: "Todos (>75%)", "Impacto Financiero Alto" y "Antigüedad".
2. **Cuadrícula de Tarjetas Críticas:**
   - Crea tarjetas con fondo `bg-slate-900` y un borde superior grueso `border-t-4 border-rose-500` para denotar urgencia.
   - **Contenido de la Tarjeta:**
     - Nombre del cliente y avatar circular pequeño.
     - Puntaje de riesgo en el centro con tamaño de fuente grande y color `text-rose-500`.
     - Sección de datos clave: "Valor Mensual: $XXX" y "Días desde última actividad: XX".
3. **Acciones de Intervención:**
   - En la parte inferior de cada tarjeta, incluye dos botones de acción:
     - "Asignar Ejecutivo": Estilo `outline` con `border-slate-700`.
     - "Intervenir": Estilo sólido con `bg-rose-500`.

## Detalles de UI
- Usa `rounded-xl` para las tarjetas y botones.
- Añade un icono de alerta (`AlertTriangle`) junto al título de la pantalla.
- Asegura que los textos informativos usen `text-slate-400`.
- El espaciado entre tarjetas en el grid debe ser `gap-6`.