package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// StudentRepository.java
@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Student> findByClassName(String className);
    Optional<Student> findByStudentId(String studentId);
}

