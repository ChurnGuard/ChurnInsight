import { Bell, User } from 'lucide-react'

const Header = () => {
  return (
    <header className="bg-slate-900/50 border-b border-slate-800 px-8 py-4">
      <div className="flex items-center justify-end">
        {/* Acciones */}
        <div className="flex items-center gap-4">
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
