FROM openjdk:17.0.1-slim

ADD /target/product-*.jar app.jar

ENV PORT 8082
EXPOSE 8082

ENTRYPOINT ["java","-jar","/app.jar"]
