package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.controller;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request.VaccinationDriveRequest;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response.ApiResponse;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response.VaccinationDriveResponse;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service.VaccinationDriveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller/VaccinationDriveController.java
@RestController
@RequestMapping("/vaccination-drives")
@Slf4j
@RequiredArgsConstructor
public class VaccinationDriveController {
    private final VaccinationDriveService driveService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR')")
    public ResponseEntity<ApiResponse<VaccinationDriveResponse>> createDrive(
            @Valid @RequestBody VaccinationDriveRequest request) {
        log.info("Creating new vaccination drive: {}", request.getVaccineName());
        VaccinationDriveResponse response = driveService.createDrive(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{driveId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR')")
    public ResponseEntity<ApiResponse<VaccinationDriveResponse>> updateDrive(
            @PathVariable String driveId,
            @Valid @RequestBody VaccinationDriveRequest request) {
        log.info("Updating vaccination drive: {}", driveId);
        VaccinationDriveResponse response = driveService.updateDrive(driveId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/upcoming")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('NURSE')")
    public ResponseEntity<ApiResponse<List<VaccinationDriveResponse>>> getUpcomingDrives() {
        log.info("Fetching upcoming vaccination drives");
        List<VaccinationDriveResponse> response = driveService.getUpcomingDrives();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
