FROM amazoncorretto:17
EXPOSE 9000
ARG JAR_FILE=target/*.jar
COPY ./target/auth-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]