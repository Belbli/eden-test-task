FROM openjdk:17-jdk-alpine AS builder
WORKDIR /app
COPY gradle/ gradle/
COPY gradlew build.gradle settings.gradle ./
COPY src/ src/
RUN ./gradlew --no-daemon build

FROM openjdk:17-jre-alpine
WORKDIR /app
EXPOSE 9090
COPY --from=builder /app/build/libs/authorization-*.jar authorization.jar
CMD ["java", "-jar", "/app/authorization.jar"]
