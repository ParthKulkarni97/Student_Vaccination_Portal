package com.bitsassignment.vaccinationportal.StudentVaccinationPortal.config;

import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.Student;
import com.bitsassignment.vaccinationportal.StudentVaccinationPortal.model.VaccinationDrive;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.school.vaccination.repository")
@Slf4j
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        log.info("Configuring MongoDB with database: {}", databaseName);
        return MongoClients.create(mongoClientSettings);
    }

    @PostConstruct
    public void createIndexes() {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), getDatabaseName());

        // Student indexes
        mongoTemplate.indexOps(Student.class)
                .ensureIndex(new Index().on("studentId", Sort.Direction.ASC).unique());
        mongoTemplate.indexOps(Student.class)
                .ensureIndex(new Index().on("className", Sort.Direction.ASC));

        // VaccinationDrive indexes
        mongoTemplate.indexOps(VaccinationDrive.class)
                .ensureIndex(new Index().on("driveDate", Sort.Direction.ASC));

        log.info("MongoDB indexes created successfully");
    }
}
