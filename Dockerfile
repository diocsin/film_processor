# Multi-stage build для movie-processing-service
FROM gradle:8-jdk21 AS build
WORKDIR /app

# Копируем build.gradle и исходный код
COPY build.gradle ./
COPY src ./src

# Собираем приложение используя gradle из образа
RUN gradle bootJar --no-daemon

# Финальный образ
FROM eclipse-temurin:21-jre
WORKDIR /app

# Копируем JAR из stage сборки
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]

