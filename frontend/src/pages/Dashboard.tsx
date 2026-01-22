import Sidebar from '../components/Sidebar'
import Header from '../components/Header'
import CriticalCustomerCard from '../components/CriticalCustomerCard'

const Dashboard = () => {
  const clientes = [
    {
      nombre: 'Elena',
      apellido: 'Sorolla',
      riesgo: 98.4,
      valorEconomico: 'Cliente de Alto Valor' as const,
      puntajePrioridad: 96,
      accionSugerida: 'Campaña de Retención VIP',
      avatarColor: '#64748b'
    },
    {
      nombre: 'Marcus',
      apellido: 'Thorne',
      riesgo: 94.2,
      valorEconomico: 'Cliente de Valor Medio' as const,
      puntajePrioridad: 89,
      accionSugerida: 'Llamada de Fidelización',
      avatarColor: '#475569'
    },
    {
      nombre: 'Julianna',
      apellido: 'Reed',
      riesgo: 91.7,
      valorEconomico: 'Cliente de Alto Valor' as const,
      puntajePrioridad: 92,
      accionSugerida: 'Oferta de Extensión Anual',
      avatarColor: '#0891b2'
    },
    {
      nombre: 'David',
      apellido: 'Chen',
      riesgo: 89.1,
      valorEconomico: 'Cliente de Valor Medio' as const,
      puntajePrioridad: 84,
      accionSugerida: 'Campaña de Retención',
      avatarColor: '#64748b'
    },
    {
      nombre: 'Sarah',
      apellido: 'Jenkins',
      riesgo: 87.5,
      valorEconomico: 'Cliente de Bajo Valor' as const,
      puntajePrioridad: 78,
      accionSugerida: 'Email de Seguimiento',
      avatarColor: '#475569'
    },
    {
      nombre: 'Robert',
      apellido: 'Vance',
      riesgo: 85.0,
      valorEconomico: 'Cliente de Bajo Valor' as const,
      puntajePrioridad: 75,
      accionSugerida: 'Análisis de Comportamiento',
      avatarColor: '#84cc16'
    }
  ]

  return (
    <div className="flex min-h-screen bg-slate-950">
      {/* Sidebar */}
      <Sidebar currentPage="tablero" />

      {/* Contenido Principal - con margen para el sidebar fijo */}
      <div className="flex-1 flex flex-col ml-64">
        {/* Header */}
        <Header />

        {/* Área de Contenido */}
        <main className="flex-1 p-8 overflow-auto">
          {/* Título y Filtros */}
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-white mb-2">
              Tablero de Gestión de Riesgo Crítico
            </h1>
            <p className="text-slate-400">
              Monitoreo individual de usuarios con riesgo de abandono superior al 85%.
            </p>
          </div>

          {/* Grid de Clientes Críticos */}
          <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
            {clientes.map((cliente, index) => (
              <CriticalCustomerCard key={index} {...cliente} />
            ))}
          </div>
        </main>
      </div>
    </div>
  )
}

export default Dashboard
