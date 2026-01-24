import { useState, useEffect } from 'react'
import { Search, AlertCircle } from 'lucide-react'
import { customerService, PredictionInfo } from '../services/customerService'

const CustomerList = () => {
  const [searchTerm, setSearchTerm] = useState('')
  const [riskFilter, setRiskFilter] = useState<'Todos' | 'Alto' | 'Medio' | 'Bajo'>('Todos')
  const [predictions, setPredictions] = useState<PredictionInfo[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
  
  // Paginación
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const [totalElements, setTotalElements] = useState(0)
  const pageSize = 10

  useEffect(() => {
    loadPredictions()
  }, [currentPage])

  const loadPredictions = async () => {
    try {
      setLoading(true)
      setError(null)
      const response = await customerService.getPredictions(currentPage, pageSize)
      setPredictions(response.content)
      setTotalPages(response.totalPages)
      setTotalElements(response.totalElements)
    } catch (err) {
      console.error('Error al cargar predicciones:', err)
      setError('Error al cargar las predicciones')
    } finally {
      setLoading(false)
    }
  }

  const getRiskLevel = (churn: number) => {
    const churnPercent = churn * 100
    if (churnPercent >= 70) return 'Alto'
    if (churnPercent >= 40) return 'Medio'
    return 'Bajo'
  }

  const getRiskColor = (churn: number) => {
    const churnPercent = churn * 100
    if (churnPercent >= 70) return 'text-rose-500'
    if (churnPercent >= 40) return 'text-amber-500'
    return 'text-emerald-500'
  }

  const formatDate = (dateString: string) => {
    const date = new Date(dateString)
    return date.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' })
  }

  const filteredPredictions = predictions.filter(prediction => {
    const matchesSearch = prediction.customer_id.toLowerCase().includes(searchTerm.toLowerCase()) ||
                          prediction.recommended_action.toLowerCase().includes(searchTerm.toLowerCase())
    
    if (riskFilter === 'Todos') return matchesSearch
    
    const riskLevel = getRiskLevel(prediction.probability_churn)
    return matchesSearch && riskLevel === riskFilter
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
        {loading ? (
          <div className="flex items-center justify-center py-20">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-emerald-500"></div>
          </div>
        ) : error ? (
          <div className="flex items-center justify-center gap-3 py-20 text-rose-500">
            <AlertCircle className="w-6 h-6" />
            <span>{error}</span>
          </div>
        ) : (
          <>
            <table className="w-full">
              <thead>
                <tr className="border-b border-slate-800">
                  <th className="text-left text-xs font-semibold text-slate-400 uppercase tracking-wider px-6 py-4">
                    ID Cliente
                  </th>
                  <th className="text-left text-xs font-semibold text-slate-400 uppercase tracking-wider px-6 py-4">
                    Probabilidad de Churn
                  </th>
                  <th className="text-left text-xs font-semibold text-slate-400 uppercase tracking-wider px-6 py-4">
                    Estado
                  </th>
                  <th className="text-left text-xs font-semibold text-slate-400 uppercase tracking-wider px-6 py-4">
                    Fecha Predicción
                  </th>
                  <th className="text-left text-xs font-semibold text-slate-400 uppercase tracking-wider px-6 py-4">
                    Acción Recomendada
                  </th>
                </tr>
              </thead>
              <tbody>
                {filteredPredictions.map((prediction) => (
                  <tr key={prediction.id} className="border-b border-slate-800 hover:bg-slate-800/50 transition-colors">
                    <td className="px-6 py-4">
                      <span className="text-sm font-medium text-slate-300">{prediction.customer_id}</span>
                    </td>
                    <td className="px-6 py-4">
                      <div className="flex items-center gap-2">
                        {prediction.probability_churn >= 0.7 ? (
                          <div className="w-1.5 h-1.5 bg-rose-500 rounded-full"></div>
                        ) : prediction.probability_churn >= 0.4 ? (
                          <div className="w-1.5 h-1.5 bg-amber-500 rounded-full"></div>
                        ) : (
                          <div className="w-1.5 h-1.5 bg-emerald-500 rounded-full"></div>
                        )}
                        <span className={`text-sm font-semibold ${getRiskColor(prediction.probability_churn)}`}>
                          {Math.round(prediction.probability_churn * 100)}%
                        </span>
                      </div>
                    </td>
                    <td className="px-6 py-4">
                      <span className={`inline-flex items-center px-2.5 py-1 rounded-full text-xs font-medium ${
                        prediction.churn === 'CHURN' 
                          ? 'bg-rose-500/10 text-rose-500' 
                          : 'bg-emerald-500/10 text-emerald-500'
                      }`}>
                        {prediction.churn === 'CHURN' ? 'Alto Riesgo' : 'Estable'}
                      </span>
                    </td>
                    <td className="px-6 py-4">
                      <span className="text-sm text-slate-400">{formatDate(prediction.prediction_date)}</span>
                    </td>
                    <td className="px-6 py-4">
                      <span className="text-sm text-slate-300">{prediction.recommended_action}</span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

            {/* Paginación */}
            <div className="flex items-center justify-between px-6 py-4 border-t border-slate-800">
              <div className="text-sm text-slate-400">
                Mostrando <span className="font-medium text-slate-300">{currentPage * pageSize + 1}-{Math.min((currentPage + 1) * pageSize, totalElements)}</span> de{' '}
                <span className="font-medium text-slate-300">{totalElements}</span> predicciones
              </div>
              
              <div className="flex items-center gap-2">
                <div className="flex items-center gap-1">
                  <button
                    onClick={() => setCurrentPage(0)}
                    disabled={currentPage === 0}
                    className="px-2 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                  >
                    ‹‹
                  </button>
                  <button
                    onClick={() => setCurrentPage(currentPage - 1)}
                    disabled={currentPage === 0}
                    className="px-2 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                  >
                    ‹
                  </button>
                  
                  {[...Array(Math.min(totalPages, 5))].map((_, idx) => {
                    let pageNumber: number
                    if (totalPages <= 5) {
                      pageNumber = idx
                    } else if (currentPage < 3) {
                      pageNumber = idx
                    } else if (currentPage > totalPages - 3) {
                      pageNumber = totalPages - 5 + idx
                    } else {
                      pageNumber = currentPage - 2 + idx
                    }
                    
                    return (
                      <button
                        key={pageNumber}
                        onClick={() => setCurrentPage(pageNumber)}
                        className={`px-2.5 py-1 rounded font-medium transition-colors ${
                          currentPage === pageNumber
                            ? 'bg-emerald-500 text-white'
                            : 'text-slate-400 hover:text-white hover:bg-slate-800'
                        }`}
                      >
                        {pageNumber + 1}
                      </button>
                    )
                  })}
                  
                  {totalPages > 5 && currentPage < totalPages - 3 && (
                    <>
                      <span className="px-2 text-slate-500">...</span>
                      <button
                        onClick={() => setCurrentPage(totalPages - 1)}
                        className="px-2.5 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors"
                      >
                        {totalPages}
                      </button>
                    </>
                  )}
                  
                  <button
                    onClick={() => setCurrentPage(currentPage + 1)}
                    disabled={currentPage >= totalPages - 1}
                    className="px-2 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                  >
                    ›
                  </button>
                  <button
                    onClick={() => setCurrentPage(totalPages - 1)}
                    disabled={currentPage >= totalPages - 1}
                    className="px-2 py-1 text-slate-400 hover:text-white hover:bg-slate-800 rounded transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                  >
                    ››
                  </button>
                </div>
              </div>
            </div>
          </>
        )}
      </div>
    </div>
  )
}

export default CustomerList
