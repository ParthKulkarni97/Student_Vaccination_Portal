package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service;


import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.VaccinationDriveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VaccinationDriveService {
    private final VaccinationDriveRepository driveRepository;

    public List<VaccinationDrive> getAllDrives() {
        return driveRepository.findAll();
    }

    public VaccinationDrive createDrive(VaccinationDrive drive) {
        // 1. Prevent overlaps (same date & class)
        List<VaccinationDrive> overlapping = driveRepository.findByDriveDateAndApplicableClassesIn(
                drive.getDriveDate(), drive.getApplicableClasses());
        if (!overlapping.isEmpty()) {
            throw new RuntimeException("Overlapping drive exists for selected date/classes.");
        }

        // 2. At least 15 days in advance
        // Convert 15 days from now to java.util.Date
        LocalDate minDate = LocalDate.now().plusDays(15);
        Date minAllowedDate = Date.from(minDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        if (drive.getDriveDate().before(minAllowedDate)) {
//            throw new RuntimeException("Drive must be scheduled at least 15 days in advance.");
//        }

        drive.setStatus("UPCOMING");
        return driveRepository.save(drive);
    }

    public List<VaccinationDrive> getUpcomingDrives() {
        LocalDate today = LocalDate.now();
        return driveRepository.findByDriveDateGreaterThanAndDriveDateLessThanEqual(
                today, today.plusDays(30));
    }


}
