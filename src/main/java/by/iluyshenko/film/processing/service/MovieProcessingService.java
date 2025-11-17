package by.iluyshenko.film.processing.service;

import by.iluyshenko.film.processing.dto.MovieEvent;

public interface MovieProcessingService {
    void processMovieEvent(MovieEvent event);
}

