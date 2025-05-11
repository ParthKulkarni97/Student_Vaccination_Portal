package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "vaccination_drives")
public class VaccinationDrive {
    @Id
    private String id;
    private String vaccineName;
    private LocalDate driveDate;
    private int availableDoses;
    private String status; // UPCOMING, COMPLETED
    private List<String> applicableClasses; // e.g., ["5", "6", "7"]
}
