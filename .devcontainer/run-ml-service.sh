#!/bin/bash
cd /workspace/ml-service
echo "🤖 Starting ML Service (FastAPI)..."
uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
