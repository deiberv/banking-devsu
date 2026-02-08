# Documentación del API MS Cliente

## Características
Api rest de cun CRUD de clientes. Se hace uso de Spring Data JPA con manejador de base de datos MySql.

## Configuración
Antes de ejecutar la aplicación, es necesario configurar las siguientes propiedades en el archivo `application.yaml`:

### Configuracion de base de datos
Se debe de configurar en el archivo application.properties las propiedades correspondientes para MySQL:
```properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/cliente_db}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:root}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:admin}
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
```
Se puede indicar mediante variables de entorno los datos necesarios para conectar a la base de datos, de no indicarse se toman
los datos por defecto

- **SPRING_DATASOURCE_URL:** Url del datasource, por defecto se indica una url de mysql local con el nombre de base de datos cliente_db
- **SPRING_DATASOURCE_USERNAME:** Usuario de de la base de datos
- **SPRING_DATASOURCE_PASSWORD:** Password del usuario con acceso a la base de datos

### Configuracion RabbitMQ
Como Message Broker se implemento rabbitMQ, para configurar se deben de indicar las siguientes variables de entornos definidas en el `application.properties`:
```properties
spring.rabbitmq.host=${RABBITMQ_HOST:localhost}
spring.rabbitmq.port=${RABBITMQ_PORT:5672}
spring.rabbitmq.username=${RABBITMQ_USER:guest}
spring.rabbitmq.password=${RABBITMQ_PASSWORD:guest}

rabbitmq.queue-name=cliente_queue
rabbitmq.exchange-name=cliente_exchange
rabbitmq.routing-key=cliente_routing_key
```
- **RABBITMQ_HOST:** Servidor donde se esta ejecutando rabbitMQ
- **RABBITMQ_PORT:** Pruerto de comunicacion
- **RABBITMQ_USER:** Usuario con acceso al servidor RabbitMQ
- **RABBITMQ_PASSWORD:** Clave de acceso del usuario servidor RabbitMQ
- **rabbitmq.queue-name** -> Indica el nombre de la cola donde se publicaran los mensajes
- **rabbitmq.exchange-name** -> Indica el nombre del exchange donde se publicaran los mensajes
- **rabbitmq.routing-key** -> Identificador de la ruta

## Documentacion
Para ingresar a la documentacion del api desarrollada se debe ingresar a [click](http://localhost:8081/swagger-ui/index.html)

