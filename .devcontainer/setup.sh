#!/bin/bash

echo "🚀 Setting up ChurnInsight development environment..."

# Wait for MySQL to be ready
echo "⏳ Waiting for MySQL to be ready..."
until mysqladmin ping -h localhost -u root -prootpassword --silent; do
    echo "Waiting for MySQL..."
    sleep 2
done
echo "✅ MySQL is ready!"

# Install frontend dependencies
echo "📦 Installing frontend dependencies..."
cd /workspace/frontend
npm install

# Install ML service dependencies
echo "🤖 Installing ML service dependencies..."
cd /workspace/ml-service
pip3 install -r requirements.txt

# Build backend (download dependencies)
echo "☕ Setting up backend dependencies..."
cd /workspace/backend
./mvnw dependency:resolve

echo "✅ Setup complete!"
echo ""
echo "🎯 Quick start commands:"
echo "  1. MySQL is already running!"
echo "  2. Frontend:    cd frontend && npm run dev"
echo "  3. Backend:     cd backend && ./mvnw spring-boot:run"
echo "  4. ML Service:  cd ml-service && uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload"
echo ""
echo "💡 Or use the run scripts:"
echo "  - bash .devcontainer/run-frontend.sh"
echo "  - bash .devcontainer/run-backend.sh"
echo "  - bash .devcontainer/run-ml-service.sh"
