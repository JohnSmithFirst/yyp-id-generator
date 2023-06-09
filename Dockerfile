FROM  amazoncorretto:17-alpine3.17
ARG  JAR_FILE=id-generator-web/target/*.jar
COPY  ${JAR_FILE}  app.jar
EXPOSE 8080:8080
ENTRYPOINT  ["java","-jar","/app.jar"]