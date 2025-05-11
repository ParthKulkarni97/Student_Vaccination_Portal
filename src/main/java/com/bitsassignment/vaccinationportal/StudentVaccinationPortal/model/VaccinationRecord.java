package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VaccinationRecord {
    private String driveId; // Link to VaccinationDrive
    private String vaccineName;
    private LocalDate dateAdministered;
    private String status; // e.g., "Administered", "Missed"
}
