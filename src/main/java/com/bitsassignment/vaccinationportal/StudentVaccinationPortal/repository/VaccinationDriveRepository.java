package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.enums.DriveStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// repository/VaccinationDriveRepository.java
@Repository
public interface VaccinationDriveRepository extends MongoRepository<VaccinationDrive, String> {
    List<VaccinationDrive> findByDriveDateGreaterThanEqual(LocalDate date);
    List<VaccinationDrive> findByStatus(DriveStatus status);

    @Query("{'driveDate': {$gte: ?0, $lte: ?1}}")
    List<VaccinationDrive> findDrivesBetweenDates(LocalDate startDate, LocalDate endDate);

    @Query("{'driveDate': {$gte: ?0}, 'status': 'SCHEDULED'}")
    List<VaccinationDrive> findUpcomingDrives(LocalDate currentDate, Pageable pageable);

    boolean existsByDriveDateAndVaccineName(LocalDate driveDate, String vaccineName);
}
