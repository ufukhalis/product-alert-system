FROM openjdk:17.0.1-slim

ADD /target/user-*.jar app.jar

ENV PORT 8081
EXPOSE 8081

ENTRYPOINT ["java","-jar","/app.jar"]
