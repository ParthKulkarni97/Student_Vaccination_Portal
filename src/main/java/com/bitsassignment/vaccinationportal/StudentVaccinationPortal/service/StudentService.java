package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service;


import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Vaccination;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.StudentRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// StudentService.java
@Service
@Slf4j
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public Page<Student> getAllStudents(String searchTerm, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return (Page<Student>) studentRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
        }
        return studentRepository.findAll(pageable);
    }

    public Student createStudent(Student student) {
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        return studentRepository.save(student);
    }

    public List<Student> bulkUpload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        // Verify file type
        if (!Objects.equals(file.getContentType(), "text/csv")) {
            throw new IllegalArgumentException("Please upload a CSV file");
        }

        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVReader csvReader = new CSVReader(reader)) {

            // Skip the header row
            String[] headers = csvReader.readNext();
            if (headers == null) {
                throw new IllegalArgumentException("CSV file is empty");
            }

            // Read data rows
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                Student student = parseStudentFromCSV(line);
                students.add(student);
            }

            return studentRepository.saveAll(students);

        } catch (IOException e) {
            log.error("Failed to read CSV file", e);
            throw new RuntimeException("Failed to read CSV file: " + e.getMessage());
        } catch (CsvException e) {
            log.error("Failed to parse CSV file", e);
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }

    private Student parseStudentFromCSV(String[] line) {
        try {
            Student student = new Student();

            // Assuming CSV columns are in order: studentId,name,className,section,dateOfBirth
            student.setStudentId(line[0].trim());
            student.setName(line[1].trim());
            student.setClassName(line[2].trim());
            student.setSection(line[3].trim());

            // Parse date of birth
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            student.setDateOfBirth(LocalDate.parse(line[4].trim(), formatter));

            // Initialize empty vaccination list
            student.setVaccinations(new ArrayList<>());

            // Set creation and update timestamps
            LocalDateTime now = LocalDateTime.now();
            student.setCreatedAt(now);
            student.setUpdatedAt(now);

            return student;
        } catch (Exception e) {
            log.error("Error parsing CSV line", e);
            throw new RuntimeException("Error parsing CSV line: " + e.getMessage());
        }
    }

    // Other methods...
}
