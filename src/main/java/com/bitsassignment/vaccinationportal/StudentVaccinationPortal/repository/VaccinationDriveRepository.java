package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface VaccinationDriveRepository extends MongoRepository<VaccinationDrive, String> {
    List<VaccinationDrive> findByDriveDateGreaterThanAndDriveDateLessThanEqual(
            LocalDate startDate, LocalDate endDate);

    long countByStatus(String status);
    List<VaccinationDrive> findByDriveDateGreaterThanAndStatusOrderByDriveDate(Date date, String status);

}
