FROM eclipse-temurin:17-jdk-alpine AS BUILDER

WORKDIR /app
COPY gradlew build.gradle settings.gradle ./
COPY gradle ./gradle
COPY src/main ./src/main

RUN ./gradlew bootJar

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=builder /app/build/libs/fine-dust-alarm-0.0.1-SNAPSHOT.jar app.jar

ENV PROFILE = "dev"

ENTRYPOINT java -jar app.jar --spring.profiles.active=$PROFILE