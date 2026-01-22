import Sidebar from '../components/Sidebar'
import Header from '../components/Header'
import { UserCog } from 'lucide-react'

const AgentsPage = () => {
  return (
    <div className="flex min-h-screen bg-slate-950">
      {/* Sidebar */}
      <Sidebar currentPage="agentes" />

      {/* Contenido Principal - con margen para el sidebar fijo */}
      <div className="flex-1 flex flex-col ml-64">
        {/* Header */}
        <Header />

        {/* Área de Contenido */}
        <main className="flex-1 flex items-center justify-center p-8">
          <div className="text-center max-w-md">
            {/* Icono */}
            <div className="inline-flex items-center justify-center w-24 h-24 bg-slate-900 rounded-2xl mb-6 border border-slate-800">
              <UserCog className="w-12 h-12 text-emerald-500" />
            </div>

            {/* Badge */}
            <div className="inline-block mb-6">
              <span className="px-3 py-1 bg-emerald-500/10 border border-emerald-500/20 rounded-full text-xs font-semibold text-emerald-500 uppercase tracking-wider">
                Próximamente
              </span>
            </div>

            {/* Título */}
            <h1 className="text-3xl font-bold text-white mb-4">
              Sección de Agentes
            </h1>

            {/* Descripción */}
            <p className="text-slate-400 text-lg leading-relaxed">
              Esta funcionalidad estará disponible próximamente para gestionar el rendimiento de tu equipo.
            </p>

            {/* Dots decorativos */}
            <div className="flex items-center justify-center gap-2 mt-8">
              <div className="w-2 h-2 bg-slate-700 rounded-full"></div>
              <div className="w-2 h-2 bg-slate-700 rounded-full"></div>
              <div className="w-2 h-2 bg-slate-700 rounded-full"></div>
            </div>
          </div>
        </main>
      </div>
    </div>
  )
}

export default AgentsPage
