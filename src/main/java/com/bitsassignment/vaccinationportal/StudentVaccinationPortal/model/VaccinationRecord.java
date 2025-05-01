package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

// model/VaccinationRecord.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationRecord {
    private String driveId;
    private String vaccineName;
    private LocalDate vaccinationDate;
    private String batchNumber;
    private String administeredBy;
    private String notes;
}
