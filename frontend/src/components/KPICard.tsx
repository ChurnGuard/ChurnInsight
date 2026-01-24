import { LucideIcon } from 'lucide-react'

interface KPICardProps {
  label: string
  value: string
  color?: 'default' | 'amber' | 'rose' | 'emerald'
  icon: LucideIcon
  trend?: {
    value: string
    isPositive: boolean
  }
}

const KPICard = ({ label, value, color = 'default', icon: Icon, trend }: KPICardProps) => {
  const colorClasses = {
    default: 'text-white',
    amber: 'text-amber-500',
    rose: 'text-rose-500',
    emerald: 'text-emerald-500',
  }

  const iconColorClasses = {
    default: 'text-slate-400',
    amber: 'text-amber-500',
    rose: 'text-rose-500',
    emerald: 'text-emerald-500',
  }

  return (
    <div className="bg-slate-900 border border-slate-800 rounded-xl p-6">
      <div className="flex items-start justify-between mb-4">
        <div className="flex-1">
          <p className="text-sm text-slate-400 mb-1">{label}</p>
          <p className={`text-3xl font-bold ${colorClasses[color]}`}>{value}</p>
        </div>
        <div className={`p-3 bg-slate-800 rounded-lg ${iconColorClasses[color]}`}>
          <Icon className="w-6 h-6" />
        </div>
      </div>
      
      {trend && (
        <div className="flex items-center gap-1">
          <span className={`text-sm font-medium ${trend.isPositive ? 'text-emerald-500' : 'text-rose-500'}`}>
            {trend.isPositive ? '↑' : '↓'} {trend.value}
          </span>
          <span className="text-xs text-slate-400">vs mes anterior</span>
        </div>
      )}
    </div>
  )
}

export default KPICard
