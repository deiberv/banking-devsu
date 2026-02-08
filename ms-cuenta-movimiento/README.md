# Documentación del API MS Cuenta Movimientos

## Características
Api rest de cun CRUD de cuentas y movimientos. Se hace uso de Spring Data JPA con manejador de base de datos MySql.

## Configuración
Antes de ejecutar la aplicación, es necesario configurar las siguientes propiedades en el archivo `application.yaml`:

## Puerto de ejecucion
```properties
server.port=8082
```

### Configuracion de base de datos
En el proyecto existe un script que permite la creación del esquema de base de datos llama ([BaseDatos-ms-cuenta-movimiento.sql](./../BaseDatos-ms-cuenta-movimiento.sql)).
Además se debe de configurar en el archivo application.properties las propiedades correspondientes para MySQL:
```properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/cuenta_movimiento_db}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:root}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:admin}
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
```

## Documentacion
El api esta documentado con swagger haciendo uso de springdoc-openapi-starter-webmvc-ui.
Para ingresar a la documentacion del api desarrollada se debe ingresar a [click](http://localhost:8082/swagger-ui/index.html)

La colleccion de Postman la puede obtener [ms-cuenta-movimiento.postman_collection.json](./../collection-postman/ms-cuenta-movimiento.postman_collection.json)

### Configuracion RabbitMQ
Como Message Broker se implemento rabbitMQ, la cola de mensaje que escucha este aplicativo es la llamada **cliente_queue** para configurar se deben de indicar las siguientes variables de entornos definidas en el `application.properties`:
```properties
spring.rabbitmq.host=${RABBITMQ_HOST:localhost}
spring.rabbitmq.port=${RABBITMQ_PORT:5672}
spring.rabbitmq.username=${RABBITMQ_USER:guest}
spring.rabbitmq.password=${RABBITMQ_PASSWORD:guest}
