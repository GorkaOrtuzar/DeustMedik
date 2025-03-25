
DROP DATABASE deustmedik;
CREATE DATABASE deustmedik;
DROP USER 'deustuser'@'localhost';
CREATE USER 'deustuser'@'localhost' IDENTIFIED BY 'deustpass';
GRANT ALL PRIVILEGES ON deustmedik.* TO 'deustuser'@'localhost';
FLUSH PRIVILEGES;

USE deustmedik;

DROP TABLE IF EXISTS Medico;

CREATE TABLE Medico (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    DNI VARCHAR(20),
    Nombre VARCHAR(20),
    Apellido VARCHAR(20),
    Especialidad VARCHAR(100),
    Contacto VARCHAR(100),
    Disponibilidad BOOLEAN DEFAULT TRUE
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
	FOREIGN KEY (medico_id) REFERENCES Medico(id) ON DELETE CASCADE   
);

CREATE TABLE Horario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    medico_id BIGINT NOT NULL,
    dia DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (medico_id) REFERENCES Medico(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS Paciente;

CREATE TABLE Paciente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    DNI VARCHAR(20) NOT NULL,
    Nombre VARCHAR(20) NOT NULL,
    Apellido VARCHAR(20) NOT NULL,
    Correo VARCHAR(100) NOT NULL,
    Contraseña VARCHAR(100) NOT NULL,
    Historial TEXT,
    medico_id BIGINT,
    FOREIGN KEY (medico_id) REFERENCES Medico(id) ON DELETE SET NULL
);

INSERT INTO Medico (DNI, Nombre, Apellido, Especialidad, Contacto, Disponibilidad) VALUES
('79230672L', 'Naroa', 'Azcona', 'Digestivo', 'naroa.azcona@opendeusto.es', TRUE),
('79457623M', 'Gorka', 'Ortuzar', 'Endocrino', 'gorka.ortuzar@opendeusto.es', TRUE),
('16784523R', 'Jacqueline', 'Furelos', 'Cabecera', 'jacqueline.furelos@opendeusto.es', TRUE),
('30897639H', 'Jorge', 'Martinez', 'Cardiologia', 'jorge.martinez@opendeusto.es', TRUE),
('30561342T', 'Ander', 'Perez', 'Hematologia', 'ander.martinez@opendeusto.es', TRUE),
('79542098Q', 'Paula', 'Gonzalez', 'Urgencias', 'paula.gonzalez@opendeusto.es', TRUE),
('79113006P', 'Ainhoa', 'Saez', 'Traumatologia', 'ainhoa.saez@opendeusto.es', TRUE);


INSERT INTO Horario (medico_id, dia, hora_inicio, hora_fin) VALUES
(1, '2025-03-25', '09:00', '13:00'),
(2, '2025-03-26', '10:00', '14:00'),
(3, '2025-03-27', '08:00', '12:00'),
(4, '2025-03-28', '11:00', '15:00'),
(5, '2025-03-29', '12:00', '16:00');

INSERT INTO Paciente (DNI, Nombre, Apellido, Correo, Contraseña, Historial, medico_id) VALUES
('12345678A', 'Carlos', 'Lopez', 'carlos.lopez@pacientes.com', 'pass1234', 'Paciente con antecedentes de hipertensión.', 1),
('98765432B', 'Lucia', 'Garcia', 'lucia.garcia@pacientes.com', 'lucia2023', 'Paciente con alergias al polen.', 2),
('45678901C', 'David', 'Martinez', 'david.martinez@pacientes.com', 'davidpass', 'Paciente con historial de migrañas.', 3),
('78901234D', 'Ana', 'Fernandez', 'ana.fernandez@pacientes.com', 'ana123', 'Paciente con historial de asma.', 4),
('89012345E', 'Elena', 'Sanchez', 'elena.sanchez@pacientes.com', 'elena456', 'Paciente con historial de diabetes.', 5);



