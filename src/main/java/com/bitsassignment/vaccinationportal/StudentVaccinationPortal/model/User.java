package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.enums.UserRole;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

// model/User.java
@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private UserRole role;
    private boolean isActive;
}
