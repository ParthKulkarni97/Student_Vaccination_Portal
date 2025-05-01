package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service;


import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.VaccinationDriveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// VaccinationDriveService.java
@Service
@Slf4j
public class VaccinationDriveService {
    @Autowired
    private VaccinationDriveRepository driveRepository;

    public VaccinationDrive createDrive(VaccinationDrive drive) {
        validateDrive(drive);
        drive.setCreatedAt(LocalDateTime.now());
        drive.setUpdatedAt(LocalDateTime.now());
        drive.setStatus("SCHEDULED");
        return driveRepository.save(drive);
    }

    public List<VaccinationDrive> getUpcomingDrives() {
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);
        return driveRepository.findByDriveDateGreaterThanAndDriveDateLessThanEqual(
                today, thirtyDaysLater);
    }

    private void validateDrive(VaccinationDrive drive) {
        if (drive.getDriveDate().isBefore(LocalDate.now().plusDays(15))) {
            throw new IllegalArgumentException("Drive must be scheduled at least 15 days in advance");
        }
        // Add more validations...
    }
}
