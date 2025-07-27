package com.bookmyshow.controller;

import com.bookmyshow.model.Movie;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService; // ✅ Needed for file upload logic

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Movie addMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @GetMapping("/search")
    public List<Movie> searchMoviesByTitle(@RequestParam String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    @GetMapping("/filter/genre")
    public List<Movie> filterByGenre(@RequestParam String genre) {
        return movieRepository.findByGenreIgnoreCase(genre);
    }

    @GetMapping("/filter/language")
    public List<Movie> filterByLanguage(@RequestParam String language) {
        return movieRepository.findByLanguageIgnoreCase(language);
    }

    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Movie updateMovie(@PathVariable String id, @RequestBody Movie updatedMovie) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setTitle(updatedMovie.getTitle());
        movie.setDescription(updatedMovie.getDescription());
        movie.setGenre(updatedMovie.getGenre());
        movie.setDuration(updatedMovie.getDuration());
        movie.setLanguage(updatedMovie.getLanguage());
        return movieRepository.save(movie);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMovie(@PathVariable String id) {
        movieRepository.deleteById(id);
    }

    // ✅ NEW: Upload poster and trailer files
    @PostMapping("/{movieId}/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> uploadMovieAssets(
            @PathVariable String movieId,
            @RequestParam("poster") MultipartFile posterFile,
            @RequestParam("trailer") MultipartFile trailerFile
    ) {
        return movieService.uploadAssets(movieId, posterFile, trailerFile);
    }

}
