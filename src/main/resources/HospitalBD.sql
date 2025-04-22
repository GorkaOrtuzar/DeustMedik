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
    DNI VARCHAR(20),
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
('79230672L', 'Naroa', 'Azcona', 'Traumatologia', 'naroa.azcona@opendeusto.es', TRUE),
('79457623M', 'Gorka', 'Ortuzar', 'Traumatologia', 'gorka.ortuzar@opendeusto.es', TRUE),
('16784523R', 'Jacqueline', 'Furelos', 'Oftalmologia', 'jacqueline.furelos@opendeusto.es', TRUE),
('30897639H', 'Jorge', 'Martinez', 'Cardiologia', 'jorge.martinez@opendeusto.es', TRUE),
('30561342T', 'Ander', 'Perez', 'Oftalmologia', 'ander.martinez@opendeusto.es', TRUE),
('79542098Q', 'Paula', 'Gonzalez', 'Oftalmologia', 'paula.gonzalez@opendeusto.es', TRUE),
('79113006P', 'Ainhoa', 'Saez', 'Traumatologia', 'ainhoa.saez@opendeusto.es', TRUE),
('50123456K', 'Leire', 'Zabala', 'Dermatologia', 'leire.zabala@opendeusto.es', TRUE),
('60123456L', 'Mikel', 'Iglesias', 'Neurologia', 'mikel.iglesias@opendeusto.es', TRUE),
('70123456M', 'Irati', 'Goikoetxea', 'Pediatria', 'irati.goikoetxea@opendeusto.es', TRUE),
('80123456N', 'Jon', 'Etxebarria', 'Cardiologia', 'jon.etxebarria@opendeusto.es', TRUE),
('90123456O', 'Ane', 'López', 'Traumatologia', 'ane.lopez@opendeusto.es', TRUE),
('31122334Z', 'Iñigo', 'Torres', 'Pediatria', 'inigo.torres@opendeusto.es', TRUE),
('41122444X', 'Nerea', 'Martinez', 'Pediatria', 'nerea.martinez@opendeusto.es', TRUE),
('51122555V', 'Xabier', 'Jiménez', 'Cardiologia', 'xabier.jimenez@opendeusto.es', TRUE),
('61122666U', 'Garazi', 'Alvarez', 'Traumatologia', 'garazi.alvarez@opendeusto.es', TRUE),
('71122777S', 'Asier', 'Moreno', 'Cardiologia', 'asier.moreno@opendeusto.es', TRUE),
('81122888R', 'Amaia', 'Diez', 'Neurologia', 'amaia.diez@opendeusto.es', TRUE),
('91122999Q', 'Oier', 'Castro', 'Dermatologia', 'oier.castro@opendeusto.es', TRUE),
('01123000P', 'June', 'Urrutia', 'Dermatologia', 'june.urrutia@opendeusto.es', TRUE);


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
(5, 'VIERNES', '12:00', '16:00'),
(6, 'LUNES', '15:00', '18:00'),
(6, 'MIERCOLES', '15:00', '18:00'),
(7, 'MARTES', '08:00', '12:00'),
(7, 'VIERNES', '08:00', '12:00'),
(8, 'LUNES', '10:00', '14:00'),
(8, 'JUEVES', '10:00', '14:00'),
(9, 'MIERCOLES', '13:00', '17:00'),
(9, 'VIERNES', '13:00', '17:00'),
(10, 'LUNES', '08:30', '12:30'),
(10, 'MARTES', '08:30', '12:30'),
(11, 'JUEVES', '09:00', '13:00'),
(11, 'VIERNES', '09:00', '13:00'),
(12, 'MIERCOLES', '10:00', '14:00'),
(12, 'JUEVES', '10:00', '14:00'),
(13, 'LUNES', '14:00', '18:00'),
(13, 'VIERNES', '14:00', '18:00'),
(14, 'MARTES', '09:00', '13:00'),
(14, 'JUEVES', '09:00', '13:00'),
(15, 'MIERCOLES', '08:00', '12:00'),
(15, 'VIERNES', '08:00', '12:00'),
(16, 'LUNES', '15:00', '19:00'),
(16, 'JUEVES', '15:00', '19:00'),
(17, 'MARTES', '08:00', '12:00'),
(17, 'VIERNES', '08:00', '12:00'),
(18, 'MIERCOLES', '09:30', '13:30'),
(18, 'JUEVES', '09:30', '13:30'),
(19, 'LUNES', '10:00', '14:00'),
(19, 'MIERCOLES', '10:00', '14:00'),
(20, 'MARTES', '13:00', '17:00'),
(20, 'JUEVES', '13:00', '17:00');


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


