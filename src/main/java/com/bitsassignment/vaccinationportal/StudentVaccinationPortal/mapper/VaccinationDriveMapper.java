package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.mapper;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request.VaccinationDriveRequest;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response.VaccinationDriveResponse;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import org.mapstruct.Mapper;

import java.util.List;

// mapper/VaccinationDriveMapper.java
@Mapper(componentModel = "spring")
public interface VaccinationDriveMapper {
    VaccinationDrive toEntity(VaccinationDriveRequest request);
    VaccinationDriveResponse toResponse(VaccinationDrive drive);
    List<VaccinationDriveResponse> toResponseList(List<VaccinationDrive> drives);
}
