package com.bookmyshow.service;

import com.bookmyshow.exception.Show.ShowNotFoundException;
import com.bookmyshow.exception.Common.UnauthorizedActionException;
import com.bookmyshow.exception.user.UserNotFoundException;
import com.bookmyshow.model.Show;
import com.bookmyshow.model.User;
import com.bookmyshow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserService userService;

    public Show addShow(Show show, String email) {
        Optional<User> userOptional = userService.getUserByEmail(email.trim());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found for email: " + email);
        }

        User user = userOptional.get();

        if (!"THEATRE".equalsIgnoreCase(user.getRole())) {
            throw new UnauthorizedActionException("Only theatre users can create shows.");
        }

        show.setTheatreId(user.getId());
        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> getShowsByMovieId(String movieId) {
        List<Show> shows = showRepository.findByMovieId(movieId);
        if (shows == null || shows.isEmpty()) {
            throw new ShowNotFoundException("No shows found for movie ID: " + movieId);
        }
        return shows;
    }
}
