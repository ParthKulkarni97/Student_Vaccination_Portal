package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request.VaccinationDriveRequest;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response.VaccinationDriveResponse;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.mapper.VaccinationDriveMapper;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.enums.DriveStatus;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.VaccinationDriveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

// service/VaccinationDriveService.java
@Service
@Slf4j
@RequiredArgsConstructor
public class VaccinationDriveService {
    private final VaccinationDriveRepository driveRepository;
    private final VaccinationDriveMapper driveMapper;

    public VaccinationDriveResponse createDrive(VaccinationDriveRequest request) {
        log.info("Creating new vaccination drive for: {}", request.getVaccineName());

        validateDriveSchedule(request);

        VaccinationDrive drive = driveMapper.toEntity(request);
        drive.setStatus(DriveStatus.SCHEDULED);
        drive.setAdministeredDoses(0);

        VaccinationDrive savedDrive = driveRepository.save(drive);
        return driveMapper.toResponse(savedDrive);
    }

    public VaccinationDriveResponse updateDrive(String driveId, VaccinationDriveRequest request) {
        log.info("Updating vaccination drive: {}", driveId);

        VaccinationDrive existingDrive = getDriveById(driveId);

        if (existingDrive.getStatus() == DriveStatus.COMPLETED) {
            throw new InvalidOperationException("Cannot update completed drive");
        }

        validateDriveSchedule(request);

        VaccinationDrive updatedDrive = driveMapper.toEntity(request);
        updatedDrive.setId(driveId);
        updatedDrive.setStatus(existingDrive.getStatus());
        updatedDrive.setAdministeredDoses(existingDrive.getAdministeredDoses());

        return driveMapper.toResponse(driveRepository.save(updatedDrive));
    }

    public List<VaccinationDriveResponse> getUpcomingDrives() {
        log.info("Fetching upcoming vaccination drives");

        List<VaccinationDrive> drives = driveRepository.findUpcomingDrives(
                LocalDate.now(),
                PageRequest.of(0, 10, Sort.by("driveDate").ascending())
        );

        return driveMapper.toResponseList(drives);
    }

    @Transactional
    public void incrementAdministeredDoses(String driveId) {
        VaccinationDrive drive = getDriveById(driveId);

        if (drive.getAdministeredDoses() >= drive.getAvailableDoses()) {
            throw new InvalidOperationException("No more doses available");
        }

        drive.setAdministeredDoses(drive.getAdministeredDoses() + 1);

        if (drive.getAdministeredDoses() == drive.getAvailableDoses()) {
            drive.setStatus(DriveStatus.COMPLETED);
        }

        driveRepository.save(drive);
    }

    private void validateDriveSchedule(VaccinationDriveRequest request) {
        // Validate 15 days advance booking
        if (request.getDriveDate().isBefore(LocalDate.now().plusDays(15))) {
            throw new ValidationException("Drive must be scheduled at least 15 days in advance");
        }

        // Check for overlapping drives
        if (driveRepository.existsByDriveDateAndVaccineName(
                request.getDriveDate(), request.getVaccineName())) {
            throw new ValidationException("A drive for this vaccine already exists on the selected date");
        }
    }

    public VaccinationDrive getDriveById(String driveId) {
        return driveRepository.findById(driveId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccination drive not found"));
    }
}
