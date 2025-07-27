package com.bookmyshow.controller;

import com.bookmyshow.model.Theatre;
import com.bookmyshow.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private TheatreService theatreService;

    // ✅ Approve a theatre
    @PutMapping("/approve-theatre/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Theatre approveTheatre(@PathVariable String id) {
        return theatreService.approveTheatre(id);
    }

    // ✅ Reject a theatre
    @PutMapping("/reject-theatre/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Theatre rejectTheatre(@PathVariable String id) {
        return theatreService.rejectTheatre(id);
    }

    // ✅ Get all pending theatres
    @GetMapping("/pending-theatres")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Theatre> getPendingTheatres() {
        return theatreService.getPendingTheatres();
    }

    // ✅ Get summary counts of pending/approved/rejected
    @GetMapping("/theatre-status-counts")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Long> getTheatreStatusCounts() {
        return theatreService.getTheatreStatusCounts();
    }
}
