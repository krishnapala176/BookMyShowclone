package com.bookmyshow.repository;

import com.bookmyshow.model.Theatre;
import com.bookmyshow.model.TheatreStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TheatreRepository extends MongoRepository<Theatre, String> {
    List<Theatre> findByStatus(TheatreStatus status);

    // ✅ Count theatres by status
    long countByStatus(TheatreStatus status);
}
