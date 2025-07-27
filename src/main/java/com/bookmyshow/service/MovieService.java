package com.bookmyshow.service;

import com.bookmyshow.exception.File.FileUploadException;
import com.bookmyshow.exception.Movie.MovieNotFoundException;
import com.bookmyshow.model.Movie;
import com.bookmyshow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public ResponseEntity<String> uploadAssets(String movieId, MultipartFile posterFile, MultipartFile trailerFile) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with ID: " + movieId));

        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "movie-" + movieId;
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        try {
            String posterName = StringUtils.cleanPath(posterFile.getOriginalFilename());
            File posterPath = new File(dir, "poster_" + posterName);
            posterFile.transferTo(posterPath);

            String trailerName = StringUtils.cleanPath(trailerFile.getOriginalFilename());
            File trailerPath = new File(dir, "trailer_" + trailerName);
            trailerFile.transferTo(trailerPath);

            movie.setPosterUrl("/" + uploadDir + "/" + posterPath.getName());
            movie.setTrailerUrl("/" + uploadDir + "/" + trailerPath.getName());

            movieRepository.save(movie);
            return ResponseEntity.ok("Files uploaded and movie updated successfully.");
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload files for movie ID: " + movieId, e);
        }
    }

    public String getMovieTitleById(String movieId) {
        return movieRepository.findById(movieId)
                .map(Movie::getTitle)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with ID: " + movieId));
    }
}
