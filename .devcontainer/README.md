# ChurnInsight Devcontainer

## Levantar servicios

```bash
# Frontend (puerto 3000)
cd frontend && npm run dev

# Backend (puerto 8080)
cd backend && ./mvnw spring-boot:run

# ML Service (puerto 8000)
cd ml-service && uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
```

## Verificar MySQL

```bash
# Check status
mysqladmin ping -h db -u root -prootpassword

# Conectar
mysql -h db -u churnuser -pchurnpass churninsight
```
