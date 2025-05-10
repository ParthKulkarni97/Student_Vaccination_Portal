package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;


import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "students")
public class Student {
    @Id
    private String id;
    private String studentId;
    private String name;
    private String className;
    private String section;
    private LocalDate dateOfBirth;
    private List<Vaccination> vaccinations;
    private boolean vaccinated;
}
