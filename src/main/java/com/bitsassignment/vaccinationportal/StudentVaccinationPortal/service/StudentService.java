package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request.StudentRequest;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request.VaccinationRecordRequest;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response.StudentResponse;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.mapper.StudentMapper;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationRecord;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.StudentRepository;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.VaccinationDriveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// service/StudentService.java
@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final VaccinationDriveService driveService;

    public StudentResponse createStudent(StudentRequest request) {
        log.info("Creating new student with ID: {}", request.getStudentId());

        if (studentRepository.existsByStudentId(request.getStudentId())) {
            throw new DuplicateResourceException("Student ID already exists");
        }

        Student student = studentMapper.toEntity(request);
        student.setVaccinations(new ArrayList<>());
        student.setActive(true);

        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponse(savedStudent);
    }

    public List<StudentResponse> bulkImportStudents(MultipartFile file) {
        log.info("Processing bulk student import");

        try {
            List<StudentRequest> requests = CSVHelper.parseCsv(file.getInputStream(), StudentRequest.class);
            List<Student> students = new ArrayList<>();

            for (StudentRequest request : requests) {
                if (!studentRepository.existsByStudentId(request.getStudentId())) {
                    Student student = studentMapper.toEntity(request);
                    student.setVaccinations(new ArrayList<>());
                    student.setActive(true);
                    students.add(student);
                }
            }

            List<Student> savedStudents = studentRepository.saveAll(students);
            return studentMapper.toResponseList(savedStudents);
        } catch (IOException e) {
            throw new FileProcessingException("Failed to process CSV file", e);
        }
    }

    public StudentResponse updateVaccinationStatus(String studentId, VaccinationRecordRequest request) {
        log.info("Updating vaccination status for student: {}", studentId);

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        VaccinationDrive drive = driveService.getDriveById(request.getDriveId());

        // Validate if student already has this vaccination
        if (student.getVaccinations().stream()
                .anyMatch(v -> v.getVaccineName().equals(drive.getVaccineName()))) {
            throw new DuplicateVaccinationException("Student already has this vaccination");
        }

        VaccinationRecord record = VaccinationRecord.builder()
                .driveId(drive.getId())
                .vaccineName(drive.getVaccineName())
                .vaccinationDate(LocalDate.now())
                .batchNumber(request.getBatchNumber())
                .administeredBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .notes(request.getNotes())
                .build();

        student.getVaccinations().add(record);
        Student updatedStudent = studentRepository.save(student);

        // Update drive administered doses
        driveService.incrementAdministeredDoses(drive.getId());

        return studentMapper.toResponse(updatedStudent);
    }

    public Page<StudentResponse> searchStudents(StudentSearchCriteria criteria, Pageable pageable) {
        log.info("Searching students with criteria: {}", criteria);

        Query query = new Query().with(pageable);

        if (StringUtils.hasText(criteria.getSearchTerm())) {
            Criteria searchCriteria = new Criteria().orOperator(
                    Criteria.where("name").regex(criteria.getSearchTerm(), "i"),
                    Criteria.where("studentId").regex(criteria.getSearchTerm(), "i")
            );
            query.addCriteria(searchCriteria);
        }

        if (StringUtils.hasText(criteria.getClassName())) {
            query.addCriteria(Criteria.where("className").is(criteria.getClassName()));
        }

        if (criteria.getVaccinationStatus() != null) {
            if (criteria.getVaccinationStatus()) {
                query.addCriteria(Criteria.where("vaccinations").ne(null).not().size(0));
            } else {
                query.addCriteria(new Criteria().orOperator(
                        Criteria.where("vaccinations").is(null),
                        Criteria.where("vaccinations").size(0)
                ));
            }
        }

        return PageableExecutionUtils.getPage(
                mongoTemplate.find(query, Student.class),
                pageable,
                () -> mongoTemplate.count(query.skip(0).limit(0), Student.class)
        ).map(studentMapper::toResponse);
    }
}
