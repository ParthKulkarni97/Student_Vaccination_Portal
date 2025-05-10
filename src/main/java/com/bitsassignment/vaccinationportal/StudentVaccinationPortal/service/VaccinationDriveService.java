package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service;


import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.VaccinationDriveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VaccinationDriveService {
    private final VaccinationDriveRepository driveRepository;

    public List<VaccinationDrive> getUpcomingDrives() {
        LocalDate today = LocalDate.now();
        return driveRepository.findByDriveDateGreaterThanAndDriveDateLessThanEqual(
                today, today.plusDays(30));
    }

    public VaccinationDrive createDrive(VaccinationDrive drive) {
        return driveRepository.save(drive);
    }
}
