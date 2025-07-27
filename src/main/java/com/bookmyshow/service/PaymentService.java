package com.bookmyshow.service;

import com.bookmyshow.exception.Payment.PaymentNotFoundException;
import com.bookmyshow.model.Payment;
import com.bookmyshow.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment makePayment(Payment payment) {
        payment.setPaymentTime(LocalDateTime.now());
        payment.setStatus("SUCCESS"); // Dummy payment for now
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByUserId(String userId) {
        List<Payment> payments = paymentRepository.findByUserId(userId);
        if (payments == null || payments.isEmpty()) {
            throw new PaymentNotFoundException("No payments found for user ID: " + userId);
        }
        return payments;
    }

    public List<Payment> getPaymentsByBookingId(String bookingId) {
        List<Payment> payments = paymentRepository.findByBookingId(bookingId);
        if (payments == null || payments.isEmpty()) {
            throw new PaymentNotFoundException("No payments found for booking ID: " + bookingId);
        }
        return payments;
    }
}
