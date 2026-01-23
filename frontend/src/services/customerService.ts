import api from './api';
import { CriticalCustomer } from '../types/customer.types';

/**
 * Servicio para manejar todas las peticiones relacionadas con clientes
 */
export const customerService = {
  /**
   * Obtiene la lista de clientes críticos con alta probabilidad de churn
   */
  getCriticalCustomers: async (): Promise<CriticalCustomer[]> => {
    const response = await api.get<CriticalCustomer[]>('/customers/critical');
    return response.data;
  }
};
