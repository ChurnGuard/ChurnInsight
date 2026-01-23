// Usar ruta relativa para que Vite proxy redirija a localhost:8080
const API_BASE_URL = '/api/v1'

export interface PredictionRequest {
  customer_id: string
  transaction_id: string
  transaction_date: string
  age: number
  gender: string
  marital_status: string
  number_of_children: number
  income_bracket: string
  education_level: string
  occupation: string
  loyalty_program: boolean
  promo_flag: boolean
  membership_years: number
  last_purchase_date: string
  product_category: string
  quantity: number
  unit_price: number
  promotion_type: string
  days_since_last_purchase: number
  total_purchases: number
  total_transactions: number
  total_items_purchased: number
  total_sales: number
  avg_purchase_value: number
  purchase_frequency: number
  avg_discount_used: number
  promotion_effectiveness: number
  online_purchases: number
  in_store_purchases: number
  online_ratio: number
}

export interface PredictionResponse {
  customer_id: string
  probability_churn: number
  churn: boolean
  economic_value: string
  priority_score: number
  risk_flags: string[]
  customer_profile: string
  recommended_action: string
}

export const predictionService = {
  async predict(data: PredictionRequest): Promise<PredictionResponse> {
    const response = await fetch(`${API_BASE_URL}/predictions`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    })

    if (!response.ok) {
      const error = await response.json()
      throw new Error(error.message || 'Error al procesar la predicción')
    }

    return response.json()
  }
}
