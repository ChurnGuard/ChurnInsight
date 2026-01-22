import { Bell, User, Search, Filter } from 'lucide-react'

const Header = () => {
  return (
    <header className="bg-slate-900/50 border-b border-slate-800 px-8 py-4">
      <div className="flex items-center justify-between">
        {/* Búsqueda */}
        <div className="relative">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-slate-400" />
          <input
            type="text"
            placeholder="Buscar clientes críticos..."
            className="bg-slate-800 border border-slate-700 rounded-lg pl-10 pr-4 py-2 text-sm text-white placeholder-slate-400 focus:outline-none focus:ring-2 focus:ring-emerald-500 w-96"
          />
        </div>

        {/* Acciones */}
        <div className="flex items-center gap-4">
          {/* Filtro */}
          <button className="flex items-center gap-2 px-4 py-2 bg-slate-800 hover:bg-slate-700 rounded-lg transition-colors text-sm text-white">
            <span className="text-slate-400 uppercase text-xs">Filtrar por:</span>
            <span>Mayor Riesgo</span>
            <Filter className="w-4 h-4 text-slate-400" />
          </button>

          {/* Notificaciones */}
          <button className="relative p-2 hover:bg-slate-800 rounded-lg transition-colors">
            <Bell className="w-5 h-5 text-slate-400" />
            <span className="absolute top-1 right-1 w-2 h-2 bg-rose-500 rounded-full"></span>
          </button>

          {/* Perfil */}
          <button className="p-2 hover:bg-slate-800 rounded-lg transition-colors">
            <User className="w-5 h-5 text-slate-400" />
          </button>
        </div>
      </div>
    </header>
  )
}

export default Header
