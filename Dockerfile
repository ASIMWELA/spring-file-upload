FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/file-upload.jar
COPY ${JAR_FILE} file-upload.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/file-upload.jar"]