# banking-devsu

El proyecto esta confortmado por 2 microservicios alojados en los repositorios que se indican a continuacion
- **Microservicio de persona** - cliente [ms-cliente-persona](https://github.com/deiberv/banking-devsu/tree/main/ms-cliente-persona). Codigo fuente en el directorio [ms-cliente-persona](./ms-cliente-persona/)
- **Microservicio cuentas** - movimientos [ms-cuenta-movimiento](https://github.com/deiberv/banking-devsu/tree/main/ms-cuenta-movimiento). Codigo fuente en el directorio [ms-cuenta-movimiento](./ms-cuenta-movimiento/)

## Base de datos
Se adjunta scrips de base de datos para cada microservicio, se utilizó MySql como manejador de base de datos para ambos MS.
Los scrips los podemos conseguir en el directorio **base-datos**.
- ms-cliente-persona [BaseDatos-ms-persona-cliente.sql](./base-datos/BaseDatos-ms-persona-cliente.sql).
- ms-cuenta-movimiento [BaseDatos-ms-cuenta-movimiento.sql](./base-datos/BaseDatos-ms-cuenta-movimiento.sql).

## Postman
Se adjuntan collecciones de postman utilizada para cada microservicio
- ms-cliente-persona [ms-clientes-devsu.postman_collection](./collection-postman/ms-clientes-devsu.postman_collection.json).
- ms-cuenta-movimiento [ms-cuenta-movimiento.postman_collection](./collection-postman/ms-cuenta-movimiento.postman_collection.json).

## Ejecutar en docker
Se adjunta un archivo docker-compose para ejecutar ambos microservicios en contenedores docker, el archivo se encuentra en el directorio **docker**.
- [compose.yaml](./compose.yaml).

Se cuenta con una orquetacion de contenedores utilizando docker compose que esta ubicado en el directorio *devsu-docker* de este repositorio.

Servicios con lo que se dispone en el docker-compose.yml

- rabbitmq([Ingresar a rabbitMQ](http://localhost:15672/))
- mysql Servidor mysql utilizado para ambos proyectos
- phpmyadmin ([Ingresar a phpmyadmin](http://localhost:9001/)) para gestionar la base de datos
- Eureka Server Se ejecuta por el puerto 8761 [documentacion](http://localhost:8761/)
- ms-cliente-persona Se ejecuta por el puerto 8081 [documentacion](http://localhost:8081/swagger-ui/index.html)
- ms-cuenta-movimiento Se ejecuta por el puerto 8082 [documentacion](http://localhost:8082/swagger-ui/index.html)

Para levantar las ejecucion de los contenedores mediante docker compose, se debe ubicar en el directorio **devsu-docker** de este repositorio y ejecutar el comando
```shell
docker compose up -d
```