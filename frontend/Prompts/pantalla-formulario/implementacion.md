# Implementación de Interfaz: Lista de Clientes ChurnInsight (P2)

Actúa como desarrollador Senior Frontend para implementar la pantalla de listado de clientes siguiendo estas directrices técnicas:

## Estilo y Layout
- **Tecnologías:** React, Tailwind CSS, Lucide-react (iconos).
- **Consistencia:** Mantener el Sidebar lateral izquierdo fijo y el fondo `bg-slate-950` utilizado en la Pantalla 1.
- **Contenedores:** El área principal debe usar un padding `p-8`. La tabla y los controles deben estar dentro de contenedores `bg-slate-900` con bordes `border-slate-800`.

## Componentes de la Pantalla
1. **Barra de Acciones:**
   - Implementa un buscador con un icono de lupa que ocupe gran parte del ancho.
   - Añade un botón "Nueva Predicción" a la derecha con `bg-emerald-500` y texto blanco.
2. **Grupo de Filtros:**
   - Crea botones de filtro rápido debajo del buscador. El botón activo debe tener un fondo sutil o borde resaltado.
3. **Tabla de Datos (Data Table):**
   - Implementa la tabla con las columnas: ID, Nombre, Última Transacción, Puntaje de Riesgo y Acciones.
   - **Lógica de Badges (Puntaje de Riesgo):**
     - > 70%: Texto `text-rose-500`, fondo `bg-rose-500/10`.
     - 40-70%: Texto `text-amber-500`, fondo `bg-amber-500/10`.
     - < 40%: Texto `text-emerald-500`, fondo `bg-emerald-500/10`.
   - **Columna Acciones:** Incluye un botón "Ver Detalle" con estilo de texto plano y un icono de flecha o "ojo".

## Detalles de UI
- Aplica `rounded-xl` a todos los botones y contenedores.
- La tabla debe tener un efecto de hover en las filas (`hover:bg-slate-800/50`).
- Asegura que el texto secundario (IDs o fechas) use `text-slate-400`.