import Dashboard from './pages/Dashboard'
import PredictionPage from './pages/PredictionPage'
import CustomerListPage from './pages/CustomerListPage'
import AgentsPage from './pages/AgentsPage'

function App() {
  // Simple router basado en hash
  const path = window.location.hash.slice(1) || '/'
  
  if (path === '/predicciones') {
    return <PredictionPage />
  }
  
  if (path === '/clientes') {
    return <CustomerListPage />
  }
  
  if (path === '/agentes') {
    return <AgentsPage />
  }
  
  return <Dashboard />
}

// Escuchar cambios de hash
window.addEventListener('hashchange', () => {
  window.location.reload()
})

export default App
