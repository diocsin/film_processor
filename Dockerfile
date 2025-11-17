# Multi-stage build для movie-processing-service
FROM eclipse-temurin:23-jdk-alpine AS build

WORKDIR /app

# 1. Копируем только то, что нужно для разрешения зависимостей
COPY gradle ./gradle
COPY gradlew gradlew
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle

# 2. Скачиваем зависимости (это самый долгий шаг, его хочется закешировать)
RUN chmod +x gradlew && ./gradlew --no-daemon --info dependencies || true

# 3. Теперь копируем исходный код
COPY src ./src

# Собираем приложение используя gradle из образа
RUN ./gradlew --no-daemon bootJar

# Финальный образ
FROM eclipse-temurin:23-jre

WORKDIR /app

# Копируем JAR из stage сборки
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8081

# Лучше явно задать разумные JVM опции для контейнера
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
