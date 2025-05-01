package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

// dto/request/UserUpdateRequest.java
@Data
@Builder
public class UserUpdateRequest {
    @Email(message = "Invalid email format")
    private String email;

    private String fullName;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}
