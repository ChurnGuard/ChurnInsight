import { useState } from 'react'
import { Search, ExternalLink, TrendingUp, TrendingDown, CheckCircle2 } from 'lucide-react'

interface Customer {
  id: string
  nombre: string
  email: string
  puntajePrioridad: number
  valorEconomico: string
  probabilidadChurn: number
  iniciales: string
  avatarColor: string
}

const CustomerList = () => {
  const [searchTerm, setSearchTerm] = useState('')
  const [riskFilter, setRiskFilter] = useState<'Todos' | 'Alto' | 'Medio' | 'Bajo'>('Todos')

  // Datos mock de clientes
  const customers: Customer[] = [
    {
      id: '#CH-92140',
      nombre: 'Jordan Dalton',
      email: 'jordan.dalton@priad.com',
      puntajePrioridad: 84,
      valorEconomico: 'Cliente de alto valor',
      probabilidadChurn: 81,
      iniciales: 'JD',
      avatarColor: 'bg-blue-500'
    },
    {
      id: '#CH-92141',
      nombre: 'Sarah Koenig',
      email: 'sarah.sarah@techip.io',
      puntajePrioridad: 52,
      valorEconomico: 'Cliente de valor medio',
      probabilidadChurn: 50,
      iniciales: 'SK',
      avatarColor: 'bg-purple-500'
    },
    {
      id: '#CH-92142',
      nombre: 'Marcus Chen',
      email: 'm.chen@logistic.net',
      puntajePrioridad: 12,
      valorEconomico: 'Cliente de bajo valor',
      probabilidadChurn: 8,
      iniciales: 'MC',
      avatarColor: 'bg-emerald-500'
    },
    {
      id: '#CH-92143',
      nombre: 'Elena Lopez',
      email: 'elena@techflow.com',
      puntajePrioridad: 92,
      valorEconomico: 'Cliente de alto valor',
      probabilidadChurn: 89,
      iniciales: 'EL',
      avatarColor: 'bg-rose-500'
    },
    {
      id: '#CH-92144',
      nombre: 'Blake Thompson',
      email: 'blake.t@thompson.co',
      puntajePrioridad: 5,
      valorEconomico: 'Cliente de alto valor',
      probabilidadChurn: 3,
      iniciales: 'BT',
      avatarColor: 'bg-cyan-500'
    }
  ]

  const getRiskLevel = (churn: number) => {
    if (churn >= 70) return 'Alto'
    if (churn >= 40) return 'Medio'
    return 'Bajo'
  }

  const getRiskColor = (churn: number) => {
    if (churn >= 70) return 'text-rose-500'
    if (churn >= 40) return 'text-amber-500'
    return 'text-emerald-500'
  }

  const filteredCustomers = customers.filter(customer => {
    const matchesSearch = customer.nombre.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          customer.id.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          customer.email.toLowerCase().includes(searchTerm.toLowerCase())
    
    if (riskFilter === 'Todos') return matchesSearch
    
    const customerRisk = getRiskLevel(customer.probabilidadChurn)
    return matchesSearch && customerRisk === riskFilter
  })

  return (
    <div className="space-y-6">
      {/* Header */}
      <div>
        <div className="flex items-center gap-2 text-sm text-slate-400 mb-4">
          <span>Directorio</span>
          <span>›</span>
          <span className="text-slate-300">Lista de Clientes</span>
        </div>
        
        <div className="mb-6">
          <h1 className="text-3xl font-bold text-white mb-2">Directorio de Clientes</h1>
          <p className="text-slate-400">
            Gestiona y monitorea el riesgo de abandono de los clientes en tiempo real.
          </p>
        </div>

        {/* Búsqueda y Filtros */}
        <div className="flex items-center gap-4">
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5 text-slate-500" />
            <input
              type="text"
              placeholder="Buscar por nombre, ID o email..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full bg-slate-900 border border-slate-800 rounded-lg pl-10 pr-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
            />
          </div>
          
          <div className="flex items-center gap-2">
            <span className="text-sm text-slate-400 uppercase tracking-wider">Riesgo:</span>
            <div className="flex items-center gap-1 bg-slate-900 rounded-lg p-1">
              {(['Todos', 'Alto', 'Medio', 'Bajo'] as const).map((filter) => (
                <button
                  key={filter}
                  onClick={() => setRiskFilter(filter)}
                  className={`px-3 py-1.5 rounded text-sm font-medium transition-colors ${
                    riskFilter === filter
                      ? 'bg-slate-800 text-white'
                      : 'text-slate-400 hover:text-white'
                  }`}
                >
                  {filter}
                </button>
              ))}
            </div>
          </div>
        </div>
      </div>

      {/* Tabla de Clientes */}
      <div className="bg-slate-900 border border-slate-800 rounded-xl overflow-hidden">
        <table className="w-full">
          <thead>
            <tr className="border-b border-slate-800">
              <th className="text-left text-xs font-semibold text-slate-400 uppercase tracking-wider px-6 py-4">
                ID Cliente
              </th>
              <th className="text-left text-xs font-semibold text-slate-400 uppercase tracking-wider px-6 py-4">
                Nombre Cliente
              </th>
              <th className="text-left text-xs font-semibold text-slate-400 uppercase tracking-wider px-6 py-4">
                Puntaje de Prioridad
              </th>
              <th className="text-left text-xs font-semibold text-slate-400 uppercase tracking-wider px-6 py-4">
                Valor Económico
              </th>
              <th className="text-left text-xs font-semibold text-slate-400 uppercase tracking-wider px-6 py-4">
                Probabilidad de Churn
              </th>
              <th className="text-left text-xs font-semibold text-slate-400 uppercase tracking-wider px-6 py-4">
                Acciones
              </th>
            </tr>
          </thead>
          <tbody>
            {filteredCustomers.map((customer) => (
              <tr key={customer.id} className="border-b border-slate-800 hover:bg-slate-800/50 transition-colors">
                <td className="px-6 py-4">
                  <span className="text-sm text-slate-300">{customer.id}</span>
                </td>
                <td className="px-6 py-4">
                  <div className="flex items-center gap-3">
                    <div className={`w-10 h-10 ${customer.avatarColor} rounded-full flex items-center justify-center text-white font-semibold text-sm`}>
                      {customer.iniciales}
                    </div>
                    <div>
                      <div className="text-sm font-medium text-white">{customer.nombre}</div>
                      <div className="text-xs text-slate-400">{customer.email}</div>
                    </div>
                  </div>
                </td>
                <td className="px-6 py-4">
                  <div className="flex items-center gap-2">
                    {customer.puntajePrioridad >= 70 ? (
                      <div className="w-1.5 h-1.5 bg-rose-500 rounded-full"></div>
                    ) : customer.puntajePrioridad >= 40 ? (
                      <div className="w-1.5 h-1.5 bg-amber-500 rounded-full"></div>
                    ) : (
                      <div className="w-1.5 h-1.5 bg-emerald-500 rounded-full"></div>
                    )}
                    <span className={`text-sm font-semibold ${getRiskColor(customer.puntajePrioridad)}`}>
                      {customer.puntajePrioridad}%
                    </span>
                  </div>
                </td>
                <td className="px-6 py-4">
                  <span className="text-sm text-slate-300">{customer.valorEconomico}</span>
                </td>
                <td className="px-6 py-4">
                  <span className={`text-sm font-semibold ${getRiskColor(customer.probabilidadChurn)}`}>
                    {customer.probabilidadChurn}%
                  </span>
                </td>
                <td className="px-6 py-4">
                  <button className="flex items-center gap-1.5 text-emerald-500 hover:text-emerald-400 text-sm font-medium transition-colors">
                    Ver Detalle
                    <ExternalLink className="w-3.5 h-3.5" />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Paginación */}
        <div className="flex items-center justify-between px-6 py-4 border-t border-slate-800">
          <div className="text-sm text-slate-400">
            Mostrando <span className="font-medium text-slate-300">1-5</span> de{' '}
            <span className="font-medium text-slate-300">2,540</span> clientes
          </div>
          
          <div className="flex items-center gap-2">
            <span className="text-sm text-slate-400 mr-2">Filas por página:</span>
            <select className="bg-slate-800 border border-slate-700 rounded px-2 py-1 text-sm text-slate-300 focus:outline-none focus:ring-2 focus:ring-emerald-500">
              <option>20</option>
              <option>50</option>
              <option>100</option>
            </select>
            
            <div className="flex items-center gap-1 ml-4">
              <button className="px-2 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors">
                ‹‹
              </button>
              <button className="px-2 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors">
                ‹
              </button>
              <button className="px-2.5 py-1 bg-emerald-500 text-white rounded font-medium">
                1
              </button>
              <button className="px-2.5 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors">
                2
              </button>
              <button className="px-2.5 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors">
                3
              </button>
              <span className="px-2 text-slate-500">...</span>
              <button className="px-2.5 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors">
                127
              </button>
              <button className="px-2 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors">
                ›
              </button>
              <button className="px-2 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors">
                ››
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* Cards de Estadísticas */}
      <div className="grid grid-cols-3 gap-6">
        <div className="bg-slate-900 border border-slate-800 rounded-xl p-6 relative overflow-hidden">
          <div className="absolute top-4 right-4">
            <div className="w-12 h-12 bg-rose-500/10 rounded-full flex items-center justify-center">
              <TrendingUp className="w-6 h-6 text-rose-500" />
            </div>
          </div>
          <div className="text-sm text-slate-400 uppercase tracking-wider mb-2">
            Segmento de Alto Riesgo
          </div>
          <div className="text-4xl font-bold text-rose-500 mb-1">412</div>
          <div className="text-sm text-slate-400">cuentas</div>
        </div>

        <div className="bg-slate-900 border border-slate-800 rounded-xl p-6 relative overflow-hidden">
          <div className="absolute top-4 right-4">
            <div className="w-12 h-12 bg-amber-500/10 rounded-full flex items-center justify-center">
              <TrendingDown className="w-6 h-6 text-amber-500" />
            </div>
          </div>
          <div className="text-sm text-slate-400 uppercase tracking-wider mb-2">
            Probabilidad Promedio de Abandono
          </div>
          <div className="flex items-baseline gap-2 mb-1">
            <span className="text-4xl font-bold text-amber-500">34.2%</span>
            <span className="text-sm text-emerald-500 flex items-center gap-1">
              <TrendingDown className="w-3 h-3" />
              2.1%
            </span>
          </div>
        </div>

        <div className="bg-slate-900 border border-slate-800 rounded-xl p-6 relative overflow-hidden">
          <div className="absolute top-4 right-4">
            <div className="w-12 h-12 bg-emerald-500/10 rounded-full flex items-center justify-center">
              <CheckCircle2 className="w-6 h-6 text-emerald-500" />
            </div>
          </div>
          <div className="text-sm text-slate-400 uppercase tracking-wider mb-2">
            Intervenciones Exitosas
          </div>
          <div className="text-4xl font-bold text-emerald-500 mb-1">89</div>
          <div className="text-sm text-slate-400">esta semana</div>
        </div>
      </div>
    </div>
  )
}

export default CustomerList
