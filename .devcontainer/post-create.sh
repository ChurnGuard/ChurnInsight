#!/bin/bash

echo "🚀 Configurando entorno de desarrollo ChurnInsight..."

# Instalar dependencias Python para ml-service
echo "📦 Instalando dependencias de Python (ml-service)..."
pip install --user -r ml-service/requirements.txt || echo "⚠️  Error instalando dependencias Python (se pueden instalar después)"

# Instalar dependencias del frontend
echo "📦 Instalando dependencias de Node.js (frontend)..."
cd /workspace/frontend && npm install || echo "⚠️  Error instalando dependencias npm (se pueden instalar después)"
cd /workspace

echo "✅ Configuración completada!"
echo ""
echo "📝 Servicios disponibles:"
echo "  - Frontend (React + Vite): http://localhost:3000"
echo "  - Backend (Java Spring Boot): http://localhost:8080"
echo "  - ML Service (FastAPI): http://localhost:8000"
echo "  - MySQL: localhost:3306"
echo ""
echo "📝 Los servicios backend, ml-service y db están corriendo en contenedores separados."
echo "   Puedes accederlos desde el devcontainer usando sus nombres: backend, ml-service, db"
echo ""
echo "🎨 Para iniciar el frontend en dev, ejecuta: cd frontend && npm run dev"
