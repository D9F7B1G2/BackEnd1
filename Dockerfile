# Imagen base de OpenJDK 17
FROM eclipse-temurin:17-jdk

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado por Maven
COPY target/*.jar app.jar

# Exponer el puerto en el que corre Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
