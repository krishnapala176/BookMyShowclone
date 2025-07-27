package com.bookmyshow.controller;

import com.bookmyshow.dto.ShowResponse;
import com.bookmyshow.model.Movie;
import com.bookmyshow.model.Show;
import com.bookmyshow.repository.MovieRepository;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/view")
public class ShowViewController {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/shows/{movieId}")
    public List<ShowResponse> getShowsByMovie(@PathVariable String movieId) {
        List<Show> shows = showRepository.findByMovieId(movieId);

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        return shows.stream().map(show -> {
            System.out.println("Available seats for show: " + show.getAvailableSeats());

            return new ShowResponse(
                    show.getId(),
                    movie.getTitle(),
                    show.getShowTime() != null ? show.getShowTime().toString() : "Not Scheduled",
                    show.getAvailableSeats()
            );
        }).collect(Collectors.toList());
    }
}
