package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service;


import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public Page<Student> getAllStudents(String searchTerm, Pageable pageable) {
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return studentRepository.findByNameContainingIgnoreCase(searchTerm, pageable);
        }
        return studentRepository.findAll(pageable);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
}

