CREATE DATABASE IF NOT EXISTS inventario;
USE inventario;
CREATE TABLE IF NOT EXISTS productos (
    ID_Prod INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200),
    descripcion VARCHAR(200),
    stock INT
);