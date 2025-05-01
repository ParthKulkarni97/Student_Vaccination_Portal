package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// UserRepository.java
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
}
