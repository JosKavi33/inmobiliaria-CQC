# ======= BACKEND =======

# 1️⃣ Build stage: Maven + JDK 17
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar pom.xml primero para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar la app (saltando tests para acelerar)
RUN mvn clean package -DskipTests

# 2️⃣ Runtime stage: solo JDK 17
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiar el jar del build stage
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto
EXPOSE 8083

# Comando por defecto
ENTRYPOINT ["java","-jar","app.jar"]
