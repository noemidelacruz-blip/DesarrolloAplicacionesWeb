-- =====================================================================
--  Laboratorio 07 - Sistema de Atención Veterinaria
--  Script de creación de bases de datos, tablas y datos de prueba (MySQL 8+)
--  Estudiante: De La Cruz Zavala, Noemi  |  Sección D
--
--  Cada microservicio tiene SU PROPIA base de datos.
--  No existen llaves foráneas entre bases: id_propietario (bd_mascota) e
--  id_mascota (bd_consulta) son referencias lógicas validadas por HTTP.
--
--  Uso:  mysql -u root -p < script-bd.sql
-- =====================================================================

-- ---------------------------------------------------------------------
-- 1) bd_propietario  ->  servicio-propietario (puerto 8081)
-- ---------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS bd_propietario
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_propietario;

DROP TABLE IF EXISTS propietarios;
CREATE TABLE propietarios (
    id_propietario BIGINT       NOT NULL AUTO_INCREMENT,
    nombres        VARCHAR(80)  NOT NULL,
    apellidos      VARCHAR(80)  NOT NULL,
    dni            VARCHAR(8)   NOT NULL,
    telefono       VARCHAR(15)  NULL,
    email          VARCHAR(100) NULL,
    PRIMARY KEY (id_propietario),
    UNIQUE KEY uk_propietarios_dni (dni)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO propietarios (nombres, apellidos, dni, telefono, email) VALUES
    ('Carlos',  'Ramírez Torres',  '45678912', '987654321', 'carlos.ramirez@correo.com'),
    ('María',   'Fernández Soto',  '70123456', '956123478', 'maria.fernandez@correo.com'),
    ('Luis',    'Huamán Quispe',   '41236587', '912345678', 'luis.huaman@correo.com');

-- ---------------------------------------------------------------------
-- 2) bd_mascota  ->  servicio-mascota (puerto 8082)
-- ---------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS bd_mascota
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_mascota;

DROP TABLE IF EXISTS mascotas;
CREATE TABLE mascotas (
    id_mascota       BIGINT      NOT NULL AUTO_INCREMENT,
    nombre           VARCHAR(60) NOT NULL,
    especie          VARCHAR(40) NOT NULL,
    fecha_nacimiento DATE        NOT NULL,
    id_propietario   BIGINT      NOT NULL,   -- referencia lógica a bd_propietario.propietarios
    PRIMARY KEY (id_mascota),
    KEY idx_mascotas_propietario (id_propietario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO mascotas (nombre, especie, fecha_nacimiento, id_propietario) VALUES
    ('Firulais', 'Perro', '2020-03-15', 1),
    ('Michi',    'Gato',  '2021-07-20', 2),
    ('Rocky',    'Perro', '2019-11-02', 3),
    ('Luna',     'Gato',  '2022-01-10', 2);

-- ---------------------------------------------------------------------
-- 3) bd_consulta  ->  servicio-consulta (puerto 8083)
-- ---------------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS bd_consulta
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_consulta;

DROP TABLE IF EXISTS consultas;
CREATE TABLE consultas (
    id_consulta BIGINT        NOT NULL AUTO_INCREMENT,
    id_mascota  BIGINT        NOT NULL,   -- referencia lógica a bd_mascota.mascotas
    fecha       DATE          NOT NULL,
    motivo      VARCHAR(200)  NOT NULL,
    diagnostico VARCHAR(500)  NOT NULL,
    costo       DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_consulta),
    KEY idx_consultas_mascota (id_mascota),
    CONSTRAINT chk_consultas_costo CHECK (costo >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO consultas (id_mascota, fecha, motivo, diagnostico, costo) VALUES
    (1, '2025-03-10', 'Vacunación anual',          'Mascota sana. Se aplicó vacuna antirrábica.',       60.00),
    (2, '2025-04-02', 'Vómitos y falta de apetito', 'Gastritis leve. Dieta blanda y medicación por 5 días.', 85.50),
    (3, '2025-04-18', 'Cojera en pata trasera',     'Esguince leve. Reposo y antiinflamatorio.',        120.00),
    (2, '2025-05-06', 'Control post tratamiento',   'Recuperación completa. Alta médica.',               40.00);

-- Verificación rápida
SELECT 'bd_propietario' AS base, COUNT(*) AS registros FROM bd_propietario.propietarios
UNION ALL SELECT 'bd_mascota',   COUNT(*) FROM bd_mascota.mascotas
UNION ALL SELECT 'bd_consulta',  COUNT(*) FROM bd_consulta.consultas;
