import Dashboard from './pages/Dashboard'
import PredictionPage from './pages/PredictionPage'

function App() {
  // Simple router basado en hash
  const path = window.location.hash.slice(1) || '/'
  
  if (path === '/predicciones') {
    return <PredictionPage />
  }
  
  return <Dashboard />
}

// Escuchar cambios de hash
window.addEventListener('hashchange', () => {
  window.location.reload()
})

export default App
