package guru.springframework.netflux.bootstrap;

import guru.springframework.netflux.domain.Movie;
import guru.springframework.netflux.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class InitMovies implements CommandLineRunner {
    private final MovieRepository movieRepository;

    @Override
    public void run(String... args) throws Exception {
        movieRepository.deleteAll()
                .thenMany(
                        Flux.just("Silence of the Lambdas", "Enter the Mono<Void>", "Lord of the Fluxes")
                                .map(title -> Movie.builder().title(title).build())
                                .flatMap(movieRepository::save))
                                .subscribe(null, null, () -> {
                                    movieRepository.findAll().subscribe(System.out::println);
                });
    }
}
