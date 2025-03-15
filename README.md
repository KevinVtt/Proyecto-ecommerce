Proyecto E-commerce con Spring Boot

Descripción

Este es un proyecto personal de un e-commerce desarrollado con Spring Boot 3, que implementa diversas tecnologías y conceptos clave en el desarrollo backend y bases de datos. La aplicación permite la gestión de productos y usuarios, asegurando una estructura escalable y mantenible.

Tecnologías utilizadas

Spring Boot 3: Framework principal para la construcción de la aplicación.

Hibernate: Implementación de JPA para la persistencia de datos.

Spring Data JPA: Simplifica la gestión de operaciones con bases de datos.

AOP (Programación Orientada a Aspectos): Separación de lógica transversal (logging, seguridad, transacciones, etc.).

Lombok: Reducción de código boilerplate en las entidades y servicios.

API RESTful: Exposición de endpoints para la interacción con el frontend o clientes externos.

POO (Programación Orientada a Objetos): Estructura modular y reutilizable.

MySQL: Base de datos relacional para almacenar información de usuarios y productos.

Funcionalidad del Proyecto

El sistema permite:

Gestión de Productos: Creación, actualización, eliminación y consulta de productos.

Gestión de Usuarios: Registro y manejo de usuarios.

Asignación Automática de Carritos: Cada usuario nuevo recibe automáticamente un carrito para realizar compras.

API RESTful: Endpoints para interactuar con el sistema desde el frontend o clientes externos.

Estado del Proyecto

Actualmente, el proyecto está en desarrollo. Planeo realizar mejoras constantes, incluyendo:

Optimizar las consultas a la base de datos.

Agregar autenticación y autorización con Spring Security.

Integrar una interfaz en React para mejorar la experiencia de usuario.

Añadir métodos de pago y funcionalidades avanzadas.

Instalación y Configuración

1. Clonar el repositorio

git clone https://github.com/KevinVtt/Proyecto-ecommerce.git
cd Proyecto-ecommerce

2. Configurar la base de datos

Crea una base de datos en MySQL con el nombre ecommerce_db.

Configura las credenciales en application.properties o application.yml.

Ejemplo de application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update

3. Ejecutar la aplicación

mvn spring-boot:run

O si usas Gradle:

gradle bootRun

Contribuciones

Si deseas contribuir con mejoras o nuevas funcionalidades, ¡eres bienvenido! Puedes hacer un fork del repositorio, realizar tus cambios y crear un pull request.

Contacto

Si tienes dudas o sugerencias, puedes encontrarme en GitHub como @KevinVtt.

🚀 Este proyecto seguirá creciendo, cualquier sugerencia es bienvenida. Pronto se integrará una interfaz en React para mejorar la experiencia de usuario.