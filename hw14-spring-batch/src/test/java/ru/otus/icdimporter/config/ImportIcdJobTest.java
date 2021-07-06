package ru.otus.icdimporter.config;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.otus.icdimporter.model.IcdEntry;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@SpringBootTest
@SpringBatchTest
class ImportIcdJobTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Test
    void testIcdEntryItemReader() throws Exception {
        ClassLoader classLoader = ImportIcdJobTest.class.getClassLoader();
        String testInputFileName = URLDecoder.decode(
                Objects.requireNonNull(classLoader.getResource("test-sourse-icd.xml")).getFile(),
                StandardCharsets.UTF_8
        );

        JobParameters parameters = new JobParametersBuilder()
                .addString("source", testInputFileName)
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

    }

}