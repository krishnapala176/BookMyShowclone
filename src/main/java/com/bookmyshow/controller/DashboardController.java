package com.bookmyshow.controller;

import com.bookmyshow.model.Show;
import com.bookmyshow.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // ✅ Customer Dashboard: Bookings & Payments Summary
    @GetMapping("/customer/{userId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Map<String, Object> getCustomerDashboard(@PathVariable String userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> dashboard = dashboardService.getCustomerDashboard(userId);
            response.put("status", "success");
            response.put("message", "Customer dashboard fetched successfully");
            response.put("data", dashboard);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch customer dashboard");
        }
        return response;
    }

    // ✅ Theatre Dashboard: Summary (revenue, bookings, etc.)
    @GetMapping("/theatre/{theatreId}/summary")
    @PreAuthorize("hasRole('THEATRE')")
    public Map<String, Object> getTheatreDashboard(@PathVariable String theatreId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> dashboard = dashboardService.getTheatreDashboard(theatreId);
            response.put("status", "success");
            response.put("message", "Theatre dashboard summary fetched successfully");
            response.put("data", dashboard);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch theatre dashboard summary");
        }
        return response;
    }

    // ✅ Theatre Dashboard: Shows with Bookings
    @GetMapping("/theatre/{theatreId}/shows")
    @PreAuthorize("hasRole('THEATRE')")
    public Map<String, Object> getTheatreShowsWithBookings(@PathVariable String theatreId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Show> shows = dashboardService.getTheatreShowsWithBookings(theatreId);
            response.put("status", "success");
            response.put("message", "Theatre shows fetched successfully");
            response.put("data", shows);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch theatre shows");
        }
        return response;
    }

    // ✅ Admin Dashboard
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> getAdminDashboard() {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> dashboard = dashboardService.getAdminDashboard();
            response.put("status", "success");
            response.put("message", "Admin dashboard fetched successfully");
            response.put("data", dashboard);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch admin dashboard");
        }
        return response;
    }
}
