interface CriticalCustomerCardProps {
  nombre: string
  apellido: string
  riesgo: number
  valorEconomico: 'Cliente de Alto Valor' | 'Cliente de Valor Medio' | 'Cliente de Bajo Valor'
  puntajePrioridad: number
  accionSugerida: string
  avatarColor: string
}

const CriticalCustomerCard = ({
  nombre,
  apellido,
  riesgo,
  valorEconomico,
  puntajePrioridad,
  accionSugerida,
  avatarColor,
}: CriticalCustomerCardProps) => {
  return (
    <div className="bg-slate-900 border border-slate-800 rounded-xl p-6 hover:border-slate-700 transition-colors">
      {/* Header con Avatar y Riesgo */}
      <div className="flex items-start justify-between mb-6">
        <div className="flex items-center gap-4">
          <div
            className="w-14 h-14 rounded-full flex items-center justify-center text-white font-semibold text-lg"
            style={{ backgroundColor: avatarColor }}
          >
            {nombre[0]}{apellido[0]}
          </div>
          <div>
            <h3 className="text-white font-semibold text-lg">
              {nombre}
            </h3>
            <p className="text-white font-medium">
              {apellido}
            </p>
            <p className="text-xs text-rose-500 uppercase font-semibold mt-1">
              Crítico
            </p>
          </div>
        </div>
        <div className="text-right">
          <div className="text-3xl font-bold text-rose-500 mb-1">
            {riesgo}%
          </div>
          <p className="text-xs text-slate-400 uppercase">
            Riesgo de<br />Abandono
          </p>
        </div>
      </div>

      {/* Valor Económico */}
      <div className="mb-4">
        <p className="text-xs text-slate-400 uppercase mb-1">
          Valor Económico
        </p>
        <p className="text-white font-semibold">
          {valorEconomico}
        </p>
      </div>

      {/* Puntaje de Prioridad */}
      <div className="mb-6">
        <p className="text-xs text-slate-400 uppercase mb-2">
          Puntaje de Prioridad
        </p>
        <div className="text-2xl font-bold text-rose-500">
          {puntajePrioridad}%
        </div>
      </div>

      {/* Acción Sugerida */}
      <div className="border-t border-slate-800 pt-4">
        <p className="text-xs text-slate-400 uppercase mb-2">
          Acción Sugerida
        </p>
        <div className="flex items-start gap-2 mb-4">
          <span className="text-emerald-500 text-lg flex-shrink-0">●</span>
          <p className="text-sm text-emerald-500 font-medium">
            {accionSugerida}
          </p>
        </div>
      </div>
    </div>
  )
}

export default CriticalCustomerCard
