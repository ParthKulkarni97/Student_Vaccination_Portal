package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.controller;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationRecord;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.StudentRepository;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.VaccinationDriveRepository;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private VaccinationDriveRepository driveRepository;

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

    @PostMapping("/students/{studentId}/vaccinate")
    public ResponseEntity<?> vaccinateStudent(
            @PathVariable String studentId,
            @RequestBody VaccinationRecord record
    ) {
        Student student = studentRepository.findByStudentId(studentId);
        if (student == null) return ResponseEntity.notFound().build();

        // Prevent duplicate vaccination for same drive/vaccine
        boolean alreadyVaccinated = student.getVaccinations().stream()
                .anyMatch(v -> v.getDriveId().equals(record.getDriveId()) && v.getVaccineName().equals(record.getVaccineName()));
        if (alreadyVaccinated) {
            return ResponseEntity.badRequest().body("Student already vaccinated for this vaccine in this drive.");
        }

        student.getVaccinations().add(record);
        studentRepository.save(student);
        return ResponseEntity.ok(student);
    }


}
