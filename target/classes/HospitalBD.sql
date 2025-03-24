
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

CREATE TABLE Horario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    medico_id BIGINT NOT NULL,
    dia DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (medico_id) REFERENCES Medico(id) ON DELETE CASCADE
);

INSERT INTO Medico (DNI, Nombre, Apellido, Especialidad, Contacto) VALUES
('79230672L', 'Naroa', 'Azcona', 'Digestivo', 'naroa.azcona@opendeusto.es'),
('79457623M', 'Gorka', 'Ortuzar', 'Endocrino', 'gorka.ortuzar@opendeusto.es'),
('16784523R', 'Jacqueline', 'Furelos', 'Cabecera', 'jacqueline.furelos@opendeusto.es'),
('30897639H', 'Jorge', 'Martinez', 'Cardiologia', 'jorge.martinez@opendeusto.es'),
('30561342T', 'Ander', 'Perez', 'Hematologia', 'ander.martinez@opendeusto.es'),
('79542098Q', 'Paula', 'Gonzalez', 'Urgencias', 'paula.gonzalez@opendeusto.es'),
('79113006P', 'Ainhoa', 'Saez', 'Traumatologia', 'ainhoa.saez@opendeusto.es');

INSERT INTO Horario (medico_id, dia, hora_inicio, hora_fin) VALUES
(1, '2025-03-25', '09:00', '13:00'),
(2, '2025-03-26', '10:00', '14:00'),
(3, '2025-03-27', '08:00', '12:00'),
(4, '2025-03-28', '11:00', '15:00'),
(5, '2025-03-29', '12:00', '16:00');


