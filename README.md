# 🏠 INMOBILIARIA-CQC

<p align="center">
  <a href="https://github.com/JosKavi33/inmobiliaria-CQC/actions">
    <img src="https://github.com/JosKavi33/inmobiliaria-CQC/actions/workflows/ci.yml/badge.svg"/>
  </a>
</p>
Proyecto full stack profesional desarrollado con arquitectura desacoplada,
seguridad JWT, manejo de imágenes con Supabase Storage, contenedorización con
Docker, integración continua (CI/CD) y despliegue en la nube.

* Backend: 100% funcional y desplegado.
* Frontend: En construcción.

------------------------------------------------------------
📌 BADGE DE CI AUTOMÁTICO
------------------------------------------------------------

El proyecto incluye Integración Continua con GitHub Actions. Este badge muestra el estado automático del pipeline:

- Verde: Build exitoso
- Rojo: Error en tests o compilación

------------------------------------------------------------
🏗️ DIAGRAMA VISUAL DE ARQUITECTURA
------------------------------------------------------------


Arquitectura del sistema:

```
                     +--------------------+
                     |      FRONTEND      |
                     |    React + Vite    |
                     +----------+---------+
                                |
                                v
                     +--------------------+
                     |       BACKEND      |
                     |   Spring Boot API  |
                     |   JWT + Refresh    |
                     +----------+---------+
                                |
                                v
                     +--------------------+
                     |   POSTGRESQL DB    |
                     |     (Supabase)     |
                     +--------------------+
                                |
                                v
                     +--------------------+
                     |  SUPABASE STORAGE  |
                     |     (Imágenes)     |
                     +--------------------+
```

Flujo:
Usuario → Frontend → Backend → Base de Datos
↓
Storage de Imágenes

------------------------------------------------------------
🏛️ ARQUITECTURA INTERNA DEL BACKEND
------------------------------------------------------------

Capas:

1) API Layer
    - Controllers
    - DTOs
    - Mappers

2) Application Layer
    - Servicios
    - Lógica de negocio

3) Domain Layer
    - Entidades
    - Enums
    - Reglas del dominio

4) Infrastructure Layer
    - Repositories
    - Specifications
    - Configuración

Beneficios:

- Bajo acoplamiento
- Alta mantenibilidad
- Escalable
- Profesional
- Listo para crecimiento empresarial

------------------------------------------------------------
🔐 SEGURIDAD
------------------------------------------------------------

- Spring Security
- JWT Access Token
- Refresh Token
- Revocación de sesiones
- Roles (ADMIN / USER)
- Protección con @PreAuthorize

Flujo:

Login →
Genera Access Token + Refresh Token →
Uso del Access Token →
Renovación con Refresh Token →
Logout revoca tokens

------------------------------------------------------------
🖼️ MANEJO DE IMÁGENES
------------------------------------------------------------

Integración con Supabase Storage.

Proceso:

1) Imagen subida al backend
2) Se almacena en el bucket
3) Solo se guarda el path en la base de datos
4) Al consultar:
    - Se generan Signed URLs dinámicas
    - Expiran automáticamente
5) Se pueden:
    - Agregar imágenes
    - Eliminar imágenes individuales
    - Reordenar imágenes
6) Al eliminar propiedad:
    - Se eliminan imágenes del bucket
    - Se elimina registro en base de datos

Arquitectura segura y escalable.

------------------------------------------------------------
🔄 CI/CD (DEVOPS)
------------------------------------------------------------

Implementado con GitHub Actions.

Pipeline automático:

1) Build con Maven
2) Ejecución de tests
3) Validación de compilación
4) Construcción de imagen Docker
5) Preparación para despliegue

Beneficios:

- Integración continua real
- Prevención de errores
- Automatización completa
- Flujo profesional empresarial
- Base para DevOps

------------------------------------------------------------
🐳 DOCKER
------------------------------------------------------------

Backend dockerizado con multi-stage build:

Etapa 1:

- Maven + JDK 17
- Compilación

Etapa 2:

- Imagen ligera Eclipse Temurin 17
- Solo el .jar final

Compatible con:

- Docker Compose
- Render
- Entornos cloud

------------------------------------------------------------
☁️ DESPLIEGUE EN PRODUCCIÓN
------------------------------------------------------------

Backend desplegado en Render.

Render:

- Detecta puerto automáticamente
- Usa variable de entorno PORT
- No requiere configuración manual

URL:

https://inmobiliaria-cqc.onrender.com


------------------------------------------------------------
⚙️ VARIABLES DE ENTORNO
------------------------------------------------------------

