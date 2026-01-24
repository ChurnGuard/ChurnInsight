import axios from 'axios';

// Configuración base de Axios
const api = axios.create({
  baseURL: '/api', // Vite proxy manejará esto
  timeout: 10000, // 10 segundos
  headers: {
    'Content-Type': 'application/json',
  }
});

// Interceptor para manejar errores globalmente (opcional)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('Error en la petición:', error);
    return Promise.reject(error);
  }
);

export default api;
