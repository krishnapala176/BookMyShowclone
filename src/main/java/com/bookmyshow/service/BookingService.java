package com.bookmyshow.service;

import com.bookmyshow.exception.Common.BadRequestException;
import com.bookmyshow.exception.Email.EmailSendFailureException;
import com.bookmyshow.exception.Common.ResourceNotFoundException;
import com.bookmyshow.model.Booking;
import com.bookmyshow.model.Show;
import com.bookmyshow.model.User;
import com.bookmyshow.repository.BookingRepository;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TheatreService theatreService;

    @Autowired
    private EmailService emailService;

    public Booking bookShow(Booking booking, String email) {
        Show show = showRepository.findById(booking.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show not found with ID: " + booking.getShowId()));

        System.out.println("📢 Show ID: " + show.getId());
        System.out.println("📢 Movie ID in Show: " + show.getMovieId());
        System.out.println("📢 Theatre ID in Show: " + show.getTheatreId());

        if (show.getAvailableSeats() < booking.getNumberOfSeats()) {
            throw new BadRequestException("Not enough seats available");
        }

        show.setAvailableSeats(show.getAvailableSeats() - booking.getNumberOfSeats());
        showRepository.save(show);

        booking.setBookingTime(LocalDateTime.now());
        Booking savedBooking = bookingRepository.save(booking);

        User user = userService.getUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        String movieTitle = movieService.getMovieTitleById(show.getMovieId());
        String theatreName = theatreService.getTheatreNameById(show.getTheatreId());
        String showTime = (show.getShowTime() != null) ? show.getShowTime().toString() : "Not Scheduled";

        System.out.println("🎬 Resolved Movie Title: " + movieTitle);
        System.out.println("🏢 Resolved Theatre Name: " + theatreName);

        List<String> dummySeats = new ArrayList<>();
        for (int i = 1; i <= booking.getNumberOfSeats(); i++) {
            dummySeats.add("Seat-" + i);
        }

        try {
            emailService.sendBookingConfirmationEmail(
                    user.getEmail(),
                    user.getName(),
                    movieTitle,
                    theatreName,
                    showTime,
                    booking.getId(),
                    dummySeats
            );
        } catch (Exception e) {
            throw new EmailSendFailureException("Failed to send booking confirmation email");
        }

        return savedBooking;
    }

    public List<Booking> getBookingsByUserId(String userId) {
        return bookingRepository.findByUserId(userId);
    }
}
