package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.controller;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service.VaccinationDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// VaccinationDriveController.java
@RestController
@RequestMapping("/api/drives")
public class VaccinationDriveController {
    @Autowired
    private VaccinationDriveService driveService;

    @PostMapping
    public ResponseEntity<VaccinationDrive> createDrive(@RequestBody VaccinationDrive drive) {
        return ResponseEntity.ok(driveService.createDrive(drive));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<VaccinationDrive>> getUpcomingDrives() {
        return ResponseEntity.ok(driveService.getUpcomingDrives());
    }
}
