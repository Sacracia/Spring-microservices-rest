FROM amazoncorretto:17
EXPOSE 9001
ARG JAR_FILE=target/*.jar
COPY ./target/ticket-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]