FROM openjdk:19-alpine
COPY target/assessment-0.0.1-SNAPSHOT.jar assessment-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/assessment-0.0.1-SNAPSHOT.jar"]