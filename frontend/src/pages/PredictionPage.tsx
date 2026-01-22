import Sidebar from '../components/Sidebar'
import Header from '../components/Header'
import PredictionForm from './PredictionForm'

const PredictionPage = () => {
  return (
    <div className="flex min-h-screen bg-slate-950">
      {/* Sidebar */}
      <Sidebar currentPage="predicciones" />

      {/* Contenido Principal - con margen para el sidebar fijo */}
      <div className="flex-1 flex flex-col ml-64">
        {/* Header */}
        <Header />

        {/* Área de Contenido */}
        <main className="flex-1 p-8 overflow-auto">
          <PredictionForm />
        </main>
      </div>
    </div>
  )
}

export default PredictionPage
