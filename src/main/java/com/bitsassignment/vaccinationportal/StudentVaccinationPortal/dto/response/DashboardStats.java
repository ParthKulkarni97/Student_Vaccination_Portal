package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

// dto/response/DashboardStats.java
@Data
@Builder
public class DashboardStats {
    private long totalStudents;
    private long vaccinatedStudents;
    private double vaccinationRate;
    private long upcomingDrives;
    private long completedDrives;
    private Map<String, Long> vaccinationsByClass;
    private List<VaccinationDriveResponse> nextThreeDrives;
}
