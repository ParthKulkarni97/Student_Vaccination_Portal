package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface VaccinationDriveRepository extends MongoRepository<VaccinationDrive, String> {
    // Find drives between dates
    List<VaccinationDrive> findByDriveDateBetween(LocalDate startDate, LocalDate endDate);

    // Count by status (for dashboard)
    long countByStatus(String status);

    // Find upcoming drives (by date and status)
    // Find drives for a specific date and any of the given classes
    List<VaccinationDrive> findByDriveDateAndApplicableClassesIn(LocalDate driveDate, List<String> applicableClasses);

    // Find drives between two dates (for upcoming drives)
    List<VaccinationDrive> findByDriveDateGreaterThanAndDriveDateLessThanEqual(LocalDate startDate, LocalDate endDate);


    List<VaccinationDrive> findByDriveDateAfterAndStatusOrderByDriveDate(LocalDate date, String status);
    // Find by driveId (default by MongoRepository)
    // Optional: List<VaccinationDrive> findById(String id);
}