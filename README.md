# ⚡ API REST - Asociación de Instaladores Eléctricos de Jujuy (AIEJ)

Este proyecto es una **API REST** construida con **Spring Boot**,
diseñada para la gestión de usuarios y futuros socios de la Asociación
de Instaladores Eléctricos de Jujuy (**AIEJ**).\
Incluye **registro de usuarios**, **autenticación segura con JWT** y
**persistencia en MySQL** con migraciones automáticas usando **Flyway**.

------------------------------------------------------------------------

## 🚀 Tecnologías Utilizadas

-   ☕ **Java 21** -- Lenguaje de programación\
-   🌱 **Spring Boot 3.5.5** -- Framework principal\
-   🗄️ **Spring Data JPA** -- Acceso y persistencia de datos\
-   🔐 **Spring Security** -- Autenticación y autorización\
-   🛠️ **Flyway** -- Migraciones de base de datos\
-   ✨ **Lombok** -- Reducción de código repetitivo\
-   🔑 **JJWT** -- Manejo de tokens JWT\
-   🐬 **MySQL** -- Base de datos relacional

------------------------------------------------------------------------

## 📋 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

-   [JDK 21+](https://adoptium.net/)\
-   [MySQL Workbench 8.0+](https://dev.mysql.com/downloads/workbench/)\
-   [IntelliJ IDEA](https://www.jetbrains.com/idea/) (o IDE similar)\
-   [Postman](https://www.postman.com/) (u otra herramienta para probar
    APIs)

------------------------------------------------------------------------

## ⚙️ Configuración del Proyecto

### 1️⃣ Clonar el Repositorio

``` bash
git clone https://github.com/RickiContreras/jujuy-api.git
cd aiej-api
```

### 2️⃣ Configuración de la Base de Datos

-   Verifica que tu servidor MySQL esté en ejecución.\
-   El proyecto usará una base de datos llamada `aiej_db`.\
-   **No necesitas crearla manualmente**, Flyway lo hará
    automáticamente.\
-   Edita `src/main/resources/application.properties` si es necesario:

``` properties
spring.datasource.url=jdbc:mysql://localhost/aiej_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=${MYSQL_DB_CLAVE}
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
```

👉 Crea la variable de entorno `MYSQL_DB_CLAVE` con tu contraseña de
MySQL para mayor seguridad.

### 3️⃣ Ejecutar la Aplicación

-   Abre el proyecto en IntelliJ.\
-   Ejecuta la clase principal:

``` java
JujuyApiApplication.java
```

📌 La primera ejecución creará automáticamente la tabla `usuarios` en la
base de datos.

------------------------------------------------------------------------

## 🏗️ Estructura del Proyecto

    src/main/java/com/aiej/api
    │── controller   # Manejo de solicitudes HTTP y endpoints
    │── service      # Lógica de negocio
    │── repository   # Interfaces JPA
    │── entidades    # Entidades de la base de datos
    │── dto          # Objetos de transferencia de datos (DTOs)
    │── security     # Configuración de seguridad y filtro JWT
    │── config       # Configuración general
    │── util         # Utilidades (gestión de JWT, etc.)

------------------------------------------------------------------------

## 📌 Uso con Postman

### 🔹 Registrar un Nuevo Usuario (público)

**POST** `http://localhost:8080/api/auth/registro`

Body (JSON):

``` json
{
  "nombreUsuario": "ejemplo.usuario",
  "email": "ejemplo@aiej.com",
  "contrasena": "MiContrasena123"
}
```

✅ Respuesta: `201 Created` con los datos del usuario registrado.

------------------------------------------------------------------------

### 🔹 Iniciar Sesión y Obtener JWT

**POST** `http://localhost:8080/api/auth/login`

Body (JSON):

``` json
{
  "email": "ejemplo@aiej.com",
  "contrasena": "MiContrasena123"
}
```

✅ Respuesta: Token JWT que deberás usar en el header
`Authorization: Bearer <token>` en las solicitudes protegidas.

------------------------------------------------------------------------

## 📜 Licencia

Este proyecto se distribuye bajo la licencia **MIT**.\
Puedes usarlo, modificarlo y adaptarlo libremente.

------------------------------------------------------------------------

✨ Desarrollado para la **Asociación de Instaladores Eléctricos de Jujuy
(AIEJ)**.
