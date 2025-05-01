package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.mapper;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request.StudentRequest;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response.StudentResponse;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import org.mapstruct.Mapper;

import java.util.List;

// mapper/StudentMapper.java
@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentRequest request);
    StudentResponse toResponse(Student student);
    List<StudentResponse> toResponseList(List<Student> students);
}
