package com.bookmyshow.controller;

import com.bookmyshow.model.Payment;
import com.bookmyshow.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // 🎯 Make a payment
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public Payment makePayment(@RequestBody Payment payment) {
        return paymentService.makePayment(payment);
    }

    // 📃 Get all payments by a specific user
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<Payment> getPaymentsByUserId(@PathVariable String userId) {
        return paymentService.getPaymentsByUserId(userId);
    }

    // 📃 Get all payments for a specific booking
    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public List<Payment> getPaymentsByBookingId(@PathVariable String bookingId) {
        return paymentService.getPaymentsByBookingId(bookingId);
    }
}
