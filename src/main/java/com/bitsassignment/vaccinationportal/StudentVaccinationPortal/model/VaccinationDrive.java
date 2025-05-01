package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.enums.DriveStatus;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

// model/VaccinationDrive.java
@Document(collection = "vaccination_drives")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VaccinationDrive extends BaseEntity {
    private String vaccineName;
    private LocalDate driveDate;
    private int availableDoses;
    private int administeredDoses;
    private List<String> applicableClasses;
    private DriveStatus status;
    private String venue;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
}
