FROM eclipse-temurin:22-jre
ARG JAR_FILE=target/*.jar
COPY ./target/Stock-Tracking-System-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
