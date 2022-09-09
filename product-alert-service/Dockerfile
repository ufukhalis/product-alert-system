FROM openjdk:17.0.1-slim

ADD /target/product-*.jar app.jar

ENV PORT 8083
EXPOSE 8083

ENTRYPOINT ["java","-jar","/app.jar"]
