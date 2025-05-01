package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// repository/StudentRepository.java
@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByStudentId(String studentId);
    List<Student> findByClassName(String className);
    boolean existsByStudentId(String studentId);

    @Query("{'vaccinations.vaccineName': ?0}")
    List<Student> findByVaccineName(String vaccineName);

    @Query("{'className': ?0, 'vaccinations.driveId': ?1}")
    List<Student> findByClassNameAndDriveId(String className, String driveId);

    @Aggregation(pipeline = {
            "{ $match: { 'vaccinations': { $exists: true, $ne: [] } } }",
            "{ $group: { _id: '$className', count: { $sum: 1 } } }"
    })
    List<ClassVaccinationCount> getVaccinationCountByClass();
}

