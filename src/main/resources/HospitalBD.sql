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
    dia varchar(30) NOT NULL,
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
('79113006P', 'Ainhoa', 'Saez', 'Traumatologia', 'ainhoa.saez@opendeusto.es', TRUE),
('50123456K', 'Leire', 'Zabala', 'Dermatología', 'leire.zabala@opendeusto.es', TRUE),
('60123456L', 'Mikel', 'Iglesias', 'Neurología', 'mikel.iglesias@opendeusto.es', TRUE),
('70123456M', 'Irati', 'Goikoetxea', 'Pediatría', 'irati.goikoetxea@opendeusto.es', TRUE),
('80123456N', 'Jon', 'Etxebarria', 'Psiquiatría', 'jon.etxebarria@opendeusto.es', TRUE),
('90123456O', 'Ane', 'López', 'Ginecología', 'ane.lopez@opendeusto.es', TRUE),
('31122334Z', 'Iñigo', 'Torres', 'Otorrinolaringología', 'inigo.torres@opendeusto.es', TRUE),
('41122444X', 'Nerea', 'Martinez', 'Reumatología', 'nerea.martinez@opendeusto.es', TRUE),
('51122555V', 'Xabier', 'Jiménez', 'Nefrología', 'xabier.jimenez@opendeusto.es', TRUE),
('61122666U', 'Garazi', 'Alvarez', 'Oncología', 'garazi.alvarez@opendeusto.es', TRUE),
('71122777S', 'Asier', 'Moreno', 'Alergología', 'asier.moreno@opendeusto.es', TRUE),
('81122888R', 'Amaia', 'Diez', 'Medicina Interna', 'amaia.diez@opendeusto.es', TRUE),
('91122999Q', 'Oier', 'Castro', 'Neumología', 'oier.castro@opendeusto.es', TRUE),
('01123000P', 'June', 'Urrutia', 'Urología', 'june.urrutia@opendeusto.es', TRUE);


INSERT INTO Horario (medico_id, dia, hora_inicio, hora_fin) VALUES
(1, 'LUNES', '09:00', '13:00'),
(1, 'MARTES', '09:00', '13:00'),
(1, 'JUEVES', '09:00', '13:00'),
(1, 'LUNES', '09:00', '13:00'),
(2, 'MARTES', '10:00', '14:00'),
(2, 'JUEVES', '10:00', '14:00'),
(3, 'LUNES', '08:00', '12:00'),
(3, 'MIERCOLES', '08:00', '12:00'),
(3, 'VIERNES', '08:00', '12:00'),
(4, 'JUEVES', '11:00', '15:00'),
(5, 'VIERNES', '12:00', '16:00');


INSERT INTO Paciente (DNI, Nombre, Apellido, Correo, Contraseña, Historial, medico_id) VALUES
('12345678A', 'Carlos', 'Lopez', 'carlos.lopez@pacientes.com', 'pass1234', 'Paciente con antecedentes de hipertensión.', 1),
('98765432B', 'Lucia', 'Garcia', 'lucia.garcia@pacientes.com', 'lucia2023', 'Paciente con alergias al polen.', 2),
('45678901C', 'David', 'Martinez', 'david.martinez@pacientes.com', 'davidpass', 'Paciente con historial de migrañas.', 3),
('78901234D', 'Ana', 'Fernandez', 'ana.fernandez@pacientes.com', 'ana123', 'Paciente con historial de asma.', 4),
('89012345E', 'Elena', 'Sanchez', 'elena.sanchez@pacientes.com', 'elena456', 'Paciente con historial de diabetes.', 5),
('11223344F', 'Sergio', 'Ruiz', 'sergio.ruiz@pacientes.com', 'sergio2024', 'Paciente con problemas de piel desde hace 3 años.', 8),
('22334455G', 'Maria', 'Ibáñez', 'maria.ibanez@pacientes.com', 'mariapass', 'Paciente con antecedentes de epilepsia.', 9),
('33445566H', 'Unai', 'Garate', 'unai.garate@pacientes.com', 'unai321', 'Niño con revisiones pediátricas frecuentes.', 10),
('44556677I', 'Nerea', 'Santos', 'nerea.santos@pacientes.com', 'nerea456', 'Paciente en tratamiento psicológico desde 2022.', 11),
('55667788J', 'Iker', 'Mendia', 'iker.mendia@pacientes.com', 'ikerpass', 'Paciente en seguimiento ginecológico anual.', 12),
('66778899K', 'Aitor', 'Navarro', 'aitor.navarro@pacientes.com', 'aitorpass', 'Paciente con problemas auditivos frecuentes.', 13),
('77889900L', 'Laia', 'Echeverria', 'laia.echeverria@pacientes.com', 'laia2023', 'Paciente con artritis reumatoide.', 14),
('88990011M', 'Markel', 'Agirre', 'markel.agirre@pacientes.com', 'markel123', 'Paciente con problemas renales crónicos.', 15),
('99001122N', 'Itziar', 'Lasa', 'itziar.lasa@pacientes.com', 'itziarpass', 'Paciente con cáncer en fase de remisión.', 16),
('00112233O', 'Gonzalo', 'Serrano', 'gonzalo.serrano@pacientes.com', 'gonzalo2023', 'Paciente con alergias alimentarias graves.', 17),
('11335577P', 'Maitane', 'Rey', 'maitane.rey@pacientes.com', 'maitane456', 'Paciente con diagnóstico de lupus.', 18),
('22446688Q', 'Adrian', 'Pascual', 'adrian.pascual@pacientes.com', 'adrian321', 'Paciente con enfisema pulmonar.', 19),
('33557799R', 'Irune', 'Delgado', 'irune.delgado@pacientes.com', 'irune123', 'Paciente en tratamiento postoperatorio de riñón.', 15),
('44668800S', 'Lander', 'Sanz', 'lander.sanz@pacientes.com', 'landerpass', 'Paciente con control prostático anual.', 20),
('55779911T', 'Claudia', 'Redondo', 'claudia.redondo@pacientes.com', 'claudia789', 'Paciente con revisiones ginecológicas periódicas.', 12);

