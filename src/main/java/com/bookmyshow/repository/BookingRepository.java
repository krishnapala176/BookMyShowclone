package com.bookmyshow.repository;

import com.bookmyshow.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByUserId(String userId);  // Match your field name
    List<Booking> findByShowId(String showId);
}
