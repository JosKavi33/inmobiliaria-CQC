# 🏠 Inmobiliaria Cabrejo Quinta Casas

Aplicación web para gestión de propiedades inmobiliarias con funcionalidades completas de CRUD, filtros dinámicos y paginación, conectada a una base de datos PostgreSQL alojada en Supabase.

Proyecto desarrollado como práctica profesional integrando frontend moderno con backend REST en Spring Boot.

🚀 Tecnologías utilizadas

## Frontend

React | Vite | Axios |CSS | Grid / Flexbox |
Modal dinámico para detalle de propiedades

## Backend

Spring Boot | Spring Data JPA | Hibernate | API REST |
Paginación con Pageable | Filtros dinámicos

## Base de Datos

PostgreSQL | Supabase (entorno cloud)

# ✨ Funcionalidades implementadas

✔ Crear propiedades

✔ Editar propiedades

✔ Eliminar propiedades

✔ Listar propiedades

✔ Paginación configurable (selector dinámico de tamaño de página)

✔ Filtros por:

Ciudad | Precio mínimo |Precio máximo

✔ Modal detallado con:

Información completa | Galería de imágenes | Navegación entre imágenes | Acciones de editar y eliminar

✔ Grid responsive con tamaño fijo de tarjetas

# 🖼 Características de la UI

Diseño responsive

3 propiedades visibles en escritorio (configurable)

Tarjetas con ancho máximo fijo

Paginación centrada

Manejo de estados loading y error

⚙️ Configuración del proyecto
1️⃣ Clonar el repositorio
git clone <URL_DEL_REPOSITORIO>
cd nombre-del-proyecto

🔌 Configuración Backend (Spring Boot)
application.properties (ejemplo)


spring.datasource.url=jdbc:postgresql://<HOST_SUPABASE>:5432/<DATABASE>
spring.datasource.username=<USER>
spring.datasource.password=<PASSWORD>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
server.port=8083

🌐 Configuración Frontend

Instalar dependencias:

'npm install'


Ejecutar proyecto:

npm run dev


Por defecto corre en:

http://localhost:5173


El frontend consume el backend en:

http://localhost:8083


Si el backend cambia de puerto o dominio, modificar:

src/api/propertyApi.js

📂 Estructura principal (Frontend)
src/
 ├── api/
 │    └── propertyApi.js
 ├── components/
 │    ├── PropertyCard.jsx
 │    ├── PropertyList.jsx
 │    └── AddPropertyModal.jsx
 ├── App.jsx
 └── main.jsx

🔐 Seguridad

Las credenciales de Supabase NO deben subirse al repositorio.

Usar .gitignore para excluir:

application.properties

.env

node_modules

📈 Próximas mejoras

Autenticación con JWT

Roles (Admin / Customer)

Deploy en la nube (Render / Railway / Vercel)

Separación de ambientes (dev / prod)

Manejo global de errores

Mejora visual con framework UI

👨‍💻 Autor

Jose Alberto Cabrejo Villar
Técnico en Desarrollo de Software

Proyecto académico con enfoque profesional, aplicando arquitectura REST y buenas prácticas.