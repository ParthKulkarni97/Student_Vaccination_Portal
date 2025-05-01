package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.controller;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// StudentController.java
@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudents(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(studentService.getAllStudents(searchTerm, page, size));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Student>> bulkUpload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(studentService.bulkUpload(file));
    }
}
