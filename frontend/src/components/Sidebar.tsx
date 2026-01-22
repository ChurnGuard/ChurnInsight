import { LayoutDashboard, Users, FileText, Activity } from 'lucide-react'

const Sidebar = () => {
  const navItems = [
    { icon: LayoutDashboard, label: 'Tablero', active: true },
    { icon: Activity, label: 'Predicciones', active: false },
    { icon: Users, label: 'Clientes', active: false },
    { icon: FileText, label: 'Reportes', active: false },
  ]

  return (
    <aside className="fixed left-0 top-0 w-64 h-screen bg-slate-900 border-r border-slate-800 flex flex-col overflow-hidden">
      {/* Logo */}
      <div className="p-6 border-b border-slate-800 flex-shrink-0">
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 bg-emerald-500 rounded-lg flex items-center justify-center">
            <Activity className="w-6 h-6 text-white" />
          </div>
          <span className="text-xl font-bold text-white">ChurnInsight</span>
        </div>
      </div>

      {/* Navegación */}
      <nav className="flex-1 p-4 overflow-y-auto">
        <ul className="space-y-2">
          {navItems.map((item) => (
            <li key={item.label}>
              <a
                href="#"
                className={`flex items-center gap-3 px-4 py-3 rounded-lg transition-colors ${
                  item.active
                    ? 'bg-slate-800 text-white'
                    : 'text-slate-400 hover:bg-slate-800/50 hover:text-white'
                }`}
              >
                <item.icon className="w-5 h-5" />
                <span className="font-medium">{item.label}</span>
              </a>
            </li>
          ))}
        </ul>
      </nav>

      {/* Usuario */}
      <div className="p-4 border-t border-slate-800 flex-shrink-0">
        <div className="flex items-center gap-3 px-4 py-3">
          <div className="w-10 h-10 bg-slate-700 rounded-full flex items-center justify-center">
            <span className="text-sm font-semibold text-white">AR</span>
          </div>
          <div className="flex-1">
            <p className="text-sm font-medium text-white">Alex Rivera</p>
            <p className="text-xs text-slate-400">Analista Senior</p>
          </div>
        </div>
      </div>
    </aside>
  )
}

export default Sidebar
