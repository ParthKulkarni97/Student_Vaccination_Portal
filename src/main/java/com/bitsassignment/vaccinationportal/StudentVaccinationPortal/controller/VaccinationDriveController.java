package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.controller;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service.VaccinationDriveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drives")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class VaccinationDriveController {
    private final VaccinationDriveService driveService;

    @GetMapping("/upcoming")
    public ResponseEntity<List<VaccinationDrive>> getUpcomingDrives() {
        return ResponseEntity.ok(driveService.getUpcomingDrives());
    }

    @PostMapping
    public ResponseEntity<VaccinationDrive> createDrive(@RequestBody VaccinationDrive drive) {
        return ResponseEntity.ok(driveService.createDrive(drive));
    }
}
