package com.bookmyshow.controller;

import com.bookmyshow.model.Show;
import com.bookmyshow.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.security.Principal;


@RestController
@RequestMapping("/api/shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    // 🎬 Only users with role THEATRE can create a new show
    @PostMapping
    @PreAuthorize("hasRole('THEATRE')")
    public Show createShow(@RequestBody Show show, Authentication authentication) {
        String email = authentication.getName();  // this works now!
        return showService.addShow(show, email);
    }


    // 🍿 Public: Get all shows
    @GetMapping
    public List<Show> getAllShows() {
        return showService.getAllShows();
    }

    // 🔍 Public: Get shows by movie ID
    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsByMovie(@PathVariable String movieId) {
        return showService.getShowsByMovieId(movieId);
    }
}
