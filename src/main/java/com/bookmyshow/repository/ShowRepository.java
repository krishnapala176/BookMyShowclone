package com.bookmyshow.repository;

import com.bookmyshow.model.Show;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ShowRepository extends MongoRepository<Show, String>
{
    List<Show> findByMovieId(String movieId);
    List<Show> findByTheatreId(String theatreId);
}
