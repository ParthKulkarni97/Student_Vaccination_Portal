package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.service;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Vaccination;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationRecord;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.repository.StudentRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final StudentRepository studentRepository;

    // ... imports and @Service annotation

    public void exportToExcel(OutputStream os) throws IOException {
        List<Student> students = studentRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Vaccination Report");

        // Header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Student ID");
        header.createCell(1).setCellValue("Student Name");
        header.createCell(2).setCellValue("Class");
        header.createCell(3).setCellValue("Section");
        header.createCell(4).setCellValue("Vaccine Name");
        header.createCell(5).setCellValue("Date Administered");
        header.createCell(6).setCellValue("Status");

        int rowIdx = 1;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Student s : students) {
            if (s.getVaccinations() != null && !s.getVaccinations().isEmpty()) {
                for (VaccinationRecord v : s.getVaccinations()) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(s.getStudentId());
                    row.createCell(1).setCellValue(s.getName());
                    row.createCell(2).setCellValue(s.getClassName());
                    row.createCell(3).setCellValue(s.getSection());
                    row.createCell(4).setCellValue(v.getVaccineName());
                    row.createCell(5).setCellValue(v.getDateAdministered() != null ? v.getDateAdministered().format(dtf) : "");
                    row.createCell(6).setCellValue(v.getStatus());
                }
            } else {
                // Student with no vaccinations
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(s.getStudentId());
                row.createCell(1).setCellValue(s.getName());
                row.createCell(2).setCellValue(s.getClassName());
                row.createCell(3).setCellValue(s.getSection());
                row.createCell(4).setCellValue("");
                row.createCell(5).setCellValue("");
                row.createCell(6).setCellValue("");
            }
        }

        workbook.write(os);
        workbook.close();
    }

    public void exportToPdf(OutputStream os) throws IOException {
        List<Student> students = studentRepository.findAll();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, os);
            document.open();

            document.add(new Paragraph("Vaccination Report"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.addCell("Student ID");
            table.addCell("Student Name");
            table.addCell("Class");
            table.addCell("Section");
            table.addCell("Vaccine Name");
            table.addCell("Date Administered");
            table.addCell("Status");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (Student s : students) {
                if (s.getVaccinations() != null && !s.getVaccinations().isEmpty()) {
                    for (VaccinationRecord v : s.getVaccinations()) {
                        table.addCell(s.getStudentId());
                        table.addCell(s.getName());
                        table.addCell(s.getClassName());
                        table.addCell(s.getSection());
                        table.addCell(v.getVaccineName());
                        table.addCell(v.getDateAdministered() != null ? v.getDateAdministered().format(dtf) : "");
                        table.addCell(v.getStatus());
                    }
                } else {
                    table.addCell(s.getStudentId());
                    table.addCell(s.getName());
                    table.addCell(s.getClassName());
                    table.addCell(s.getSection());
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                }
            }

            document.add(table);
        } finally {
            document.close();
        }
    }
}