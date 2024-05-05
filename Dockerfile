FROM eclipse-temurin:19-alpine
RUN mkdir -p /var/logs
VOLUME /var/logs
EXPOSE 8080
WORKDIR /app
COPY target/*.jar labs-app.jar
ENTRYPOINT ["java","-jar","labs-app.jar"]