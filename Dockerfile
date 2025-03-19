# Usamos una imagen oficial de Java como base
FROM openjdk:17-jdk-slim

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el JAR generado
COPY demo-0.0.1-SNAPSHOT.jar app.jar

# Especificamos el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8080"]
