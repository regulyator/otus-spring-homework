package ru.otus.icdimporter.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.icdimporter.model.IcdEntry;
import ru.otus.icdimporter.model.Tree;
import ru.otus.icdimporter.tasklets.IcdWriter;
import ru.otus.icdimporter.tasklets.IcdXmlSourceReader;

@Configuration
public class TaskletConfiguration {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public TaskletConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    protected Step readIcdSource() {
        return stepBuilderFactory
                .get("readInputIcdXml")
                .tasklet(new IcdXmlSourceReader())
                .build();
    }

    @Bean
    protected Step writeIcd() {
        return stepBuilderFactory
                .get("writeIcd")
                .tasklet(new IcdWriter())
                .build();
    }

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory
                .get("taskletsJob")
                .start(readIcdSource())
                .next(writeIcd())
                .build();
    }
}
