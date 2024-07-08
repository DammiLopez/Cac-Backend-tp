CREATE TABLE usuarios (
    Id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    contrasena VARCHAR(100),
    fechaNacimiento DATE,
    pais VARCHAR(255)
);
