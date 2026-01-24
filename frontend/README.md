# ChurnInsight Frontend

Dashboard de predicción de abandono de clientes construido con React, TypeScript y Tailwind CSS.

## 🚀 Tecnologías

- **React 18** - Framework UI
- **TypeScript** - Tipado estático
- **Vite** - Build tool y dev server
- **Tailwind CSS** - Framework de estilos
- **Recharts** - Librería de gráficos
- **Lucide React** - Iconos

## 📦 Instalación

```bash
# Instalar dependencias
npm install

# Iniciar servidor de desarrollo
npm run dev

# Compilar para producción
npm run build

# Vista previa de la build
npm run preview
```

## 🎨 Estructura del Proyecto

```
frontend/
├── src/
│   ├── components/
│   │   ├── Sidebar.tsx          # Navegación lateral
│   │   ├── Header.tsx           # Cabecera con búsqueda
│   │   ├── KPICard.tsx          # Tarjeta de KPI
│   │   ├── ChurnTrendChart.tsx  # Gráfico de tendencia
│   │   └── ActivityTable.tsx    # Tabla de actividad reciente
│   ├── pages/
│   │   └── Dashboard.tsx        # Página principal
│   ├── App.tsx                  # Componente raíz
│   ├── main.tsx                 # Punto de entrada
│   └── index.css                # Estilos globales
├── public/                       # Assets estáticos
├── index.html                    # HTML base
├── package.json
├── tsconfig.json
├── tailwind.config.js
└── vite.config.ts
```

## 🎨 Sistema de Diseño

### Paleta de Colores

- **Background**: `slate-950` (#020617)
- **Surface**: `slate-900` (#0f172a)
- **Border**: `slate-800` (#1e293b)
- **Success**: `emerald-500` (#10b981)
- **Warning**: `amber-500` (#f59e0b)
- **Danger**: `rose-500` (#f43f5e)

### Componentes

- **Sidebar**: Navegación fija con logo y menú
- **Header**: Título, búsqueda y notificaciones
- **KPI Cards**: Métricas clave con indicadores de tendencia
- **Chart**: Gráfico de área con tendencia temporal
- **Activity Table**: Lista de predicciones recientes

## 🔌 Integración con Backend

El proxy de Vite está configurado para redirigir `/api` a `http://localhost:8080`.

Ejemplo de uso:

```typescript
// Obtener clientes críticos
const response = await fetch("/api/customers/critical");
const data = await response.json();

// Crear predicción
const response = await fetch("/api/v1/predictions", {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify(predictionData),
});
```

## 📱 Características Implementadas

- ✅ Dashboard con KPIs principales
- ✅ Gráfico de tendencia de abandono
- ✅ Tabla de actividad reciente
- ✅ Sidebar de navegación
- ✅ Sistema de colores según nivel de riesgo
- ✅ Diseño responsive
- ✅ Modo oscuro (dark theme)

## 🚧 Próximas Pantallas

1. Lista de Clientes (con filtros y búsqueda)
2. Formulario de Nueva Predicción (wizard 7 pasos)
3. Detalle de Cliente (vista 360°)
4. Clientes Críticos (gestión de intervenciones)
5. Análisis y Reportes (BI)
6. Monitoreo de Agentes IA
7. Configuración del Sistema

## 📄 Licencia

Este proyecto es parte del sistema ChurnInsight.
