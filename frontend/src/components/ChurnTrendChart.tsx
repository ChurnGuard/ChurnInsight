import { XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, Area, AreaChart } from 'recharts'

const data = [
  { mes: 'Jul', tasa: 18 },
  { mes: 'Ago', tasa: 22 },
  { mes: 'Sep', tasa: 19 },
  { mes: 'Oct', tasa: 25 },
  { mes: 'Nov', tasa: 28 },
  { mes: 'Dic', tasa: 24 },
  { mes: 'Ene', tasa: 24 },
]

const ChurnTrendChart = () => {
  return (
    <div className="bg-slate-900 border border-slate-800 rounded-xl p-6">
      <div className="mb-6">
        <h3 className="text-lg font-semibold text-white mb-1">
          Tendencia de Abandono Mensual
        </h3>
        <p className="text-sm text-slate-400">
          Porcentaje de clientes en riesgo por mes
        </p>
      </div>

      <ResponsiveContainer width="100%" height={300}>
        <AreaChart data={data}>
          <defs>
            <linearGradient id="colorTasa" x1="0" y1="0" x2="0" y2="1">
              <stop offset="5%" stopColor="#10b981" stopOpacity={0.3}/>
              <stop offset="95%" stopColor="#10b981" stopOpacity={0}/>
            </linearGradient>
          </defs>
          <CartesianGrid strokeDasharray="3 3" stroke="#1e293b" />
          <XAxis 
            dataKey="mes" 
            stroke="#64748b"
            style={{ fontSize: '12px' }}
          />
          <YAxis 
            stroke="#64748b"
            style={{ fontSize: '12px' }}
            tickFormatter={(value) => `${value}%`}
          />
          <Tooltip
            contentStyle={{
              backgroundColor: '#0f172a',
              border: '1px solid #1e293b',
              borderRadius: '8px',
              color: '#fff'
            }}
            formatter={(value: number) => [`${value}%`, 'Tasa de Abandono']}
          />
          <Area 
            type="monotone" 
            dataKey="tasa" 
            stroke="#10b981" 
            strokeWidth={2}
            fill="url(#colorTasa)"
          />
        </AreaChart>
      </ResponsiveContainer>
    </div>
  )
}

export default ChurnTrendChart
