# 🏠 Inmobiliaria Cabrejo

Sistema web inmobiliario desarrollado con arquitectura full stack para la gestión de propiedades y usuarios, incluyendo autenticación con JWT y refresh tokens. Permite realizar operaciones completas de: Crear propiedades, Editar propiedades, Eliminar propiedades, Listar propiedades, Filtrar resultados, Paginación, Visualización detallada con galería de imágenes, Registro y login de usuarios, Autenticación segura con JWT, Refresh tokens para mantener sesión activa, Logout y revocación de tokens.

El proyecto está organizado en dos módulos principales dentro de una sola carpeta raíz:

El proyecto está organizado en dos módulos principales dentro de una sola carpeta raíz:

```java
InmobiliariaCabrejo
├── property-service (Backend - Spring Boot + JWT + Refresh Tokens)
└── property-frontend (Frontend - React + Vite)
```

Estructura del backend (resumen):

```java
common
├── exception
│   ├── ApiErrorResponse.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
└── security
    ├── AuthResponse.java
    ├── CustomAccessDeniedHandler.java
    ├── CustomAuthenticationEntryPoint.java
    ├── JwtAuthenticationFilter.java
    ├── JwtService.java
    ├── RefreshRequest.java
    ├── SecurityBeansConfig.java
    └── SecurityConfig.java

service
├── api
│   ├── controller
│   │   └── PropertyController.java
│   ├── dto
│   │   ├── request
│   │   │   ├── PropertyCreateRequestDTO.java
│   │   │   └── PropertyImageRequestDTO.java
│   │   └── response
│   │       ├── ApiResponse.java
│   │       ├── PageMeta.java
│   │       ├── PropertyImageResponseDTO.java
│   │       └── PropertyResponseDTO.java
│   └── mapper
│       └── PropertyMapper.java
├── application
│   └── service
│       ├── PropertyService.java
│       ├── CustomerService.java
│       ├── CustomUserDetailsService.java
│       └── RefreshTokenService.java
├── domain
│   ├── entity
│   │   ├── Property.java
│   │   ├── PropertyImage.java
│   │   ├── Customer.java
│   │   ├── User.java
│   │   └── RefreshToken.java
│   └── enums
│       ├── OperationType.java
│       ├── PropertyType.java
│       └── RoleType.java
└── infrastructure
    ├── config
    │   └── CorsConfig.java
    ├── repository
    │   ├── PropertyRepository.java
    │   ├── PropertyImageRepository.java
    │   ├── UserRepository.java
    │   ├── CustomerRepository.java
    │   └── RefreshTokenRepository.java
    └── specification
        └── PropertySpecifications.java

user_service
├── api
│   ├── controller
│   │   └── AuthController.java
│   └── dto
│       ├── LoginRequest.java
│       └── RegisterRequest.java
```

## Tecnologías Utilizadas

**Backend:** Java 17, Spring Boot, Spring Security (JWT + Refresh Tokens), Spring Data JPA, PostgreSQL (Supabase), HikariCP, Maven, Docker

**Frontend:** React, Vite, Axios, CSS Grid, Modales personalizados

## Base de Datos

Se requiere configurar las credenciales en el backend usando application.yml:

```yml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 5
      minimum-idle: 1
      connection-timeout: 30000
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: ${SPRING_JPA_HIBERNATE_DDL_AUTO}
    show-sql: ${SPRING_JPA_SHOW_SQL}
server:
  port: 8083
logging:
  level:
    org.hibernate.SQL: OFF
    org.hibernate.type.descriptor.sql: OFF

## Base de Datos
Se requiere configurar las credenciales en el backend usando `application.yml`:

spring:
  datasource:
    url: jdbc:postgresql://<HOST>:5432/<DB_NAME>?sslmode=require
    username: <DB_USER>
    password: <DB_PASSWORD>
    driver-class-name: org.postgresql.Driver
    hikari:
      maximum-pool-size: 5
      minimum-idle: 1
      connection-timeout: 30000
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
    show-sql: false

server:
  port: 8083

logging:
  level:
    org.hibernate.SQL: OFF
    org.hibernate.type.descriptor.sql: OFF
```

## Archivo .env Recomendado

```yml
SPRING_DATASOURCE_URL=jdbc:postgresql://YOUR_HOST:5432/YOUR_DATABASE?sslmode=require
SPRING_DATASOURCE_USERNAME=YOUR_USERNAME
SPRING_DATASOURCE_PASSWORD=YOUR_PASSWORD
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false
```

## Cómo ejecutar el proyecto

1️⃣ Clonar el repositorio  
git clone https://github.com/JosKavi33/inmobiliaria-CQC.git  
cd ProyectoInmobiliaria

2️⃣ Ejecutar Backend (Spring Boot)  
cd property-service  
mvn clean install  
mvn spring-boot:run  

El backend quedará disponible en: http://localhost:8083

3️⃣ Ejecutar Frontend (React + Vite)  
En otra terminal:  
cd property-frontend  
npm install  
npm run dev  

El frontend quedará disponible en: http://localhost:5173

## Funcionalidades Implementadas
- Listado de propiedades con paginación
- Filtros por: Ciudad, Precio mínimo, Precio máximo
- Modal con información detallada
- Galería de imágenes por propiedad
- CRUD completo desde la interfaz
- Registro, login y logout de usuarios
- Autenticación segura con JWT
- Refresh tokens para mantener sesión activa
- Revocación de tokens al cerrar sesión
- Diseño responsive con grid adaptable

## Información de Propiedades
Cada propiedad contiene información como:  
Título, Tipo de propiedad, Tipo de operación, Precio, Ciudad y departamento, Dirección, Descripción, Habitaciones, Baños, Área del lote, Área construida, Imágenes asociadas

## Estado del Proyecto
Proyecto funcional con arquitectura desacoplada (frontend y backend separados). Pensado como proyecto práctico y escalable para uso profesional.  


## Autor
Jose Alberto Cabrejo Villar  
Técnico en Desarrollo de Software  
Proyecto full stack desarrollado con enfoque profesional.
