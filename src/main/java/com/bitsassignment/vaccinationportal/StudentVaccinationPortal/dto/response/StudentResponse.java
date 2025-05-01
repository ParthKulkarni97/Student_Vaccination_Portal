package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// dto/response/StudentResponse.java
@Data
@Builder
public class StudentResponse {
    private String id;
    private String name;
    private String studentId;
    private String className;
    private String dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String parentName;
    private String contactNumber;
    private String address;
    private List<VaccinationRecordResponse> vaccinations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
