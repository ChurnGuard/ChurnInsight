# Stage 1: Instalar las dependencias
# Nos da la ventaja de hacer rebuilds rapidos cuando el codigo cambia pero las dependencias no
FROM maven:3.9-eclipse-temurin-17 AS deps
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

# Stage 2: Construir la aplicacion
# Compilamos la aplicaciom de spring boot y generamos el jar
# Nota: reusamos las dependencias en cache del stage 1 para tener rebuilds mas rapidos
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY --from=deps /root/.m2 /root/.m2
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 3: Construimos la imagen de produccion
# con solo JRE (sin herramientas de build)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]