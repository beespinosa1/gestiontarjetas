FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY target/gestiontarjetas-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8092

ENTRYPOINT ["java", "-jar", "app.jar"]