#!/bin/bash

# Install frontend dependencies
echo "📦 Installing frontend dependencies..."
cd /workspace/frontend
npm install

# Install ML service dependencies
echo "🤖 Installing ML service dependencies..."
cd /workspace/ml-service
pip3 install --break-system-packages -r requirements.txt

# Build backend (download dependencies)
echo "☕ Setting up backend dependencies..."
cd /workspace/backend
./mvnw dependency:resolve

echo "✅ Setup complete!"

