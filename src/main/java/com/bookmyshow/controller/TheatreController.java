package com.bookmyshow.controller;

import com.bookmyshow.model.Theatre;
import com.bookmyshow.model.TheatreStatus;
import com.bookmyshow.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/theatre")
public class TheatreController {

    @Autowired
    private TheatreRepository theatreRepository;

    // ✅ Register a new theatre (status set to PENDING automatically)
    @PostMapping("/register")
    public Theatre registerTheatre(@RequestBody Theatre theatre) {
        theatre.setStatus(TheatreStatus.PENDING); // always enforce this
        return theatreRepository.save(theatre);
    }
}
