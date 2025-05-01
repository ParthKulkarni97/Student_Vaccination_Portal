package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// Vaccination.java
@Data
public class Vaccination {
    private String driveId;
    private String vaccineName;
    private LocalDate dateAdministered;
    private String status;
}
