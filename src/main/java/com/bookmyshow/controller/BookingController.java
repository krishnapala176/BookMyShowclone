package com.bookmyshow.controller;

import com.bookmyshow.model.Booking;
import com.bookmyshow.model.User;
import com.bookmyshow.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public Booking bookShow(@RequestBody Booking booking, Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof User user)) {
            throw new RuntimeException("❌ Invalid authentication principal: User object not found in context");
        }

        return bookingService.bookShow(booking, user.getEmail());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<Booking> getUserBookings(@PathVariable String userId) {
        return bookingService.getBookingsByUserId(userId);
    }
}
