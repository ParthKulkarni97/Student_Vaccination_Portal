package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;


import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

// model/Student.java
@Document(collection = "students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseEntity {
    private String name;
    private String studentId;
    private String className;
    private String dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String parentName;
    private String contactNumber;
    private String address;
    private List<VaccinationRecord> vaccinations;
    private boolean isActive;
}
