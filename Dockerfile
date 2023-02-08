FROM openjdk:14-jdk-alpine
COPY target/amazing-games-backend-0.0.1-SNAPSHOT.jar docker-amazing-games-backend-0.0.1-SNAPSHOT.jar 
ENTRYPOINT ["java","-jar","/amazing-games-backend-0.0.1-SNAPSHOT.jar"]
