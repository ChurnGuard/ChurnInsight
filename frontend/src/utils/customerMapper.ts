import { CriticalCustomer, ClienteUI } from '../types/customer.types';

// Colores para los avatares (se asignan de forma determinística)
const AVATAR_COLORS = [
  '#64748b', '#475569', '#0891b2', '#84cc16', 
  '#f59e0b', '#ef4444', '#8b5cf6', '#ec4899'
];

/**
 * Mapea el valor económico del backend al formato de UI
 */
const mapEconomicValue = (value: string): ClienteUI['valorEconomico'] => {
  switch (value) {
    case 'HIGH_VALUE_CUSTOMER':
      return 'Cliente de Alto Valor';
    case 'MEDIUM_VALUE_CUSTOMER':
      return 'Cliente de Valor Medio';
    case 'LOW_VALUE_CUSTOMER':
      return 'Cliente de Bajo Valor';
    default:
      return 'Cliente de Valor Medio';
  }
};

/**
 * Genera un nombre y apellido aleatorio basado en el customer_id
 * (temporal hasta que el backend envíe nombres reales)
 */
const generateNameFromId = (customerId: string): { nombre: string; apellido: string } => {
  const nombres = ['Elena', 'Marcus', 'Julianna', 'David', 'Sarah', 'Robert', 'María', 'Carlos'];
  const apellidos = ['Sorolla', 'Thorne', 'Reed', 'Chen', 'Jenkins', 'Vance', 'García', 'López'];
  
  // Usar el ID para seleccionar de forma determinística
  const hashCode = customerId.split('').reduce((acc, char) => acc + char.charCodeAt(0), 0);
  
  return {
    nombre: nombres[hashCode % nombres.length],
    apellido: apellidos[(hashCode * 2) % apellidos.length]
  };
};

/**
 * Transforma un cliente crítico del backend al formato de UI
 */
export const transformCriticalCustomer = (customer: CriticalCustomer, index: number): ClienteUI => {
  const { nombre, apellido } = generateNameFromId(customer.customer_id);
  
  return {
    nombre,
    apellido,
    riesgo: customer.probability_churn * 100, // Convertir de 0-1 a 0-100
    valorEconomico: mapEconomicValue(customer.economic_value),
    puntajePrioridad: customer.priority_score * 100, // Convertir de 0-1 a 0-100
    accionSugerida: customer.recommended_action,
    avatarColor: AVATAR_COLORS[index % AVATAR_COLORS.length]
  };
};

/**
 * Transforma un array de clientes críticos del backend al formato de UI
 */
export const transformCriticalCustomers = (customers: CriticalCustomer[]): ClienteUI[] => {
  return customers.map((customer, index) => transformCriticalCustomer(customer, index));
};
