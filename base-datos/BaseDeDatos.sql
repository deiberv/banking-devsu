-- Tablas relacionadas el ms persona cliente
CREATE DATABASE `cliente_db`;
USE `cliente_db`;

CREATE TABLE `clientes` (
    `cliente_id` bigint NOT NULL,
    `edad` bigint DEFAULT NULL,
    `direccion` varchar(255) DEFAULT NULL,
    `identificacion` varchar(255) DEFAULT NULL,
    `nombre` varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    `telefono` varchar(255) DEFAULT NULL,
    `estado` enum('TRUE','FALSE') DEFAULT NULL,
    `genero` enum('MASCULINO','FEMENINO','OTRO') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `clientes`
    ADD PRIMARY KEY (`cliente_id`);

ALTER TABLE `clientes`
    MODIFY `cliente_id` bigint NOT NULL AUTO_INCREMENT;

-- Tablas relacionadas al servicio de cuenta movimiento
CREATE DATABASE `cuenta_movimiento_db`;
USE `cuenta_movimiento_db`;

CREATE TABLE `clientes` (
                            `cliente_id` bigint NOT NULL,
                            `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB;

CREATE TABLE `cuentas` (
                           `saldo` decimal(38,2) DEFAULT NULL,
                           `cliente_id` bigint NOT NULL,
                           `cuenta_id` bigint NOT NULL,
                           `numero_cuenta` varchar(10) NOT NULL,
                           `estado` enum('True','False') DEFAULT NULL,
                           `tipo_cuenta` enum('CORRIENTE','AHORRO') NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `movimientos` (
                               `movimiento_id` bigint NOT NULL,
                               `fecha` datetime(6) DEFAULT NULL,
                               `cuenta_id` bigint DEFAULT NULL,
                               `saldo_inicial` decimal(38,2) DEFAULT NULL,
                               `valor` decimal(38,2) DEFAULT NULL,
                               `saldo` decimal(38,2) DEFAULT NULL
) ENGINE=InnoDB;


--
ALTER TABLE `clientes`
    ADD PRIMARY KEY (`cliente_id`);

ALTER TABLE `cuentas`
    ADD PRIMARY KEY (`cuenta_id`),
ADD UNIQUE KEY `UK_NROCUENTA` (`numero_cuenta`),
ADD KEY `FK_CTA_CLIENTE_ID` (`cliente_id`);

ALTER TABLE `movimientos`
    ADD PRIMARY KEY (`movimiento_id`),
ADD KEY `FK_MOV_CUENTA_ID` (`cuenta_id`);

ALTER TABLE `cuentas`
    MODIFY `cuenta_id` bigint NOT NULL AUTO_INCREMENT;

ALTER TABLE `movimientos`
    MODIFY `movimiento_id` bigint NOT NULL AUTO_INCREMENT;

ALTER TABLE `cuentas`
    ADD CONSTRAINT `FK_CTA_CLIENTE_ID` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`cliente_id`);

ALTER TABLE `movimientos`
    ADD CONSTRAINT `FK_MOV_CUENTA_ID` FOREIGN KEY (`cuenta_id`) REFERENCES `cuentas` (`cuenta_id`);