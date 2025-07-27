package com.bookmyshow.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "shows")
public class Show {

    @Id
    private String id;

    private String movieId;
    private String theatreId; // ✅ Consistent spelling with "re"
    private LocalDateTime showTime;
    private int availableSeats;

    @Transient
    private List<Booking> bookings; // Not stored in DB, only used for response display

    public Show() {
    }

    public Show(String id, String movieId, String theatreId, LocalDateTime showTime, int availableSeats) {
        this.id = id;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.showTime = showTime;
        this.availableSeats = availableSeats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(String theatreId) {
        this.theatreId = theatreId;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
