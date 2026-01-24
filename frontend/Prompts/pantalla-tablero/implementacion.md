# Implementación de Interfaz: Dashboard ChurnInsight (P1)

Actúa como desarrollador Senior Frontend para implementar la pantalla de inicio siguiendo estas directrices técnicas:

## Estilo y Layout
- **Tecnologías:** React, Tailwind CSS, Lucide-react (iconos).
- **Contenedor Raíz:** Fondo `bg-slate-950` con ocupación de pantalla completa (`min-h-screen`).
- **Sidebar:** Ancho fijo, fondo `bg-slate-900`, borde derecho `border-slate-800`. Incluye navegación para Dashboard, Clientes y Reportes.
- **Header:** Título "Dashboard" en `text-2xl font-bold` y perfil de usuario.

## Componentes Críticos
1. **KPI Grid:** 4 tarjetas con fondo `bg-slate-900` y borde `border-slate-800`.
   - El valor de "Riesgo Promedio" debe usar `text-amber-500`.
   - El valor de "Ingresos en Riesgo" debe usar `text-rose-500`.
   - El valor de "Predicciones Hoy" debe usar `text-emerald-500`.
2. **Gráfico de Tendencia:** Utiliza Recharts (o un placeholder de contenedor con altura fija) para un gráfico de líneas. Usa el color `emerald-500` para la línea principal.
3. **Tabla de Actividad:** Implementa una tabla sin bordes internos, solo separadores de fila. Los niveles de riesgo deben mostrarse como badges:
   - Riesgo > 70%: Fondo `bg-rose-500/10`, texto `text-rose-500`.
   - Riesgo 40-70%: Fondo `bg-amber-500/10`, texto `text-amber-500`.
   - Riesgo < 40%: Fondo `bg-emerald-500/10`, texto `text-emerald-500`.

## Detalles Finales
- Usa la fuente Inter.
- Asegura que todos los contenedores tengan `rounded-xl`.
- Aplica un padding de `p-8` a la zona de contenido principal.