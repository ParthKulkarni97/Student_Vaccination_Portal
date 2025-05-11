package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.controller;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/download")
    public void downloadReport(@RequestParam String format, HttpServletResponse response) throws IOException {
        if ("excel".equalsIgnoreCase(format)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=report.xlsx");
            reportService.exportToExcel(response.getOutputStream());
        } else if ("pdf".equalsIgnoreCase(format)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
            reportService.exportToPdf(response.getOutputStream());
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid format");
        }
    }
}