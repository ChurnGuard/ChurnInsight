import { useState } from 'react'
import { User, Briefcase, CreditCard, Sparkles, TrendingUp, Lightbulb, Edit2, Share2, ChevronLeft, ChevronRight, AlertCircle, AlertTriangle, CheckCircle } from 'lucide-react'
import { predictionService, PredictionResponse } from '../services/predictionService'

interface PredictionResult {
  riesgo: number
  puntajePrioridad: number
  clienteAltoValor: boolean
  perfil: string
  factores: string[]
  accionRecomendada: string
  churn: boolean
}

const PredictionForm = () => {
  const [isPanelOpen, setIsPanelOpen] = useState(false)
  const [formData, setFormData] = useState({
    // Identificación
    customer_id: '',
    transaction_id: '',
    transaction_date: '',
    
    // Demografía
    age: '',
    gender: '',
    marital_status: '',
    number_of_children: '',
    income_bracket: '',
    education_level: '',
    occupation: '',
    
    // Membresía
    loyalty_program: true,
    promo_flag: true,
    membership_years: '',
    
    // Transacción
    product_category: '',
    quantity: '',
    unit_price: '',
    promotion_type: '',
    
    // Historial de Compras
    last_purchase_date: '',
    days_since_last_purchase: '',
    total_purchases: '',
    total_transactions: '',
    total_items_purchased: '',
    total_sales: '',
    avg_purchase_value: '',
    purchase_frequency: '',
    avg_discount_used: '',
    promotion_effectiveness: '',
    
    // Canal de Compra
    online_purchases: '',
    in_store_purchases: '',
    online_ratio: ''
  })

  const [predictionResult, setPredictionResult] = useState<PredictionResult | null>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)
    setError(null)
    
    try {
      // Convertir formData a tipos correctos
      const requestData = {
        customer_id: formData.customer_id,
        transaction_id: formData.transaction_id,
        transaction_date: formData.transaction_date,
        age: parseInt(formData.age),
        gender: formData.gender,
        marital_status: formData.marital_status,
        number_of_children: parseInt(formData.number_of_children) || 0,
        income_bracket: formData.income_bracket,
        education_level: formData.education_level,
        occupation: formData.occupation,
        loyalty_program: formData.loyalty_program === 'true',
        promo_flag: formData.promo_flag === 'true',
        membership_years: parseInt(formData.membership_years) || 0,
        last_purchase_date: formData.last_purchase_date,
        product_category: formData.product_category,
        quantity: parseInt(formData.quantity) || 1,
        unit_price: parseFloat(formData.unit_price) || 0,
        promotion_type: formData.promotion_type || 'NONE',
        days_since_last_purchase: parseInt(formData.days_since_last_purchase) || 0,
        total_purchases: parseInt(formData.total_purchases) || 0,
        total_transactions: parseInt(formData.total_transactions) || 0,
        total_items_purchased: parseInt(formData.total_items_purchased) || 0,
        total_sales: parseFloat(formData.total_sales) || 0,
        avg_purchase_value: parseFloat(formData.avg_purchase_value) || 0,
        purchase_frequency: parseFloat(formData.purchase_frequency) || 0,
        avg_discount_used: parseFloat(formData.avg_discount_used) || 0,
        promotion_effectiveness: parseFloat(formData.promotion_effectiveness) || 0,
        online_purchases: parseInt(formData.online_purchases) || 0,
        in_store_purchases: parseInt(formData.in_store_purchases) || 0,
        online_ratio: parseFloat(formData.online_ratio) || 0
      }

      const response: PredictionResponse = await predictionService.predict(requestData)
      
      // Transformar respuesta del backend al formato del UI
      const result: PredictionResult = {
        riesgo: Math.round(response.probability_churn * 100),
        puntajePrioridad: Math.round(response.priority_score * 100),
        clienteAltoValor: response.economic_value === 'HIGH_VALUE_CUSTOMER',
        perfil: response.customer_profile,
        factores: response.risk_flags,
        accionRecomendada: response.recommended_action,
        churn: response.churn
      }
      
      setPredictionResult(result)
      setIsPanelOpen(true)
      
      // Scroll hacia arriba para ver los resultados
      window.scrollTo({ top: 0, behavior: 'smooth' })
    } catch (err) {
      console.error('Error al generar predicción:', err)
      setError(err instanceof Error ? err.message : 'Error al procesar la predicción')
    } finally {
      setLoading(false)
    }
  }

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    })
  }

  const resetPrediction = () => {
    setPredictionResult(null)
    setError(null)
    setIsPanelOpen(false)
    setFormData({
      customer_id: '',
      transaction_id: '',
      transaction_date: '',
      age: '',
      gender: '',
      marital_status: '',
      number_of_children: '',
      income_bracket: '',
      education_level: '',
      occupation: '',
      loyalty_program: true,
      promo_flag: true,
      membership_years: '',
      product_category: '',
      quantity: '',
      unit_price: '',
      promotion_type: '',
      last_purchase_date: '',
      days_since_last_purchase: '',
      total_purchases: '',
      total_transactions: '',
      total_items_purchased: '',
      total_sales: '',
      avg_purchase_value: '',
      purchase_frequency: '',
      avg_discount_used: '',
      promotion_effectiveness: '',
      online_purchases: '',
      in_store_purchases: '',
      online_ratio: ''
    })
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
            {/* Sección: Identificación */}
            <div className="bg-slate-900 border border-slate-800 rounded-xl p-6">
              <div className="flex items-center gap-2 mb-6">
                <User className="w-5 h-5 text-emerald-500" />
                <h2 className="text-lg font-semibold text-white">Identificación</h2>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    ID de Cliente <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="text"
                    name="customer_id"
                    value={formData.customer_id}
                    onChange={handleChange}
                    placeholder="C1000"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    ID de Transacción <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="text"
                    name="transaction_id"
                    value={formData.transaction_id}
                    onChange={handleChange}
                    placeholder="T1000"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Fecha de Transacción <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="text"
                    name="transaction_date"
                    value={formData.transaction_date}
                    onChange={handleChange}
                    placeholder="01/07/2025"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>
              </div>
            </div>

            {/* Sección: Demografía */}
            <div className="bg-slate-900 border border-slate-800 rounded-xl p-6">
              <div className="flex items-center gap-2 mb-6">
                <User className="w-5 h-5 text-blue-500" />
                <h2 className="text-lg font-semibold text-white">Demografía</h2>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Edad <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    name="age"
                    value={formData.age}
                    onChange={handleChange}
                    placeholder="33"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Género <span className="text-rose-500">*</span>
                  </label>
                  <select
                    name="gender"
                    value={formData.gender}
                    onChange={handleChange}
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  >
                    <option value="">Seleccionar</option>
                    <option value="Male">Masculino</option>
                    <option value="Female">Femenino</option>
                    <option value="Other">Otro</option>
                  </select>
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Estado Civil <span className="text-rose-500">*</span>
                  </label>
                  <select
                    name="marital_status"
                    value={formData.marital_status}
                    onChange={handleChange}
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  >
                    <option value="">Seleccionar</option>
                    <option value="Single">Soltero/a</option>
                    <option value="Married">Casado/a</option>
                    <option value="Divorced">Divorciado/a</option>
                  </select>
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Número de Hijos <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    name="number_of_children"
                    value={formData.number_of_children}
                    onChange={handleChange}
                    placeholder="0"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Nivel de Ingresos <span className="text-rose-500">*</span>
                  </label>
                  <select
                    name="income_bracket"
                    value={formData.income_bracket}
                    onChange={handleChange}
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  >
                    <option value="">Seleccionar</option>
                    <option value="Low">Bajo</option>
                    <option value="Medium">Medio</option>
                    <option value="High">Alto</option>
                  </select>
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Nivel de Educación <span className="text-rose-500">*</span>
                  </label>
                  <select
                    name="education_level"
                    value={formData.education_level}
                    onChange={handleChange}
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  >
                    <option value="">Seleccionar</option>
                    <option value="High_School">Secundaria</option>
                    <option value="Bachelor_s">Licenciatura</option>
                    <option value="Master_s">Maestría</option>
                    <option value="PhD">Doctorado</option>
                  </select>
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Ocupación <span className="text-rose-500">*</span>
                  </label>
                  <select
                    name="occupation"
                    value={formData.occupation}
                    onChange={handleChange}
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  >
                    <option value="">Seleccionar</option>
                    <option value="Employed">Empleado</option>
                    <option value="Self-Employed">Autónomo</option>
                    <option value="Unemployed">Desempleado</option>
                    <option value="Student">Estudiante</option>
                    <option value="Retired">Jubilado</option>
                  </select>
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Años de Membresía <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    name="membership_years"
                    value={formData.membership_years}
                    onChange={handleChange}
                    placeholder="1"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-6">
                <div className="flex items-center gap-3 bg-slate-950 rounded-lg px-4 py-3 border border-slate-800">
                  <input
                    type="checkbox"
                    name="loyalty_program"
                    checked={formData.loyalty_program}
                    onChange={(e) => setFormData({ ...formData, loyalty_program: e.target.checked })}
                    className="w-5 h-5 rounded border-slate-700 text-emerald-500 focus:ring-emerald-500 focus:ring-offset-0"
                  />
                  <label className="text-sm font-medium text-white">Programa de Lealtad</label>
                </div>

                <div className="flex items-center gap-3 bg-slate-950 rounded-lg px-4 py-3 border border-slate-800">
                  <input
                    type="checkbox"
                    name="promo_flag"
                    checked={formData.promo_flag}
                    onChange={(e) => setFormData({ ...formData, promo_flag: e.target.checked })}
                    className="w-5 h-5 rounded border-slate-700 text-emerald-500 focus:ring-emerald-500 focus:ring-offset-0"
                  />
                  <label className="text-sm font-medium text-white">Recibe Promociones</label>
                </div>
              </div>
            </div>

            {/* Sección: Transacción */}
            <div className="bg-slate-900 border border-slate-800 rounded-xl p-6">
              <div className="flex items-center gap-2 mb-6">
                <Briefcase className="w-5 h-5 text-amber-500" />
                <h2 className="text-lg font-semibold text-white">Transacción Actual</h2>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Categoría de Producto <span className="text-rose-500">*</span>
                  </label>
                  <select
                    name="product_category"
                    value={formData.product_category}
                    onChange={handleChange}
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  >
                    <option value="">Seleccionar</option>
                    <option value="Electronics">Electrónicos</option>
                    <option value="Groceries">Comestibles</option>
                    <option value="Clothing">Ropa</option>
                    <option value="Home_Goods">Hogar</option>
                    <option value="Beauty">Belleza</option>
                    <option value="Sports">Deportes</option>
                    <option value="Books">Libros</option>
                    <option value="Toys">Juguetes</option>
                    <option value="Home">Casa</option>
                  </select>
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Cantidad <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    name="quantity"
                    value={formData.quantity}
                    onChange={handleChange}
                    placeholder="1"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Precio Unitario <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    step="0.01"
                    name="unit_price"
                    value={formData.unit_price}
                    onChange={handleChange}
                    placeholder="56.00"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Tipo de Promoción <span className="text-rose-500">*</span>
                  </label>
                  <select
                    name="promotion_type"
                    value={formData.promotion_type}
                    onChange={handleChange}
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  >
                    <option value="">Seleccionar</option>
                    <option value="No_Promotion">Sin Promoción</option>
                    <option value="Twenty_Percent_Off">20% de Descuento</option>
                    <option value="Buy_One_Get_One_Free">2x1</option>
                    <option value="Seasonal_Discount">Descuento Estacional</option>
                  </select>
                </div>
              </div>
            </div>

            {/* Sección: Historial de Compras */}
            <div className="bg-slate-900 border border-slate-800 rounded-xl p-6">
              <div className="flex items-center gap-2 mb-6">
                <CreditCard className="w-5 h-5 text-rose-500" />
                <h2 className="text-lg font-semibold text-white">Historial de Compras</h2>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Última Compra <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="text"
                    name="last_purchase_date"
                    value={formData.last_purchase_date}
                    onChange={handleChange}
                    placeholder="01/07/2025"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Días desde Última Compra <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    name="days_since_last_purchase"
                    value={formData.days_since_last_purchase}
                    onChange={handleChange}
                    placeholder="3"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Total de Compras <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    name="total_purchases"
                    value={formData.total_purchases}
                    onChange={handleChange}
                    placeholder="200"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Total de Transacciones <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    name="total_transactions"
                    value={formData.total_transactions}
                    onChange={handleChange}
                    placeholder="1002"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Artículos Comprados <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    name="total_items_purchased"
                    value={formData.total_items_purchased}
                    onChange={handleChange}
                    placeholder="400"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Ventas Totales ($) <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    step="0.01"
                    name="total_sales"
                    value={formData.total_sales}
                    onChange={handleChange}
                    placeholder="1002.00"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Valor Promedio de Compra ($) <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    step="0.01"
                    name="avg_purchase_value"
                    value={formData.avg_purchase_value}
                    onChange={handleChange}
                    placeholder="2.00"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Frecuencia de Compra <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    step="0.01"
                    name="purchase_frequency"
                    value={formData.purchase_frequency}
                    onChange={handleChange}
                    placeholder="0"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Descuento Promedio Usado <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    step="0.01"
                    name="avg_discount_used"
                    value={formData.avg_discount_used}
                    onChange={handleChange}
                    placeholder="1.00"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Efectividad de Promoción <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    step="0.01"
                    name="promotion_effectiveness"
                    value={formData.promotion_effectiveness}
                    onChange={handleChange}
                    placeholder="0.00"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Compras Online <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    name="online_purchases"
                    value={formData.online_purchases}
                    onChange={handleChange}
                    placeholder="2"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Compras en Tienda <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    name="in_store_purchases"
                    value={formData.in_store_purchases}
                    onChange={handleChange}
                    placeholder="1"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-white mb-2">
                    Ratio Online <span className="text-rose-500">*</span>
                  </label>
                  <input
                    type="number"
                    step="0.01"
                    name="online_ratio"
                    value={formData.online_ratio}
                    onChange={handleChange}
                    placeholder="1.00"
                    required
                    className="w-full bg-slate-950 border border-slate-800 rounded-lg px-4 py-2.5 text-white placeholder-slate-500 focus:outline-none focus:ring-2 focus:ring-emerald-500"
                  />
                </div>
              </div>
            </div>

            {/* Mensaje de Error */}
            {error && (
              <div className="flex items-center gap-3 bg-red-500/10 border border-red-500 rounded-lg p-4 text-red-400">
                <AlertCircle className="w-5 h-5 flex-shrink-0" />
                <p>{error}</p>
              </div>
            )}

            {/* Botón de Submit */}
            <div className="flex justify-end">
              <button
                type="submit"
                disabled={loading}
                className="flex items-center gap-2 bg-emerald-500 hover:bg-emerald-600 disabled:bg-gray-500 disabled:cursor-not-allowed text-white font-semibold px-8 py-3 rounded-xl transition-colors"
              >
                {loading ? (
                  <>
                    <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-white"></div>
                    Procesando...
                  </>
                ) : (
                  <>
                    <Sparkles className="w-5 h-5" />
                    Generar Predicción
                  </>
                )}
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
        className={`bg-slate-950 border-l border-slate-800 transition-all duration-300 ${
          isPanelOpen ? 'w-[380px]' : 'w-0'
        }`}
      >
        <div className={`h-full overflow-y-auto ${isPanelOpen ? 'px-6 py-8' : ''}`}>
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

                {/* Badge Churn - Dinámico */}
                {predictionResult.churn ? (
                  <div className="flex items-center gap-2 px-3 py-1.5 bg-rose-500/10 border border-rose-500/20 rounded-full mb-3">
                    <AlertTriangle className="w-4 h-4 text-rose-500" />
                    <span className="text-xs font-semibold text-rose-500 uppercase">Cliente con Riesgo de Abandono</span>
                  </div>
                ) : (
                  <div className="flex items-center gap-2 px-3 py-1.5 bg-emerald-500/10 border border-emerald-500/20 rounded-full mb-3">
                    <CheckCircle className="w-4 h-4 text-emerald-500" />
                    <span className="text-xs font-semibold text-emerald-500 uppercase">Cliente Estable</span>
                  </div>
                )}

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
                <h3 className="text-sm font-semibold text-slate-100">Factores de Riesgo</h3>
              </div>
              <div className="space-y-2">
                {predictionResult.factores.length > 0 ? (
                  predictionResult.factores.map((factor, index) => (
                    <div key={index} className="flex items-center gap-2 text-xs text-slate-300 bg-slate-800 rounded-lg p-3">
                      <div className="w-2 h-2 rounded-full bg-rose-500"></div>
                      <span>{factor.replace(/_/g, ' ')}</span>
                    </div>
                  ))
                ) : (
                  <p className="text-xs text-slate-400">No se detectaron factores de riesgo significativos</p>
                )}
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
