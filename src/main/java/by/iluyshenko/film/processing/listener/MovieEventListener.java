package by.iluyshenko.film.processing.listener;

import by.iluyshenko.film.processing.config.RabbitMQConfig;
import by.iluyshenko.film.processing.dto.MovieEvent;
import by.iluyshenko.film.processing.service.MovieProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovieEventListener {

    private final MovieProcessingService movieProcessingService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleMovieSavedEvent(MovieEvent event) {
        log.info("Получено сообщение о сохранении фильма: {}", event.getTitle());
        try {
            movieProcessingService.processMovieEvent(event);
        } catch (Exception e) {
            log.error("Ошибка при обработке события фильма: {}", event.getTitle(), e);
            // В реальном приложении здесь можно добавить retry механизм или dead letter queue
            throw e; // Пробрасываем исключение для повторной обработки
        }
    }
}

