package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.*;

import java.time.LocalDateTime;

// model/BaseEntity.java
@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;
}