INSERT INTO Cita (medico_id, Paciente, Apellido, Fecha, Motivo, DNI) VALUES
(1, 'Carlos', 'Lopez', '2024-05-16', 'Chequeo hipertensión', '12345678A'),
(1, 'Carlos', 'Lopez', '2024-04-03', 'Revisión general', '12345678A'),
(2, 'Lucia', 'Garcia', '2024-06-18', 'Control alergias', '98765432B'),
(2, 'Lucia', 'Garcia', '2024-04-01', 'Vacuna primavera', '98765432B'),
(3, 'David', 'Martinez', '2024-05-11', 'Consulta por migraña', '45678901C'),
(3, 'David', 'Martinez', '2024-03-11', 'Seguimiento tratamiento', '45678901C'),
(4, 'Ana', 'Fernandez', '2024-04-15', 'Control de asma', '78901234D'),
(4, 'Ana', 'Fernandez', '2024-07-06', 'Consulta por bronquitis', '78901234D'),
(5, 'Elena', 'Sanchez', '2024-06-01', 'Control de diabetes', '89012345E'),
(5, 'Elena', 'Sanchez', '2024-03-01', 'Análisis glucosa', '89012345E'),
(8, 'Sergio', 'Ruiz', '2024-05-17', 'Dermatitis crónica', '11223344F'),
(8, 'Sergio', 'Ruiz', '2024-02-25', 'Revisión de manchas', '11223344F'),
(9, 'Maria', 'Ibáñez', '2024-05-25', 'Revisión neurológica', '22334455G'),
(9, 'Maria', 'Ibáñez', '2024-03-11', 'Consulta por epilepsia', '22334455G'),
(10, 'Unai', 'Garate', '2024-06-10', 'Revisión pediátrica', '33445566H'),
(10, 'Unai', 'Garate', '2024-03-20', 'Vacunación anual', '33445566H'),
(11, 'Nerea', 'Santos', '2024-06-12', 'Terapia psicológica', '44556677I'),
(11, 'Nerea', 'Santos', '2024-04-22', 'Seguimiento emocional', '44556677I'),
(12, 'Iker', 'Mendia', '2024-05-29', 'Control ginecológico', '55667788J'),
(12, 'Iker', 'Mendia', '2024-03-15', 'Ecografía rutinaria', '55667788J'),
(13, 'Aitor', 'Navarro', '2024-04-10', 'Revisión auditiva', '66778899K'),
(13, 'Aitor', 'Navarro', '2024-07-12', 'Molestias en oído derecho', '66778899K'),
(14, 'Laia', 'Echeverria', '2024-05-02', 'Control de artritis', '77889900L'),
(14, 'Laia', 'Echeverria', '2024-06-20', 'Dolor en articulaciones', '77889900L'),
(15, 'Markel', 'Agirre', '2024-03-21', 'Revisión nefrológica', '88990011M'),
(15, 'Markel', 'Agirre', '2024-05-18', 'Análisis de riñón', '88990011M'),
(16, 'Itziar', 'Lasa', '2024-04-28', 'Consulta de oncología', '99001122N'),
(16, 'Itziar', 'Lasa', '2024-06-14', 'Revisión post-quimioterapia', '99001122N'),
(17, 'Gonzalo', 'Serrano', '2024-03-07', 'Revisión alergias alimentarias', '00112233O'),
(17, 'Gonzalo', 'Serrano', '2024-05-25', 'Reacción a marisco', '00112233O'),
(18, 'Maitane', 'Rey', '2024-02-29', 'Consulta sobre lupus', '11335577P'),
(18, 'Maitane', 'Rey', '2024-06-03', 'Análisis inmunológicos', '11335577P'),
(19, 'Adrian', 'Pascual', '2024-04-19', 'Control de enfisema', '22446688Q'),
(19, 'Adrian', 'Pascual', '2024-06-30', 'Revisión pulmonar', '22446688Q'),
(15, 'Irune', 'Delgado', '2024-05-11', 'Postoperatorio de riñón', '33557799R'),
(15, 'Irune', 'Delgado', '2024-07-05', 'Ecografía de seguimiento', '33557799R'),
(20, 'Lander', 'Sanz', '2024-06-15', 'Chequeo prostático anual', '44668800S'),
(20, 'Lander', 'Sanz', '2024-03-12', 'PSA y tacto rectal', '44668800S'),
(12, 'Claudia', 'Redondo', '2024-04-05', 'Revisión ginecológica', '55779911T'),
(12, 'Claudia', 'Redondo', '2024-06-10', 'Ecografía transvaginal', '55779911T');
