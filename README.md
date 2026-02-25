# 🏠 Inmobiliaria Cabrejo

Sistema web inmobiliario full stack para la gestión de propiedades y usuarios.
Arquitectura desacoplada (Frontend + Backend).
Autenticación con JWT + Refresh Tokens.
Manejo profesional de imágenes con Supabase Storage y Signed URLs.
Despliegue con Docker.

------------------------------------------------------------
📌 ARQUITECTURA DEL PROYECTO
------------------------------------------------------------

```
InmobiliariaCabrejo
├── property-service      (Backend - Spring Boot)
├── property-frontend     (Frontend - React + Vite)
└── docker-compose.yml    (Orquestación con Docker)
```

------------------------------------------------------------
🛠 TECNOLOGÍAS UTILIZADAS
------------------------------------------------------------

Backend:

- Java 17
- Spring Boot
- Spring Security (JWT + Refresh Tokens)
- Spring Data JPA
- PostgreSQL (Supabase)
- Supabase Storage
- Maven
- Docker

Frontend:

- React
- Vite
- Axios
- CSS Grid

------------------------------------------------------------
🖼 MANEJO DE IMÁGENES
------------------------------------------------------------

Las imágenes se almacenan en Supabase Storage.

Bucket utilizado:
property-images

Flujo de trabajo:

1. El backend sube la imagen al bucket.
2. En la base de datos se guarda únicamente el imagePath.
3. Cuando se consultan propiedades, el backend genera Signed URLs.
4. El frontend recibe la URL firmada y la utiliza para mostrar la imagen.
5. Las URLs son temporales por seguridad.
6. Las imágenes pueden:
    - Subirse individualmente
    - Eliminarse individualmente
    - Eliminarse junto con la propiedad
    - Reordenarse mediante el campo "position"

------------------------------------------------------------
🔐 VARIABLES DE ENTORNO (OBLIGATORIAS)
------------------------------------------------------------

Para trabajar en local debes configurar variables de entorno.

El proyecto NO contiene credenciales hardcodeadas.

Variables requeridas:

```
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD

SUPABASE_URL
SUPABASE_SERVICE_ROLE
SUPABASE_BUCKET

ADMIN_EMAIL
ADMIN_PASSWORD
CREATE_DEFAULT_ADMIN
```

------------------------------------------------------------
📄 EJEMPLO DE ARCHIVO .env
------------------------------------------------------------

```
SPRING_DATASOURCE_URL=jdbc:postgresql://HOST:5432/DB?sslmode=require
SPRING_DATASOURCE_USERNAME=YOUR_USERNAME
SPRING_DATASOURCE_PASSWORD=YOUR_PASSWORD

SUPABASE_URL=https://xxxxx.supabase.co
SUPABASE_SERVICE_ROLE=YOUR_SERVICE_ROLE_KEY
SUPABASE_BUCKET=property-images

ADMIN_EMAIL=admin@inmobiliaria.com
ADMIN_PASSWORD=Admin123*
CREATE_DEFAULT_ADMIN=true
```

------------------------------------------------------------
🐳 EJECUCIÓN CON DOCKER
------------------------------------------------------------

Desde la raíz del proyecto:

```
docker compose build
```

```
docker compose up -d
```

Backend disponible en:

```
http://localhost:8083
```

------------------------------------------------------------
💻 EJECUCIÓN LOCAL SIN DOCKER
------------------------------------------------------------

Backend:

```
cd property-service
mvn clean install
mvn spring-boot:run
```

Frontend:

ESTADO DEL FRONTEND(despliega pero falta aplicacion de funcionalidades)

El frontend se encuentra actualmente en desarrollo.

```
cd property-frontend
npm install
npm run dev
```

Frontend disponible en:

```
http://localhost:5173
```

------------------------------------------------------------
📡 ENDPOINTS PRINCIPALES
------------------------------------------------------------

PROPIEDADES

```
GET /properties
```

```
GET /properties/{id}
```

```JSON
POST /properties

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

```
PUT /properties/{id}
```

```
DELETE /properties/{id}
```

IMÁGENES

```
POST /properties/{id}/images

form-data -> Key | file  (FILE) | VALUE (Seleccionar imagen del equipo)
```

```
DELETE /properties/images/{imageId}
```

AUTENTICACIÓN

```JSON
Registro /auth/register

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

```JSON
Login /auth/login

{
  "email": "admin@inmobiliaria.com",
  "password": "Admin123*"
}
```

```
Refresh Token /auth/refresh
```

```JSON
Logout (revocación de tokens) /auth/logout

{
  "refreshToken": "EL_REFRESH_TOKEN_AQUI"
}
```

------------------------------------------------------------
📊 FUNCIONALIDADES IMPLEMENTADAS
------------------------------------------------------------

- CRUD completo de propiedades
- Gestión de imágenes con Supabase
- Signed URLs automáticas
- Eliminación de imágenes en bucket
- Eliminación en cascada
- Reordenamiento de imágenes
- Filtros avanzados
- Paginación
- Seguridad con JWT
- Refresh tokens
- Protección por roles (ADMIN)
- Inicialización automática de administrador
- Arquitectura limpia por capas
- Manejo profesional de errores
- Logs estructurados con SLF4J

------------------------------------------------------------
🏗 ARQUITECTURA INTERNA
------------------------------------------------------------

Capas del backend:

- api
- application
- domain
- infrastructure
- common

Patrones aplicados:

- DTO + Mapper
- Specification Pattern
- CascadeType.ALL
- OrphanRemoval
- Signed URL Strategy
- Separación por responsabilidades

------------------------------------------------------------
📦 VERSIONAMIENTO
------------------------------------------------------------

Recomendado usar versionado semántico:

Ejemplo:

v1.0.0

Si se usa Docker, también versionar imágenes:

inmobiliaria-backend:1.0.0

------------------------------------------------------------
👨‍💻 AUTOR
------------------------------------------------------------

Jose Alberto Cabrejo Villar

Técnico en Desarrollo de Software

Proyecto Full Stack con enfoque profesional y escalable.