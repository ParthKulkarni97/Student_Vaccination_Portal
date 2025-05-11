package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationRecord;
import lombok.Data;

@Data
public class VaccinationReportDTO {
    private String studentId;
    private String studentName;
    private String className;
    private String section;
    private String vaccineName;
    private String driveDate;
    private String status;
    private String dateAdministered;

    public VaccinationReportDTO(Student s, VaccinationRecord v, VaccinationDrive d) {
        this.studentId = s.getStudentId();
        this.studentName = s.getName();
        this.className = s.getClassName();
        this.section = s.getSection();
        this.vaccineName = v.getVaccineName();
        this.status = v.getStatus();
        this.dateAdministered = v.getDateAdministered() != null ? v.getDateAdministered().toString() : "";
        this.driveDate = d != null && d.getDriveDate() != null ? d.getDriveDate().toString() : "";
    }
}
