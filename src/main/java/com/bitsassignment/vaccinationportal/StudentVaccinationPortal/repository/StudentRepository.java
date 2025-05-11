package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {
    // Find by studentId (for marking vaccination)
    Student findByStudentId(String studentId);

    // Search by name (case-insensitive, paginated)
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Filter by className and/or section (paginated)
    Page<Student> findByClassNameAndSection(String className, String section, Pageable pageable);

    // Count by vaccinated status (for dashboard)
    long countByVaccinated(boolean vaccinated);

    // Find all students in a class (for drive eligibility)
    List<Student> findByClassName(String className);

    // Find all students in a class and section
    List<Student> findByClassNameAndSection(String className, String section);
}