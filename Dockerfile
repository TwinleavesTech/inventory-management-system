FROM openjdk:17-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} spring-boot-application.jar
CMD ["java","-jar","spring-boot-application.jar"]
