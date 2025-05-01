package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.controller;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request.StudentRequest;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request.VaccinationRecordRequest;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response.ApiResponse;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response.StudentResponse;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// controller/StudentController.java
@RestController
@RequestMapping("/students")
@Slf4j
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR')")
    public ResponseEntity<ApiResponse<StudentResponse>> createStudent(
            @Valid @RequestBody StudentRequest request) {
        log.info("Creating new student: {}", request.getName());
        StudentResponse response = studentService.createStudent(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR')")
    public ResponseEntity<ApiResponse<List<StudentResponse>>> bulkImport(
            @RequestParam("file") MultipartFile file) {
        log.info("Processing bulk import");
        List<StudentResponse> response = studentService.bulkImportStudents(file);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{studentId}/vaccination")
    @PreAuthorize("hasRole('NURSE')")
    public ResponseEntity<ApiResponse<StudentResponse>> updateVaccinationStatus(
            @PathVariable String studentId,
            @Valid @RequestBody VaccinationRecordRequest request) {
        log.info("Updating vaccination status for student: {}", studentId);
        StudentResponse response = studentService.updateVaccinationStatus(studentId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDINATOR') or hasRole('NURSE')")
    public ResponseEntity<ApiResponse<Page<StudentResponse>>> searchStudents(
            StudentSearchCriteria criteria,
            @PageableDefault(size = 10) Pageable pageable) {
        log.info("Searching students with term: {}", criteria.getSearchTerm());
        Page<StudentResponse> response = studentService.searchStudents(criteria, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
