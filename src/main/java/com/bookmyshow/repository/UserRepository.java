package com.bookmyshow.repository;

import com.bookmyshow.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String>
{
    Optional<User> findByEmail(String email);
    List<User> findByRole(String role);
}
