FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY gradle gradle
COPY gradlew build.gradle.kts settings.gradle.kts ./
RUN ls -lR && ./gradlew install

COPY src ./src

CMD ["./gradlew", "spring-boot:run"]