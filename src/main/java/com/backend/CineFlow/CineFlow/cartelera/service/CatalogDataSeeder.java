package com.backend.CineFlow.CineFlow.cartelera.service;

import com.backend.CineFlow.CineFlow.cartelera.factory.CinemaFunctionFactoryResolver;
import com.backend.CineFlow.CineFlow.cartelera.model.Funcion;
import com.backend.CineFlow.CineFlow.cartelera.model.Pelicula;
import com.backend.CineFlow.CineFlow.cartelera.model.FormatoProyeccion;
import com.backend.CineFlow.CineFlow.cartelera.model.Sala;
import com.backend.CineFlow.CineFlow.cartelera.repository.CinemaFunctionRepository;
import com.backend.CineFlow.CineFlow.cartelera.repository.MovieRepository;
import com.backend.CineFlow.CineFlow.cartelera.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class CatalogDataSeeder implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final CinemaFunctionRepository cinemaFunctionRepository;
    private final CinemaFunctionFactoryResolver factoryResolver;

    public CatalogDataSeeder(MovieRepository movieRepository,
                             RoomRepository roomRepository,
                             CinemaFunctionRepository cinemaFunctionRepository,
                             CinemaFunctionFactoryResolver factoryResolver) {
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.cinemaFunctionRepository = cinemaFunctionRepository;
        this.factoryResolver = factoryResolver;
    }

    @Override
    public void run(String... args) {
        if (movieRepository.count() == 0) {
            movieRepository.save(new Pelicula("Neón Nocturno", "Una historia de misterio ambientada en una ciudad futurista.", "Thriller", 118, "PG-13", true));
            movieRepository.save(new Pelicula("Latido de Acero", "Acción y ciencia ficción en una misión de rescate espacial.", "Action", 126, "PG-13", true));
            movieRepository.save(new Pelicula("Destino de Papel", "Drama íntimo sobre decisiones que cambian una vida.", "Drama", 104, "PG", true));
        }

        if (roomRepository.count() == 0) {
            roomRepository.save(new Sala("Sala 1", 8, 12, true));
            roomRepository.save(new Sala("Sala 2", 10, 14, true));
        }

        if (cinemaFunctionRepository.count() == 0) {
            Pelicula movie = movieRepository.findByEnCarteleraTrue().get(0);
            Sala room = roomRepository.findAll().get(0);
            Funcion cinemaFunction = factoryResolver.resolve(FormatoProyeccion.TWO_D)
                    .create(movie, room, LocalDateTime.now().plusDays(1).withHour(19).withMinute(30).withSecond(0).withNano(0), BigDecimal.valueOf(9.50));
            cinemaFunctionRepository.save(Objects.requireNonNull(cinemaFunction, "La función semilla no puede ser nula"));
        }
    }
}