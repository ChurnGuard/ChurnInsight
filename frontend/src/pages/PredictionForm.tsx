import { useState } from 'react'
import { User, Briefcase, CreditCard, Sparkles, TrendingUp, Lightbulb, Edit2, Share2, ChevronLeft, ChevronRight } from 'lucide-react'

interface PredictionResult {
  riesgo: number
  puntajePrioridad: number
  clienteAltoValor: boolean
  perfil: string
  factores: Array<{ nombre: string; impacto: number }>
  accionRecomendada: string
}

const PredictionForm = () => {
  const [isPanelOpen, setIsPanelOpen] = useState(false)
  const [formData, setFormData] = useState({
    // Demografía
    clientId: '',
    edad: '',
    genero: '',
    pais: '',
    
    // Uso del Servicio
    nivelSuscripcion: 'Premium',
    tipoPromocion: 'Sin Promoción',
    ticketsSoporte: '',
    horasRegistradas: '',
    
    // Historial Financiero
    puntajeCredito: '',
    saldoCuenta: '',
    salarioEstimado: '',
    metodoPago: 'Tarjeta de Crédito'
  })

  const [predictionResult, setPredictionResult] = useState<PredictionResult | null>(null)

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    
    // Simulación de resultados (reemplazar con llamada API real)
    const mockResult: PredictionResult = {
      riesgo: 72,
      puntajePrioridad: 84,
      clienteAltoValor: true,
      perfil: 'Dependiente de Promociones',
      factores: [
        { nombre: 'Riesgo por Inactividad', impacto: 32 },
        { nombre: 'Abuso de Promociones', impacto: 15 }
      ],
      accionRecomendada: 'Acción Sugerida: Llamada de retención + beneficio de lealtad'
    }
    
    setPredictionResult(mockResult)
    setIsPanelOpen(true)
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  const resetPrediction = () => {
    setPredictionResult(null)
    setIsPanelOpen(false)
  }

  const togglePanel = () => {
    setIsPanelOpen(!isPanelOpen)
  }

  return (
    <div className="relative flex h-full">
      {/* Panel Izquierdo - Formulario */}
      <div className="flex-1 overflow-y-auto px-6 py-8 flex justify-center">
        <div className="max-w-3xl w-full">
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-white mb-2">
              Formulario de Nueva Predicción
            </h1>
            <p className="text-slate-400">
              Ingrese las variables del cliente para generar un análisis de riesgo de abandono de alta fidelidad utilizando el modelo ML-v4.
            </p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-6">
            {/* Sección: Demografía */}
            <div className="bg-slate-900 border border-slate-800 rounded-xl p-6">
              <div className="flex items-center gap-2 mb-6">
                <User className="w-5 h-5 text-emerald-500" />
                <h2 className="text-lg font-semibold text-white">Demografía</h2>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {/* ID de Cliente */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    ID de Cliente
                  </label>
                  <input
                    type="text"
                    name="clientId"
                    value={formData.clientId}
                    onChange={handleChange}
                    placeholder="CID-882910-X"
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                {/* Edad */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Edad
                  </label>
                  <input
                    type="number"
                    name="edad"
                    value={formData.edad}
                    onChange={handleChange}
                    placeholder="ej. 34"
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                {/* Género */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Género
                  </label>
                  <select
                    name="genero"
                    value={formData.genero}
                    onChange={handleChange}
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  >
                    <option value="">Seleccionar Género</option>
                    <option value="Male">Masculino</option>
                    <option value="Female">Femenino</option>
                    <option value="Other">Otro</option>
                  </select>
                </div>

                {/* País */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    País
                  </label>
                  <input
                    type="text"
                    name="pais"
                    value={formData.pais}
                    onChange={handleChange}
                    placeholder="Ingrese país"
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>
              </div>
            </div>

            {/* Sección: Uso del Servicio */}
            <div className="bg-slate-900 border border-slate-800 rounded-xl p-6">
              <div className="flex items-center gap-2 mb-6">
                <Briefcase className="w-5 h-5 text-amber-500" />
                <h2 className="text-lg font-semibold text-white">Uso del Servicio</h2>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {/* Nivel de Suscripción */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Nivel de Suscripción
                  </label>
                  <select
                    name="nivelSuscripcion"
                    value={formData.nivelSuscripcion}
                    onChange={handleChange}
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  >
                    <option value="Basic">Basic</option>
                    <option value="Premium">Premium</option>
                    <option value="Enterprise">Enterprise</option>
                  </select>
                </div>

                {/* Tipo de Promoción */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2 flex items-center gap-2">
                    Tipo de Promoción
                    <span className="text-slate-400 text-xs">ⓘ</span>
                  </label>
                  <select
                    name="tipoPromocion"
                    value={formData.tipoPromocion}
                    onChange={handleChange}
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  >
                    <option value="Sin Promoción">Sin Promoción</option>
                    <option value="No_Promotion">No Promotion</option>
                    <option value="Twenty_Percent_Off">20% Off</option>
                    <option value="Buy_One_Get_One_Free">BOGO</option>
                    <option value="Seasonal_Discount">Descuento Estacional</option>
                  </select>
                </div>

                {/* Tickets de Soporte */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Tickets de Soporte (Últimos 30 días)
                  </label>
                  <input
                    type="number"
                    name="ticketsSoporte"
                    value={formData.ticketsSoporte}
                    onChange={handleChange}
                    placeholder="0"
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                {/* Horas Registradas Mensuales */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Horas Registradas Mensuales
                  </label>
                  <input
                    type="number"
                    step="0.1"
                    name="horasRegistradas"
                    value={formData.horasRegistradas}
                    onChange={handleChange}
                    placeholder="0.0"
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>
              </div>
            </div>

            {/* Sección: Historial Financiero */}
            <div className="bg-slate-900 border border-slate-800 rounded-xl p-6">
              <div className="flex items-center gap-2 mb-6">
                <CreditCard className="w-5 h-5 text-rose-500" />
                <h2 className="text-lg font-semibold text-white">Historial Financiero</h2>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {/* Puntaje de Crédito */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Puntaje de Crédito
                  </label>
                  <input
                    type="number"
                    name="puntajeCredito"
                    value={formData.puntajeCredito}
                    onChange={handleChange}
                    placeholder="300-850"
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                {/* Saldo de Cuenta */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Saldo de Cuenta ($)
                  </label>
                  <input
                    type="number"
                    step="0.01"
                    name="saldoCuenta"
                    value={formData.saldoCuenta}
                    onChange={handleChange}
                    placeholder="0.00"
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                {/* Salario Estimado */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Salario Estimado ($)
                  </label>
                  <input
                    type="text"
                    name="salarioEstimado"
                    value={formData.salarioEstimado}
                    onChange={handleChange}
                    placeholder="Estimación anual"
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                {/* Método de Pago */}
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Método de Pago
                  </label>
                  <select
                    name="metodoPago"
                    value={formData.metodoPago}
                    onChange={handleChange}
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  >
                    <option value="Tarjeta de Crédito">Tarjeta de Crédito</option>
                    <option value="Tarjeta de Débito">Tarjeta de Débito</option>
                    <option value="Transferencia">Transferencia Bancaria</option>
                    <option value="PayPal">PayPal</option>
                  </select>
                </div>
              </div>
            </div>

            {/* Botón de Submit */}
            <div className="flex justify-end">
              <button
                type="submit"
                className="flex items-center gap-2 bg-emerald-500 hover:bg-emerald-600 text-white font-semibold px-8 py-3 rounded-xl transition-colors"
              >
                <Sparkles className="w-5 h-5" />
                Generar Predicción
              </button>
            </div>
          </form>
        </div>
      </div>

      {/* Botón Toggle Panel */}
      {predictionResult && (
        <button
          onClick={togglePanel}
          className="fixed top-1/2 transform -translate-y-1/2 z-20 bg-emerald-500 hover:bg-emerald-600 text-white rounded-full p-2 shadow-lg transition-all duration-300"
          style={{ right: isPanelOpen ? '380px' : '0' }}
        >
          {isPanelOpen ? <ChevronRight className="w-5 h-5" /> : <ChevronLeft className="w-5 h-5" />}
        </button>
      )}

      {/* Panel Derecho - Resultados */}
      <div 
        className={`bg-slate-950 overflow-y-auto px-6 py-8 border-l border-slate-800 transition-all duration-300 ${
          isPanelOpen ? 'w-[380px]' : 'w-0 px-0'
        }`}
      >
        <div className={isPanelOpen ? 'opacity-100' : 'opacity-0'}>
        {!predictionResult ? (
          // Empty State
          <div className="flex flex-col items-center justify-center h-full text-center">
            <div className="w-20 h-20 bg-slate-900 rounded-full flex items-center justify-center mb-4 border border-slate-800">
              <TrendingUp className="w-10 h-10 text-slate-600" />
            </div>
            <h3 className="text-lg font-semibold text-slate-300 mb-2">Análisis de Riesgo IA</h3>
            <p className="text-slate-500 text-sm max-w-xs">
              Completa el formulario para ver el análisis de riesgo de abandono
            </p>
          </div>
        ) : (
          // Resultados
          <div className="space-y-6">
            <div className="text-center">
              <h2 className="text-2xl font-bold text-slate-100 mb-1">Resultados</h2>
              <p className="text-sm text-slate-400">
                Los resultados se basan en el modelo ML-v4 analizando el perfil del cliente proporcionado.
              </p>
            </div>

            {/* Gauge de Riesgo */}
            <div className="bg-slate-900 rounded-lg p-6 border border-slate-800">
              <h3 className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-4">
                Riesgo de Abandono
              </h3>
              <div className="flex flex-col items-center">
                {/* Gauge Visual */}
                <div className="relative w-44 h-44 mb-4">
                  <svg className="transform -rotate-90" width="176" height="176">
                    <circle
                      cx="88"
                      cy="88"
                      r="75"
                      stroke="#1e293b"
                      strokeWidth="14"
                      fill="none"
                    />
                    <circle
                      cx="88"
                      cy="88"
                      r="75"
                      stroke="#f43f5e"
                      strokeWidth="14"
                      fill="none"
                      strokeDasharray={`${2 * Math.PI * 75}`}
                      strokeDashoffset={`${2 * Math.PI * 75 * (1 - predictionResult.riesgo / 100)}`}
                      strokeLinecap="round"
                    />
                  </svg>
                  <div className="absolute inset-0 flex items-center justify-center">
                    <div className="text-center">
                      <div className="text-5xl font-bold text-rose-500">{predictionResult.riesgo}%</div>
                      <div className="text-xs text-slate-400 mt-1">Probabilidad</div>
                    </div>
                  </div>
                </div>

                {/* Badge Riesgo Crítico */}
                <div className="flex items-center gap-2 px-3 py-1.5 bg-rose-500/10 border border-rose-500/20 rounded-full mb-3">
                  <div className="w-2 h-2 bg-rose-500 rounded-full"></div>
                  <span className="text-xs font-semibold text-rose-500 uppercase">Riesgo Crítico</span>
                </div>

                <div className="text-sm text-slate-300 mb-4">
                  Puntaje de Prioridad: <span className="font-semibold text-slate-100">{predictionResult.puntajePrioridad}%</span>
                </div>

                {/* Badges de Perfil */}
                <div className="flex flex-col gap-2 w-full">
                  {predictionResult.clienteAltoValor && (
                    <div className="flex items-center gap-2 px-3 py-2 bg-emerald-500/10 border border-emerald-500/20 rounded-lg">
                      <Lightbulb className="w-4 h-4 text-emerald-500" />
                      <span className="text-xs font-medium text-emerald-500">Cliente de Alto Valor</span>
                    </div>
                  )}
                  <div className="flex items-center gap-2 px-3 py-2 bg-slate-800 border border-slate-700 rounded-lg">
                    <User className="w-4 h-4 text-slate-400" />
                    <span className="text-xs font-medium text-slate-300">Perfil: {predictionResult.perfil}</span>
                  </div>
                </div>
              </div>
            </div>

            {/* Factores de Impacto */}
            <div className="bg-slate-900 rounded-lg p-6 border border-slate-800">
              <div className="flex items-center gap-2 mb-4">
                <TrendingUp className="w-5 h-5 text-rose-500" />
                <h3 className="text-sm font-semibold text-slate-100">Factores de Impacto</h3>
              </div>
              <div className="space-y-3">
                {predictionResult.factores.map((factor, index) => (
                  <div key={index}>
                    <div className="flex items-center justify-between mb-1.5">
                      <span className="text-xs text-slate-300">{factor.nombre}</span>
                      <span className="text-xs font-semibold text-rose-400">+{factor.impacto}%</span>
                    </div>
                    <div className="w-full bg-slate-800 rounded-full h-1.5">
                      <div
                        className="bg-rose-500 h-1.5 rounded-full transition-all"
                        style={{ width: `${Math.min(factor.impacto * 2.5, 100)}%` }}
                      ></div>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Acción Recomendada */}
            <div className="bg-emerald-500/10 border border-emerald-500/20 rounded-lg p-5">
              <div className="flex items-start gap-3">
                <div className="w-10 h-10 bg-emerald-500/20 rounded-full flex items-center justify-center flex-shrink-0">
                  <Lightbulb className="w-5 h-5 text-emerald-500" />
                </div>
                <div>
                  <h3 className="text-sm font-semibold text-emerald-400 mb-1">Acción Recomendada</h3>
                  <p className="text-sm text-emerald-300/90">{predictionResult.accionRecomendada}</p>
                </div>
              </div>
            </div>

            {/* Botones de Acción */}
            <div className="flex gap-3 pt-2">
              <button
                onClick={resetPrediction}
                className="flex-1 flex items-center justify-center gap-2 px-4 py-2.5 bg-slate-900 border border-slate-800 text-slate-300 rounded-lg hover:bg-slate-800 transition-colors text-sm font-medium"
              >
                <Edit2 className="w-4 h-4" />
                Editar Formulario
              </button>
              <button className="flex-1 flex items-center justify-center gap-2 px-4 py-2.5 bg-emerald-500 text-white rounded-lg hover:bg-emerald-600 transition-colors text-sm font-medium">
                <Share2 className="w-4 h-4" />
                Compartir Informe
              </button>
            </div>
          </div>
        )}
        </div>
      </div>
    </div>
  )
}

export default PredictionForm
