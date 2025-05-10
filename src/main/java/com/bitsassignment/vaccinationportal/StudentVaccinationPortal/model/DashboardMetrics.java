package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model;

import lombok.Data;

import java.util.List;

@Data
public class DashboardMetrics {
    private long totalStudents;
    private long vaccinatedStudents;
    private long upcomingDrives;
    private long completedDrives;
    private List<UpcomingDrive> upcomingDrivesList;
}
