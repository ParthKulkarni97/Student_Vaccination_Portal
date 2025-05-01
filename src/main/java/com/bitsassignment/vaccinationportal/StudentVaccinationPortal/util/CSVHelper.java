package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.util;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.dto.request.StudentRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// util/CSVHelper.java
@Slf4j
public class CSVHelper {

    public static List<StudentRequest> parseStudentsFromCsv(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            List<StudentRequest> students = new ArrayList<>();

            for (CSVRecord csvRecord : csvParser) {
                StudentRequest student = StudentRequest.builder()
                        .name(csvRecord.get("Name"))
                        .studentId(csvRecord.get("StudentId"))
                        .className(csvRecord.get("Class"))
                        .dateOfBirth(csvRecord.get("DateOfBirth"))
                        .gender(csvRecord.get("Gender"))
                        .bloodGroup(csvRecord.get("BloodGroup"))
                        .parentName(csvRecord.get("ParentName"))
                        .contactNumber(csvRecord.get("ContactNumber"))
                        .address(csvRecord.get("Address"))
                        .build();

                students.add(student);
            }

            return students;
        } catch (IOException e) {
            log.error("Failed to parse CSV file: {}", e.getMessage());
            throw new FileProcessingException("Failed to parse CSV file: " + e.getMessage());
        }
    }

    public static byte[] generateCsvReport(List<VaccinationReportData> reportData) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out),
                     CSVFormat.DEFAULT.withHeader("Student ID", "Name", "Class",
                             "Vaccine Name", "Vaccination Date", "Batch Number"))) {

            for (VaccinationReportData data : reportData) {
                csvPrinter.printRecord(
                        data.getStudentId(),
                        data.getStudentName(),
                        data.getClassName(),
                        data.getVaccineName(),
                        data.getVaccinationDate(),
                        data.getBatchNumber()
                );
            }

            csvPrinter.flush();
            return out.toByteArray();
        } catch (IOException e) {
            log.error("Failed to generate CSV report: {}", e.getMessage());
            throw new FileProcessingException("Failed to generate CSV report: " + e.getMessage());
        }
    }
}
