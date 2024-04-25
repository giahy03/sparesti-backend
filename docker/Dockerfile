FROM maven:3-eclipse-temurin-21-alpine as builder
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn -Dmaven.compiler.source=21 -Dmaven.compiler.target=21 package

FROM openjdk:21
COPY --from=builder /usr/src/app/.env /.env
COPY --from=builder /usr/src/app/target/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java"]
CMD ["-jar", "/app.jar"]