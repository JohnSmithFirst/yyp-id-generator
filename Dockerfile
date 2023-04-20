FROM  amazoncorretto:17-alpine3.17
VOLUME  /tmp
ARG  JAR_FILE=target/*.jar
COPY  ${JAR_FILE}  app.jar
EXPOSE 8080:8080
ENTRYPOINT  ["java","-jar","/app.jar"]