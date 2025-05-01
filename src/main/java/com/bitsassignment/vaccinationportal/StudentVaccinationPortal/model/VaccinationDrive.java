package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.enums.DriveStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// VaccinationDrive.java
@Data
@Document(collection = "vaccination_drives")
public class VaccinationDrive {
    @Id
    private String id;
    private String vaccineName;
    private LocalDate driveDate;
    private Integer availableDoses;
    private List<String> applicableClasses;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
