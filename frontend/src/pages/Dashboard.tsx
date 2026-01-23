import { useState, useEffect } from 'react'
import Sidebar from '../components/Sidebar'
import Header from '../components/Header'
import CriticalCustomerCard from '../components/CriticalCustomerCard'
import { customerService } from '../services/customerService'
import { transformCriticalCustomers } from '../utils/customerMapper'
import { ClienteUI } from '../types/customer.types'

const Dashboard = () => {
  const [clientes, setClientes] = useState<ClienteUI[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  // Cargar datos del backend al montar el componente
  useEffect(() => {
    const fetchCriticalCustomers = async () => {
      try {
        setLoading(true)
        setError(null)
        
        const data = await customerService.getCriticalCustomers()
        const transformedData = transformCriticalCustomers(data)
        setClientes(transformedData)
      } catch (err) {
        console.error('Error al cargar clientes críticos:', err)
        setError('No se pudieron cargar los clientes críticos. Por favor, intente de nuevo.')
      } finally {
        setLoading(false)
      }
    }

    fetchCriticalCustomers()
  }, [])

  return (
    <div className="flex min-h-screen bg-slate-950">
      {/* Sidebar */}
      <Sidebar currentPage="tablero" />

      {/* Contenido Principal - con margen para el sidebar fijo */}
      <div className="flex-1 flex flex-col ml-64">
        {/* Header */}
        <Header />

        {/* Área de Contenido */}
        <main className="flex-1 p-8 overflow-auto">
          {/* Título y Filtros */}
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-white mb-2">
              Tablero de Gestión de Riesgo Crítico
            </h1>
            <p className="text-slate-400">
              Monitoreo individual de usuarios con riesgo de abandono superior al 85%.
            </p>
          </div>

          {/* Estados de Carga y Error */}
          {loading && (
            <div className="flex justify-center items-center py-12">
              <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-cyan-500"></div>
              <span className="ml-4 text-slate-400">Cargando clientes críticos...</span>
            </div>
          )}

          {error && (
            <div className="bg-red-500/10 border border-red-500 rounded-lg p-4 text-red-400">
              {error}
            </div>
          )}

          {/* Grid de Clientes Críticos */}
          {!loading && !error && (
            <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
              {clientes.length > 0 ? (
                clientes.map((cliente, index) => (
                  <CriticalCustomerCard key={index} {...cliente} />
                ))
              ) : (
                <div className="col-span-full text-center py-12 text-slate-400">
                  No hay clientes críticos en este momento.
                </div>
              )}
            </div>
          )}
        </main>
      </div>
    </div>
  )
}

export default Dashboard
