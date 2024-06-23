-- db/migration/V1__crear_tabla_prueba.sql

-- Crear una tabla de prueba
CREATE TABLE prueba (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Insertar datos de prueba
INSERT INTO prueba (nombre) VALUES ('Prueba 1'), ('Prueba 2'), ('Prueba 3');
