# Configuración de Entorno

Este archivo contiene las instrucciones necesarias para lograr configurar las variables de entorno para ejecutar el proyecto.

## Setup Rápido

### En desarrollo local:

```bash
# Crea un nuevo arcvhivo .env basado en la plantilla de ejemplo
cp .env.example .env

# Edita el .env con tus credenciales seguras
# Construye y levanta los servicios
docker-compose up --build -d
```

### En Entornos de Desarrollo:

No es necesario crear el archivo `.env` ya que el proyecto usa valores por defecto seguros para desarrollo.

```bash
docker-compose up --build -d
```

#### Credenciales por Defecto

Las siguientes credenciales se usan automáticamente si no existe archivo `.env`:

- **MySQL Root Password:** `rootpassword`
- **Database:** `churninsight`
- **MySQL User:** `churnuser`
- **MySQL Password:** `churnpass`

⚠️ **IMPORTANTE:** Para producción recuerda que siempre debes usar credenciales seguras en tu archivo `.env`, además de incluirlo en tu `.gitignore` para no subirlo al repositorio.

## Variables Disponibles

Revisa [.env.example](.env.example) para ver todas las variables configurables.
