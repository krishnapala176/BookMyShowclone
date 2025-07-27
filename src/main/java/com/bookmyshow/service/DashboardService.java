package com.bookmyshow.service;

import com.bookmyshow.exception.Common.ResourceNotFoundException;
import com.bookmyshow.model.Booking;
import com.bookmyshow.model.Payment;
import com.bookmyshow.model.Show;
import com.bookmyshow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    public Map<String, Object> getCustomerDashboard(String userId) {
        System.out.println("📌 Fetching dashboard for userId: " + userId);

        List<Booking> bookings = bookingRepository.findByUserId(userId);
        List<Payment> payments = paymentRepository.findByUserId(userId);

        if (bookings.isEmpty() && payments.isEmpty()) {
            throw new ResourceNotFoundException("No bookings or payments found for user with ID: " + userId);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("totalBookings", bookings.size());
        data.put("totalPayments", payments.size());
        return data;
    }

    public Map<String, Object> getTheatreDashboard(String theatreId) {
        List<Show> shows = showRepository.findByTheatreId(theatreId);

        if (shows.isEmpty()) {
            throw new ResourceNotFoundException("No shows found for theatre ID: " + theatreId);
        }

        int totalSeats = shows.stream().mapToInt(Show::getAvailableSeats).sum();
        Map<String, Object> data = new HashMap<>();
        data.put("showsCreated", shows.size());
        data.put("totalSeatsAvailable", totalSeats);
        return data;
    }

    public Map<String, Object> getAdminDashboard() {
        int totalUsers = userRepository.findAll().size();
        int totalMovies = movieRepository.findAll().size();
        int totalBookings = bookingRepository.findAll().size();

        Map<String, Object> data = new HashMap<>();
        data.put("totalUsers", totalUsers);
        data.put("totalMovies", totalMovies);
        data.put("totalBookings", totalBookings);
        return data;
    }

    public List<Show> getTheatreShowsWithBookings(String theatreId) {
        List<Show> shows = showRepository.findByTheatreId(theatreId);

        if (shows.isEmpty()) {
            throw new ResourceNotFoundException("No shows found for theatre ID: " + theatreId);
        }

        for (Show show : shows) {
            List<Booking> bookings = bookingRepository.findByShowId(show.getId());
            show.setBookings(bookings);
        }

        return shows;
    }
}
