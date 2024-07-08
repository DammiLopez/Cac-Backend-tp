CREATE TABLE peliculas (
    Id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    genero VARCHAR(100),
    duracion INT,
    imagen VARCHAR(255)
);