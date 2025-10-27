
# Proyecto JAVA0078_M6_Agenda

## Descripción
Este proyecto corresponde a un prototipo funcional de una aplicación web para la **gestión de eventos internos** de una empresa.  
El objetivo es proporcionar una herramienta sencilla donde los empleados puedan **registrar y visualizar eventos** como cumpleaños, convivencias, reuniones o fechas clave.

El sistema fue desarrollado utilizando **Spring Boot** bajo el modelo **MVC (Modelo-Vista-Controlador)**, asegurando una correcta separación de capas, inyección de dependencias y uso de anotaciones estándar del framework. 


## Tecnologías Utilizadas
- Java 17
- Spring Boot 3.x
- Spring Web
- Jakarta Validation
- JSP con Bootstrap 5
- Maven
- SLF4J (logging)
- JUnit 5 para pruebas unitarias

## Estructura simplificada del Proyecto
```
JAVA0078_M6_Agenda/
├─src/
│	 ├─ main/
│	 │   ├─ java/
│	 │   │   └─ cl/web/
│	 │   │       ├─ Java0078M6AgendaApplication.java
│	 │   │       ├─ controller/
│	 │   │       │   ├─ AgendaController.java
│	 │   │       │   └─ HomeController.java
│	 │   │       ├─ exception/
│	 │   │       │   └─ GlobalExceptionHandler.java
│	 │   │       ├─ modelo/
│	 │   │       │   └─ Evento.java
│	 │   │       └─ service/
│	 │   │           ├─ AgendaService.java
│	 │   │           └─ AgendaServiceImpl.java
│	 │   ├─ resources/
│	 │   │   └─ application.properties
│	 │   └─ webapp/WEB-INF/views/
│	 │       ├─ form.jsp
│	 │       └─ list.jsp
│	 └─ test/
│	     └─ java/cl/web/
│	         └─ AgendaServiceImplTest.java
├─ despliegue tomcat.png
├─ pom.xml
└─ README.md

```

## Configuración en `application.properties`
```properties
spring.application.name=JAVA0078_M6_Agenda
server.port=8081

spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

logging.level.root=INFO
logging.file.name=logs/agenda.log
```

## Ejecución del Proyecto
### Desde Spring Tool Suite (STS) o terminal:
```bash
mvn clean package
mvn spring-boot:run
```
Luego abrir en el navegador:  
👉 [http://localhost:8081](http://localhost:8081)

## Pruebas Unitarias
Para ejecutar los tests del servicio:
```bash
mvn test
```

## Vistas JSP
El proyecto utiliza **Bootstrap 5** con las vistas:
- `form.jsp`: Formulario de registro de contactos.
- `list.jsp`: Listado de contactos con tabla y botones de acción.

## Anotaciones Destacadas
- `@Controller`: Define la clase como un controlador dentro del patrón MVC.
- `@Service`: Marca la clase de lógica de negocio (capa de servicio).
- `@Autowired`: Permite la inyección automática de dependencias.
- `@Valid`: Activa la validación de datos de entrada.
- `@Slf4j`: Permite el uso de logging.

## Empaquetado WAR
Para generar el archivo .war:
```bash
mvn clean package
```
El archivo generado se ubicará en la carpeta `target/`.

---
## Autores
- Andrea Correa
- Andrés Shranka
- Laura Duhalde
