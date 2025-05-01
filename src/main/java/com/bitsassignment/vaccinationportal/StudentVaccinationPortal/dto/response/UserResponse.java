package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.enums.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// dto/response/UserResponse.java
@Data
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String fullName;
    private UserRole role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
