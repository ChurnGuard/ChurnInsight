#!/bin/bash

echo "Configurando entorno de desarrollo ChurnInsight..."

# Instalar dependencias Python para ml-service
echo "📦 Instalando dependencias de Python (ml-service)..."
pip install --user -r ml-service/requirements.txt || echo "⚠️  Error instalando dependencias Python (se pueden instalar después)"

# Instalar dependencias del frontend
echo "📦 Instalando dependencias de Node.js (frontend)..."
cd /workspace/frontend && npm install || echo "⚠️  Error instalando dependencias npm (se pueden instalar después)"
cd /workspace