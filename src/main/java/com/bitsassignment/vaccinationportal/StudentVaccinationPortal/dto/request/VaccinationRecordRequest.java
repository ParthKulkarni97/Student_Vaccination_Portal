package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

// dto/request/VaccinationRecordRequest.java
@Data
@Builder
public class VaccinationRecordRequest {
    @NotBlank(message = "Drive ID is required")
    private String driveId;

    @NotBlank(message = "Batch number is required")
    private String batchNumber;

    private String notes;
}
