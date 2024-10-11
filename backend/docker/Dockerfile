FROM eclipse-temurin:latest AS build
ADD . /app
WORKDIR /app
RUN ./mvnw clean verify install

FROM eclipse-temurin:latest AS service
COPY --from=build /app/target/inventario-0.0.1-SNAPSHOT.jar /app/inventario-0.0.1-SNAPSHOT.jar
RUN chmod +x /app/inventario-0.0.1-SNAPSHOT.jar
EXPOSE 4000
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=selenium", "/app/inventario-0.0.1-SNAPSHOT.jar"]