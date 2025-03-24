
DROP DATABASE deustmedik;
CREATE DATABASE deustmedik;
DROP USER 'deustuser'@'localhost';
CREATE USER 'deustuser'@'localhost' IDENTIFIED BY 'deustpass';
GRANT ALL PRIVILEGES ON deustmedik.* TO 'deustuser'@'localhost';
FLUSH PRIVILEGES;

USE deustmedik;
CREATE TABLE Medico (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    DNI VARCHAR(20),
    Nombre VARCHAR(20),
    Apellido VARCHAR(20),
    Especialidad VARCHAR(100),
    Contacto VARCHAR(100)
);

CREATE TABLE Paciente (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    DNI VARCHAR(20),
    Nombre VARCHAR(20),
    Apellido VARCHAR(20),
    Correo VARCHAR(100),
    Contraseña VARCHAR(100),
    Historia TEXT,
    medico_id BIGINT,
    FOREIGN KEY (medico_id) REFERENCES Medico(id)
);

CREATE TABLE Cita (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	medico_id BIGINT NOT NULL,
    Paciente VARCHAR(100),
    Apellido VARCHAR(100),
    Fecha DATETIME,
	Motivo TEXT,
	FOREIGN KEY (medico_id) REFERENCES Medico(id)    
);