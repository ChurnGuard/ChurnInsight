import { ArrowRight } from 'lucide-react'

interface Activity {
  cliente: string
  clienteId: string
  fecha: string
  riesgo: number
  accion: string
}

const activities: Activity[] = [
  {
    cliente: 'Elena Sorolla',
    clienteId: 'C1234',
    fecha: '21 Ene, 10:30',
    riesgo: 98.4,
    accion: 'Campaña de Retención VIP'
  },
  {
    cliente: 'Marcus Thorne',
    clienteId: 'C5678',
    fecha: '21 Ene, 09:15',
    riesgo: 94.2,
    accion: 'Llamada de Fidelización'
  },
  {
    cliente: 'Julianna Reed',
    clienteId: 'C9012',
    fecha: '21 Ene, 08:45',
    riesgo: 91.7,
    accion: 'Oferta de Extensión Anual'
  },
  {
    cliente: 'David Chen',
    clienteId: 'C3456',
    fecha: '20 Ene, 16:20',
    riesgo: 89.1,
    accion: 'Campaña de Retención'
  },
  {
    cliente: 'Sarah Jenkins',
    clienteId: 'C7890',
    fecha: '20 Ene, 14:50',
    riesgo: 87.5,
    accion: 'Email de Seguimiento'
  },
]

const getRiskBadgeClasses = (riesgo: number) => {
  if (riesgo > 70) {
    return 'bg-rose-500/10 text-rose-500 border-rose-500/20'
  }
  if (riesgo >= 40) {
    return 'bg-amber-500/10 text-amber-500 border-amber-500/20'
  }
  return 'bg-emerald-500/10 text-emerald-500 border-emerald-500/20'
}

const ActivityTable = () => {
  return (
    <div className="bg-slate-900 border border-slate-800 rounded-xl p-6">
      <div className="flex items-center justify-between mb-6">
        <div>
          <h3 className="text-lg font-semibold text-white mb-1">
            Actividad Reciente
          </h3>
          <p className="text-sm text-slate-400">
            Últimas predicciones y acciones realizadas
          </p>
        </div>
        <button className="text-sm text-emerald-500 hover:text-emerald-400 font-medium flex items-center gap-1">
          Ver todo
          <ArrowRight className="w-4 h-4" />
        </button>
      </div>

      <div className="space-y-1">
        {activities.map((activity, index) => (
          <div
            key={index}
            className="flex items-center justify-between py-4 border-b border-slate-800 last:border-b-0 hover:bg-slate-800/50 -mx-2 px-2 rounded-lg transition-colors"
          >
            {/* Cliente */}
            <div className="flex-1 min-w-0">
              <div className="flex items-center gap-3">
                <div className="w-10 h-10 bg-slate-800 rounded-full flex items-center justify-center flex-shrink-0">
                  <span className="text-sm font-semibold text-white">
                    {activity.cliente.split(' ').map(n => n[0]).join('')}
                  </span>
                </div>
                <div className="min-w-0">
                  <p className="text-sm font-medium text-white truncate">
                    {activity.cliente}
                  </p>
                  <p className="text-xs text-slate-400 font-mono">
                    {activity.clienteId}
                  </p>
                </div>
              </div>
            </div>

            {/* Fecha */}
            <div className="flex-shrink-0 px-4 text-center">
              <p className="text-sm text-slate-400">{activity.fecha}</p>
            </div>

            {/* Riesgo */}
            <div className="flex-shrink-0 px-4">
              <span className={`inline-flex items-center px-3 py-1 rounded-full text-xs font-semibold border ${getRiskBadgeClasses(activity.riesgo)}`}>
                {activity.riesgo}%
              </span>
            </div>

            {/* Acción */}
            <div className="flex-1 px-4 text-right">
              <p className="text-sm text-slate-300 truncate">
                {activity.accion}
              </p>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default ActivityTable