Se requiere archivo .env con:

```
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
SPRING_JPA_HIBERNATE_DDL_AUTO
SPRING_JPA_SHOW_SQL
SUPABASE_URL
SUPABASE_SERVICE_ROLE
SUPABASE_BUCKET
ADMIN_EMAIL
ADMIN_PASSWORD
CREATE_DEFAULT_ADMIN
```

En producción:
Las variables deben configurarse en la plataforma cloud.

------------------------------------------------------------
📡 ENDPOINTS PRINCIPALES
------------------------------------------------------------

------------------------------------------------------------
🏠 PROPIEDADES
------------------------------------------------------------

------------------------------------------------------------

* **GET /properties**

Descripción: Obtiene listado de propiedades.
Soporta filtros, ordenamiento y paginación mediante query params.

Ejemplo:

```json
GET /properties?page=0&size=10&sort=price&direction=asc
```

------------------------------------------------------------

* **GET /properties/{id}**

Descripción: Obtiene el detalle de una propiedad por su ID.
Incluye generación automática de Signed URLs para imágenes.

Ejemplo:

```json
GET /properties/1
```

------------------------------------------------------------

* **POST /properties**

Descripción: Crea una nueva propiedad.

Body (JSON):

```json
{
  "title": "Apartamento moderno en Cabecera",
  "price": 450000000,
  "administrationFee": 350000,
  "propertyType": "APARTMENT",
  "operationType": "SALE",
  "address": "Carrera 35 #48-120",
  "city": "Bucaramanga",
  "department": "Santander",
  "neighborhood": "Cabecera",
  "propertyDescription": "Apartamento amplio con excelente iluminación natural.",
  "locationDescription": "Ubicado cerca a centros comerciales y parques.",
  "bedrooms": 3,
  "bathrooms": 2,
  "parkingSpaces": 1,
  "lotArea": 0,
  "builtArea": 95
}
``` 

------------------------------------------------------------

* **PUT /properties/{id}**

Descripción: Actualiza una propiedad existente.

Body: Mismo formato que el POST.


------------------------------------------------------------

* **DELETE /properties/{id}**

Descripción: Elimina una propiedad.

También elimina sus imágenes del bucket de almacenamiento.

------------------------------------------------------------------
🖼️ IMÁGENES

------------------------------------------------------------

* **POST /properties/{id}/images**

Descripción: Sube una imagen a una propiedad.

Tipo de petición:
multipart/form-data

Key: file
Type: FILE
Value: Seleccionar imagen desde el equipo

La imagen se almacena en Supabase Storage.
En la base de datos solo se guarda el path.


------------------------------------------------------------

* **DELETE /properties/images/{imageId}**

Descripción: Elimina una imagen específica.

- Se elimina del bucket.
- Se elimina de la base de datos.
- Se reordenan las posiciones automáticamente.

------------------------------------------------------------
🔐 AUTENTICACIÓN
------------------------------------------------------------

* **POST /auth/register**

Descripción: Registro de nuevo usuario.

Body (JSON):

```json
{
  "email": "nuevo@test.com",
  "password": "123456",
  "firstName": "Jose",
  "lastName": "Cabrejo",
  "documentNumber": "123456789",
  "phone": "3000000000",
  "address": "Bucaramanga"
}
```

------------------------------------------------------------

* **POST /auth/login**

Descripción: Inicia sesión y genera:

- Access Token (JWT)
- Refresh Token

Body (JSON):

```json
{
  "email": "admin@inmobiliaria.com",
  "password": "Admin123*"
}
```

------------------------------------------------------------

* **POST /auth/refresh**

Descripción: Genera un nuevo Access Token usando el Refresh Token.

------------------------------------------------------------

* **POST /auth/logout**

Descripción: Revoca el Refresh Token y cierra la sesión.

Body (JSON):

```json
{
  "refreshToken": "EL_REFRESH_TOKEN_AQUI"
}
```

------------------------------------------------------------
📊 ESTADO DEL PROYECTO
------------------------------------------------------------

* Backend: 100% funcional
* CI/CD: Implementado
* Docker: Implementado
* Deploy: Activo
* Imágenes: Integración completa
* Frontend: En construcción
* Arquitectura: Profesional y escalable

------------------------------------------------------------
👨‍💻 AUTOR
------------------------------------------------------------

Jose Alberto Cabrejo Villar

Técnico en Desarrollo de Software

Proyecto desarrollado con enfoque profesional,
arquitectura escalable y prácticas modernas.