import { useState } from 'react'
import { User, Briefcase, CreditCard, Sparkles } from 'lucide-react'

const PredictionForm = () => {
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

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    console.log('Generando predicción...', formData)
    // Aquí iría la llamada al API
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  return (
    <div className="max-w-4xl mx-auto">
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
                placeholder="CID: 6B291Q-X"
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
                placeholder="ej: 34"
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
                placeholder="Ingresar país"
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
                className="w-full bg-slate-800 border border-slate-700 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
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
  )
}

export default PredictionForm
