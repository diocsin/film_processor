package by.iluyshenko.film.processing.service;

import by.iluyshenko.film.processing.dto.MovieEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieProcessingServiceImpl implements MovieProcessingService {

    @Override
    public void processMovieEvent(MovieEvent event) {
        log.info("=== Начало обработки фильма ===");
        log.info("Фильм: {} ({})", event.getTitle(), event.getReleaseYear());
        log.info("IMDb ID: {}", event.getImdbId());
        log.info("Режиссер: {}", event.getDirector());
        log.info("Актеры: {}", event.getActors());
        log.info("Жанры: {}", event.getGenres());
        log.info("Рейтинг IMDb: {}", event.getImdbRating());
        
        // Симуляция асинхронной обработки (например, обогащение данных, анализ, индексация)
        try {
            log.info("Обработка данных фильма...");
            Thread.sleep(1000); // Симуляция обработки
            
            // Пример обработки: вычисление времени между сохранением и обработкой
            if (event.getEventTimestamp() != null) {
                Duration processingDelay = Duration.between(event.getEventTimestamp(), LocalDateTime.now());
                log.info("Время обработки: {} мс", processingDelay.toMillis());
            }
            
            log.info("=== Обработка фильма завершена успешно ===");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Обработка была прервана", e);
        } catch (Exception e) {
            log.error("Ошибка при обработке фильма: {}", event.getTitle(), e);
            throw new RuntimeException("Failed to process movie event", e);
        }
    }
}

