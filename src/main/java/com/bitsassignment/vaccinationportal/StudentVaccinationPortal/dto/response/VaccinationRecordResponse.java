package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

// dto/response/VaccinationRecordResponse.java
@Data
@Builder
public class VaccinationRecordResponse {
    private String driveId;
    private String vaccineName;
    private LocalDate vaccinationDate;
    private String batchNumber;
    private String administeredBy;
    private String notes;
}
