import { useState, useEffect } from 'react'
import Dashboard from './pages/Dashboard'
import PredictionPage from './pages/PredictionPage'
import AgentsPage from './pages/AgentsPage'

function App() {
  const [currentPath, setCurrentPath] = useState(window.location.hash.slice(1) || '/')

  useEffect(() => {
    const handleHashChange = () => {
      setCurrentPath(window.location.hash.slice(1) || '/')
    }

    window.addEventListener('hashchange', handleHashChange)
    return () => window.removeEventListener('hashchange', handleHashChange)
  }, [])

  // Renderizar página
  const renderPage = () => {
    switch (currentPath) {
      case '/predicciones':
        return <PredictionPage />
      case '/agentes':
        return <AgentsPage />
      default:
        return <Dashboard />
    }
  }

  return renderPage()
}

export default App
