package com.bookmyshow.service;

import com.bookmyshow.exception.Theatre.TheatreNotFoundException;
import com.bookmyshow.model.Theatre;
import com.bookmyshow.model.TheatreStatus;
import com.bookmyshow.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    public Theatre registerTheatre(Theatre theatre) {
        theatre.setStatus(TheatreStatus.PENDING);
        return theatreRepository.save(theatre);
    }

    public Theatre approveTheatre(String id) {
        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new TheatreNotFoundException(id));

        theatre.setStatus(TheatreStatus.APPROVED);
        return theatreRepository.save(theatre);
    }

    public Theatre rejectTheatre(String id) {
        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new TheatreNotFoundException(id));

        theatre.setStatus(TheatreStatus.REJECTED);
        return theatreRepository.save(theatre);
    }

    public List<Theatre> getPendingTheatres() {
        return theatreRepository.findByStatus(TheatreStatus.PENDING);
    }

    public List<Theatre> getApprovedTheatres() {
        return theatreRepository.findByStatus(TheatreStatus.APPROVED);
    }

    public Optional<Theatre> getTheatreById(String id) {
        return theatreRepository.findById(id);
    }

    public Map<String, Long> getTheatreStatusCounts() {
        Map<String, Long> map = new HashMap<>();
        map.put("pending", theatreRepository.countByStatus(TheatreStatus.PENDING));
        map.put("approved", theatreRepository.countByStatus(TheatreStatus.APPROVED));
        map.put("rejected", theatreRepository.countByStatus(TheatreStatus.REJECTED));
        return map;
    }

    public String getTheatreNameById(String theatreId) {
        return theatreRepository.findById(theatreId)
                .map(Theatre::getName)
                .orElse("Unknown");
    }
}
