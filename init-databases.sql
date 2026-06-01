-- ============================================================
-- Script de inicialización de bases de datos PostgreSQL
-- Sistema de Gestión - Centro de Formación
-- ============================================================

-- Crear las 3 bases de datos (ejecutar como superusuario postgres)
CREATE DATABASE db_instructor;
CREATE DATABASE db_alumno;
CREATE DATABASE db_taller;

-- Las tablas se crean automáticamente con spring.jpa.hibernate.ddl-auto: update
-- al iniciar cada microservicio.

-- ============================================================
-- Verificación (opcional): conectarse a cada DB y listar tablas
-- ============================================================
-- \c db_instructor
-- \dt
--
-- \c db_alumno
-- \dt
--
-- \c db_taller
-- \dt
