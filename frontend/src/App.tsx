import Dashboard from './pages/Dashboard'
import PredictionPage from './pages/PredictionPage'
import CustomerListPage from './pages/CustomerListPage'

function App() {
  // Simple router basado en hash
  const path = window.location.hash.slice(1) || '/'
  
  if (path === '/predicciones') {
    return <PredictionPage />
  }
  
  if (path === '/clientes') {
    return <CustomerListPage />
  }
  
  return <Dashboard />
}

// Escuchar cambios de hash
window.addEventListener('hashchange', () => {
  window.location.reload()
})

export default App
