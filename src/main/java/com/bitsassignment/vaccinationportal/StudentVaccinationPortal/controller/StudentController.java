package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.controller;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudents(
            @RequestParam(required = false) String searchTerm,
            Pageable pageable) {
        return ResponseEntity.ok(studentService.getAllStudents(searchTerm, pageable));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }
}
