package com.bookmyshow.service;

import com.bookmyshow.exception.Email.EmailSendFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String toEmail, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Welcome to BookMyShow 🎉");
            message.setText("Hi " + userName + ",\n\nThank you for registering on BookMyShow!");
            mailSender.send(message);
        } catch (MailException ex) {
            throw new EmailSendFailureException("Failed to send registration email to " + toEmail, ex);
        }
    }

    public void sendBookingConfirmationEmail(String toEmail, String userName, String movieTitle,
                                             String theatreName, String showTime, String bookingId,
                                             List<String> seats) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("🎟️ Booking Confirmed: " + movieTitle);
            message.setText("Hi " + userName + ",\n\nYour booking is confirmed!\n\n"
                    + "🎬 Movie: " + movieTitle
                    + "\n🏢 Theatre: " + theatreName
                    + "\n🕒 Show Time: " + showTime
                    + "\n💺 Seats: " + String.join(", ", seats)
                    + "\n🆔 Booking ID: " + bookingId
                    + "\n\nThank you for booking with BookMyShow!");
            mailSender.send(message);
        } catch (MailException ex) {
            throw new EmailSendFailureException("Failed to send booking confirmation email to " + toEmail, ex);
        }
    }
}
