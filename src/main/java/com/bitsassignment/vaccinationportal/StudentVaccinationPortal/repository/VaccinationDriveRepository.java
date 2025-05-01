package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// VaccinationDriveRepository.java
@Repository
public interface VaccinationDriveRepository extends MongoRepository<VaccinationDrive, String> {
    List<VaccinationDrive> findByDriveDateGreaterThanAndDriveDateLessThanEqual(
            LocalDate startDate, LocalDate endDate);
    List<VaccinationDrive> findByStatus(String status);
}
