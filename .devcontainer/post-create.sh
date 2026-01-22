#!/bin/bash

echo "🚀 Configurando entorno de desarrollo ChurnInsight..."

# Instalar dependencias Python para ml-service
echo "📦 Instalando dependencias de Python (ml-service)..."
pip install --user -r ml-service/requirements.txt

echo "✅ Configuración completada!"
echo ""
echo "📝 Servicios disponibles:"
echo "  - Backend (Java Spring Boot): http://localhost:8080"
echo "  - ML Service (FastAPI): http://localhost:8000"
echo "  - MySQL: localhost:3306"
echo ""
echo "🔧 Para iniciar los servicios, ejecuta: docker-compose up -d"
