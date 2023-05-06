FROM bellsoft/liberica-openjdk-alpine

ARG JAR_FILE=target/*.jar

COPY env.properties env.properties
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]
