package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpcomingDrive {
    private String vaccineName;
    private LocalDate driveDate; // instead of Date
    private int availableDoses;
}
