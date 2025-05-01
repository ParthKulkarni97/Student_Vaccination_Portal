package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

// dto/request/VaccinationDriveRequest.java
@Data
@Builder
public class VaccinationDriveRequest {
    @NotBlank(message = "Vaccine name is required")
    private String vaccineName;

    @NotNull(message = "Drive date is required")
    @Future(message = "Drive date must be in the future")
    private LocalDate driveDate;

    @Min(value = 1, message = "Available doses must be at least 1")
    private int availableDoses;

    @NotEmpty(message = "Applicable classes must not be empty")
    private List<String> applicableClasses;

    private String venue;
    private String description;

    @Pattern(regexp = "\\d{2}:\\d{2}", message = "Time should be in HH:mm format")
    private String startTime;

    @Pattern(regexp = "\\d{2}:\\d{2}", message = "Time should be in HH:mm format")
    private String endTime;
}
