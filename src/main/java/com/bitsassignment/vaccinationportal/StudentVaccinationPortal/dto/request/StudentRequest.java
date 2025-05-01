package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

// dto/request/StudentRequest.java
@Data
@Builder
public class StudentRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "Class is required")
    private String className;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date of birth should be in YYYY-MM-DD format")
    private String dateOfBirth;

    private String gender;
    private String bloodGroup;
    private String parentName;

    @Pattern(regexp = "\\d{10}", message = "Contact number should be 10 digits")
    private String contactNumber;

    private String address;
}
