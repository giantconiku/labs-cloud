FROM eclipse-temurin:19-alpine
RUN mkdir -p /var/log
VOLUME /var/log
EXPOSE 8080
WORKDIR /app
COPY target/*.jar labs-app.jar
ENTRYPOINT ["java","-jar","labs-app.jar"]