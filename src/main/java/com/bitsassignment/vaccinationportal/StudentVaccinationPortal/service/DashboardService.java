package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.DashboardMetrics;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.UpcomingDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.StudentRepository;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.VaccinationDriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final StudentRepository studentRepository;
    private final VaccinationDriveRepository driveRepository;

    @Autowired
    public DashboardService(StudentRepository studentRepository,
                            VaccinationDriveRepository driveRepository) {
        this.studentRepository = studentRepository;
        this.driveRepository = driveRepository;
    }

    public DashboardMetrics getDashboardMetrics() {
        try {
            DashboardMetrics metrics = new DashboardMetrics();

            // Get total students and vaccinated count
            long totalStudents = studentRepository.count();
            long vaccinatedStudents = studentRepository.countByVaccinated(true);

            // Get drives counts
            long upcomingDrivesCount = driveRepository.countByStatus("UPCOMING");
            long completedDrivesCount = driveRepository.countByStatus("COMPLETED");

            LocalDate today = LocalDate.now();
            List<VaccinationDrive> upcomingDrivesList = driveRepository.findByDriveDateAfterAndStatusOrderByDriveDate(today, "UPCOMING");

            List<UpcomingDrive> upcomingDrivesDTO = upcomingDrivesList.stream()
                    .map(this::convertToUpcomingDriveDTO)
                    .collect(Collectors.toList());

// Set all metrics
            metrics.setTotalStudents(totalStudents);
            metrics.setVaccinatedStudents(vaccinatedStudents);
            metrics.setUpcomingDrives(upcomingDrivesCount);
            metrics.setCompletedDrives(completedDrivesCount);
            metrics.setUpcomingDrivesList(upcomingDrivesDTO);

            return metrics;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching dashboard metrics: " + e.getMessage(), e);
        }
    }

    private UpcomingDrive convertToUpcomingDriveDTO(VaccinationDrive drive) {
        UpcomingDrive dto = new UpcomingDrive();
        dto.setVaccineName(drive.getVaccineName());
        dto.setDriveDate(drive.getDriveDate());
        dto.setAvailableDoses(drive.getAvailableDoses());
        return dto;
    }
}