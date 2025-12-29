# Guía de Contribución y Flujo de Trabajo

Dentro de este documento se definen los estándares de colaboración para el repositorio. El objetivo de este es mantener un historial limpio, facilitar la integración continua (CI/CD) y asegurar la calidad del código en nuestro Monorepo (Backend, Data, Infra).

## 1. Estrategia de Branching (GitHub Flow)

Utilizamos **GitHub Flow**. Esto significa que la rama `main` siempre debe ser desplegable y estable.



**Reglas Generales:**
* 🔒 **Rama `main` Protegida:** No se permiten commits directos. Todo cambio debe llegar mediante un Pull Request (PR).
* 🌿 **Ramas Efímeras:** Las ramas de trabajo deben tener una vida corta. Se crean, se trabajan y se fusionan.

## 2. Convención de Nombres de Ramas

Todas las ramas deben crearse a partir de `main` y seguir estrictamente el formato:

`categoria/descripcion-corta`

### Reglas de Formato
* Todo en **minúsculas**.
* Usar **guiones (`-`)** para separar palabras (kebab-case).
* Sin caracteres especiales ni espacios.

### Prefijos Permitidos (Categorías)

| Prefijo | Uso Exclusivo | Ejemplo |
| :--- | :--- | :--- |
| `feat/` | Nuevas funcionalidades para Backend o Frontend. | `feat/login-endpoint`, `feat/dashboard-ui` |
| `fix/` | Corrección de errores o bugs. | `fix/cors-policy`, `fix/null-pointer-id` |
| `data/` | **Ciencia de Datos:** Notebooks, ETLs, modelos. | `data/exploratory-analysis`, `data/train-model-v1` |
| `infra/` | **Infraestructura:** OCI, Docker, Terraform, CI/CD. | `infra/oci-bucket-setup`, `infra/docker-compose` |
| `docs/` | Cambios exclusivos en documentación. | `docs/api-contract`, `docs/update-readme` |
| `refactor/` | Mejoras de código que no alteran la funcionalidad. | `refactor/optimize-imports` |

---

## 3. Convención de Commits (Conventional Commits)

Nuestros mensajes de commit deben ser semánticos para facilitar la lectura del historial.

**Estructura:**
`tipo(alcance): descripción breve`

* **tipo:** Coincide con el prefijo de la rama (`feat`, `fix`, `data`, `infra`, `docs`, `refactor`).
* **(alcance):** Opcional. Indica qué parte del sistema se tocó (`back`, `front`, `data`, `shared`).
* **descripción:** Imperativo, claro y conciso.

### Ejemplos Válidos
* `feat(back): add customer churn controller`
* `data(model): update random forest hyperparameters`
* `infra(oci): add terraform script for object storage`
* `fix(front): resolve mobile responsiveness on login`
* `docs: update contributing guidelines`

---

## 4. Flujo de Pull Requests (PR)

1.  **Crear PR:** Al terminar tu tarea, abre un PR apuntando a `main`.
2.  **Revisión:** Se requiere **al menos 1 aprobación** de otro integrante del equipo (Cross-review: Back revisa Data, Front revisa Back, etc.).
3.  **Merge:** Una vez aprobado y con los checks pasados, se realiza el merge.

> **Nota:** Si una rama o commit no cumple con estas convenciones, el PR no será aprobado hasta que se corrija.