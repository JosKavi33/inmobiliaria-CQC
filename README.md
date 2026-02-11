# 🏠 Inmobiliaria Cabrejo

Sistema web inmobiliario desarrollado con arquitectura full stack para la gestión de propiedades inmobiliarias.

Permite realizar operaciones completas de:
- Crear propiedades
- Editar propiedades
- Eliminar propiedades
- Listar propiedades
- Filtrar resultados
- Paginación
- Visualización detallada con galería de imágenes

---

## 🧱 Arquitectura del Proyecto

El proyecto está organizado en dos módulos principales dentro de una sola carpeta raíz:

```
InmobiliariaCabrejo
│
├── property-service    (Backend - Spring Boot)
└── property-frontend   (Frontend - React + Vite)
```

---

## 🚀 Tecnologías Utilizadas

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL (Supabase)
- HikariCP
- Maven

### Frontend
- React
- Vite
- Axios
- CSS Grid
- Modales personalizados

---

## 🗄 Base de Datos

La base de datos utilizada es PostgreSQL (por ejemplo Supabase).

Para ejecutar el proyecto es necesario crear una base de datos PostgreSQL y configurar las credenciales en el backend.

Archivo a configurar:

```
property-service/src/main/resources/application.properties
```

Ejemplo (SIN datos sensibles):

```properties
spring.datasource.url=jdbc:postgresql://YOUR_HOST:5432/YOUR_DATABASE?sslmode=require
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.datasource.hikari.maximum-pool-size=5
spring.datasource.hikari.minimum-idle=1
spring.datasource.hikari.connection-timeout=30000
server.port=8083
```

⚠ IMPORTANTE:  
No subir credenciales reales al repositorio.

---

## ⚙️ Cómo ejecutar el proyecto

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/JosKavi33/inmobiliaria-CQC.git
cd ProyectoInmobiliaria
```

---

### 2️⃣ Ejecutar Backend (Spring Boot)

```bash
cd property-service
mvn clean install
mvn spring-boot:run
```

El backend quedará disponible en:

```
http://localhost:8083
```

---

### 3️⃣ Ejecutar Frontend (React + Vite)

En otra terminal:

```bash
cd property-frontend
npm install
npm run dev
```

El frontend quedará disponible en:

```
http://localhost:5173
```

---

## 📌 Funcionalidades Implementadas

- Listado de propiedades con paginación
- Filtros por:
  - Ciudad
  - Precio mínimo
  - Precio máximo
- Modal con información detallada
- Galería de imágenes por propiedad
- CRUD completo desde la interfaz
- Diseño responsive con grid adaptable

---

## 📷 Información de Propiedades

Cada propiedad contiene información como:

- Título
- Tipo de propiedad
- Tipo de operación
- Precio
- Ciudad y departamento
- Dirección
- Descripción
- Habitaciones
- Baños
- Área del lote
- Área construida
- Imágenes asociadas

---

## 📈 Estado del Proyecto

Proyecto funcional con arquitectura desacoplada (frontend y backend separados).

Pensado como proyecto práctico y escalable para uso profesional.

### Posibles mejoras futuras:
- Autenticación y roles
- Gestión de usuarios
- Subida de imágenes a almacenamiento en la nube
- Deploy en producción (Railway / Render / Vercel)

---

## 👨‍💻 Autor

Jose Alberto Cabrejo Villar  
Técnico en Desarrollo de Software  

Proyecto full stack desarrollado con enfoque profesional.
