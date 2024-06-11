# Usar la imagen base de Maven para compilar la aplicación
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar la aplicación
COPY src ./src
RUN mvn clean package -DskipTests

# Usar una imagen base más ligera para la ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiar el jar generado en la etapa de compilación
COPY --from=build /app/target/tu-aplicacion.jar tu-aplicacion.jar

# Exponer el puerto en el que la aplicación estará escuchando
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "tu-aplicacion.jar"]
