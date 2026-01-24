import api from './api';
import { CriticalCustomer } from '../types/customer.types';

export interface PredictionInfo {
  id: number
  customer_id: string
  probability_churn: number
  churn: string
  recommended_action: string
  prediction_date: string
}

export interface PageResponse<T> {
  content: T[]
  totalElements: number
  totalPages: number
  size: number
  number: number
  first: boolean
  last: boolean
}

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
  },

  /**
   * Obtiene una página de predicciones con paginación
   */
  getPredictions: async (page: number = 0, size: number = 10): Promise<PageResponse<PredictionInfo>> => {
    const response = await api.get<PageResponse<PredictionInfo>>(`/predictions/info-de-paginacion?page=${page}&size=${size}`);
    return response.data;
  }
};
