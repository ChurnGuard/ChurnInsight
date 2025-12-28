# ChurnInsight 42

ChurnInsight es una API REST desarrollada en Java con Spring Boot cuyo objetivo es predecir si un cliente tiene alta probabilidad de cancelar un servicio (churn).

La solución está pensada para empresas con modelos de suscripción o contratos recurrentes (fintech, telecomunicaciones, streaming, e-commerce) que desean anticiparse a la cancelación de clientes y tomar acciones preventivas.


## Instalación

```bash
git clone https://github.com/ChurnGuard/ChurnInsight.git
```


## Endpoints
* POST /predict
Recibe los datos de un cliente y devuelve la predicción de churn (cancelación del servicio) junto con su probabilidad.


## Body y Respuesta
Ejemplo de request
```json
{
  "tiempo_contrato_meses": 12,
  "retrasos_pago": 2,
  "uso_mensual": 14.5,
  "plan": "Premium"
}
```
Ejemplo de response
```json
{
  "prevision": "Va a cancelar",
  "probabilidad": 0.81
}
```

## Technologies

* [Spring](https://spring.io/) - Framework Used.
* [Hibernate](https://hibernate.org/) - ORM.
* [React](https://reactjs.org/) - UI Library.


## Documentación

La documentación interactiva está disponible vía Swagger (OpenAPI) una vez levantada la aplicación.

Desde allí se pueden:
* Ver los endpoints
* Probar requests
* Consultar modelos de datos


## Participantes

* [Samuel Granados](https://github.com/ggsgranados)
* [Johan Leal](https://github.com/JsLealM)
* [Javier Garcia](https://github.com/popex404)
* [Barbara Ortiz](https://github.com/BarbaraAngelesOrtiz)
* [Yoshua Pariona](https://github.com/YoshuaPariona)
* [Arturo Trelles](https://github.com/ArturoTrelles91)
* [Jhony Rodriguez](https://github.com/jhonyaldo)
* [Juan Rendon](https://github.com/Phylip28)
* [Cristian Pinzon](https://github.com/Crispis723)
* [Damian Lambrecht](https://github.com/DamianL96)
