package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.controller;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.DashboardMetrics;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardMetrics> getDashboardMetrics() {
        try {
            DashboardMetrics metrics = dashboardService.getDashboardMetrics();
            return ResponseEntity.ok(metrics);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
