// Tipos para la respuesta del backend
export interface CriticalCustomer {
  customer_id: string;
  probability_churn: number;
  economic_value: 'HIGH_VALUE_CUSTOMER' | 'MEDIUM_VALUE_CUSTOMER' | 'LOW_VALUE_CUSTOMER';
  priority_score: number;
  recommended_action: string;
}

// Tipos para el frontend (UI)
export interface ClienteUI {
  nombre: string;
  apellido: string;
  riesgo: number;
  valorEconomico: 'Cliente de Alto Valor' | 'Cliente de Valor Medio' | 'Cliente de Bajo Valor';
  puntajePrioridad: number;
  accionSugerida: string;
  avatarColor: string;
}
