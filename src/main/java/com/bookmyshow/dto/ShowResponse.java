package com.bookmyshow.dto;

public class ShowResponse {
    private String showId;
    private String movieTitle;
    private String showTime;
    private int availableSeats;

    public ShowResponse(String showId, String movieTitle, String showTime, int availableSeats) {
        this.showId = showId;
        this.movieTitle = movieTitle;
        this.showTime = showTime;
        this.availableSeats = availableSeats;
    }

    public String getShowId() {
        return showId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getShowTime() {
        return showTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
