FROM maven:3.9.4-eclipse-temurin-22 AS build
WORKDIR /app
COPY . .
RUN mvn clean package
CMD ["mvn", "spring-boot:run"]
