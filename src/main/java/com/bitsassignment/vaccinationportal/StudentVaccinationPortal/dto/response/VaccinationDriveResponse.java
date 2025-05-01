package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.enums.DriveStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

// dto/response/VaccinationDriveResponse.java
@Data
@Builder
public class VaccinationDriveResponse {
    private String id;
    private String vaccineName;
    private LocalDate driveDate;
    private int availableDoses;
    private int administeredDoses;
    private List<String> applicableClasses;
    private DriveStatus status;
    private String venue;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
