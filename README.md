# DeustMedik

## Descripción del Proyecto

DeustMedik es una aplicación web desarrollada con Spring Boot y Maven, orientada a gestionar médicos, pacientes y citas médicas. Ofrece funcionalidades para la creación, edición y eliminación de registros, así como la gestión completa de citas médicas mediante una base de datos SQL externa.

## Tecnologías utilizadas

* **Java 17**
* **Spring Boot** (Web, Data JPA)
* **Maven** (gestión de dependencias)
* **JUnit & Mockito** (testing)
* **JaCoCo** (cobertura de código)
* **Base de Datos SQL** (MySQL recomendada)
* **Servidor embebido Tomcat**

## Estructura del Proyecto

* `src/main/java`: Código fuente principal del proyecto.
* `src/main/resources`: Archivos de configuración como `application.properties`.
* `src/test/java`:

  * `test/unit`: Pruebas unitarias de la lógica de servicios.
  * `test/integration`: Pruebas de integración para verificar flujos completos.
  * `test/rendimiento`: Pruebas de rendimiento para medir tiempos de respuesta.

## Configuración inicial

Asegúrate de configurar correctamente la conexión con tu base de datos SQL en el archivo:

```
src/main/resources/application.properties
```

Ejemplo de configuración:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/deustmedik
spring.datasource.username=usuario
spring.datasource.password=contraseña
spring.jpa.hibernate.ddl-auto=update
```

## Comandos básicos

* **Compilar el proyecto:**

```shell
mvn compile
```

* **Ejecutar pruebas:**

```shell
# Pruebas unitarias
directorio base del proyecto:
mvn test

# Pruebas de integración
mvn test -P i

# Pruebas de rendimiento
mvn test -P r
# Pruebas de rendimiento de JUnitPerf
mvn test -P perf
```
* **Ejecutar proyecto:**
mvn spring-boot:run
* **Generar versión empaquetada (JAR):**

```shell
mvn package
```

* **Ejecutar la aplicación desde el JAR:**

```shell
java -jar target/DeustMedik-1.0-SNAPSHOT.jar
```

La aplicación iniciará el servidor embebido Tomcat y expondrá los endpoints REST para gestionar pacientes, médicos y citas.

## Documentación

* **Generar documentación Javadoc:**

```shell
mvn javadoc:javadoc
```

La documentación generada estará disponible en:

```
target/site/apidocs/index.html
```

* **Reporte de cobertura JaCoCo:**
  Al ejecutar las pruebas, se generará automáticamente un informe de cobertura de código disponible en:

```
target/site/jacoco/index.html
```

## Limpieza del proyecto

Para limpiar los artefactos generados en compilaciones anteriores, utiliza:

```shell
mvn clean
```

## Soporte y contacto

Para consultas o sugerencias relacionadas con este proyecto, puedes contactar con el equipo desarrollador:

* **Nombre:** \DeustoMedik
* **Repositorio:** \https://github.com/GorkaOrtuzar/DeustMedik

---

© 2025 DeustMedik - Todos los derechos reservados.
