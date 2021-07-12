package ru.otus.icdimporter.config;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.otus.icdimporter.model.IcdEntry;
import ru.otus.icdimporter.model.domain.mongo.IcdDocument;
import ru.otus.icdimporter.service.CustomIcdMongoWriter;
import ru.otus.icdimporter.service.PrepareForJobService;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class JobConfiguration {

    public static final String SOURCE_FILE_JOB_PARAMETER = "source";
    private final PrepareForJobService prepareForJobService;

    public JobConfiguration(PrepareForJobService prepareForJobService) {
        this.prepareForJobService = prepareForJobService;
    }


    @Bean
    @StepScope
    public ItemStreamReader<IcdEntry> icdEntryXmlItemReader(@Value("#{jobParameters['" + SOURCE_FILE_JOB_PARAMETER + "']}") String inputSource) {
        Jaxb2Marshaller icdEntryMarshaller = new Jaxb2Marshaller();
        icdEntryMarshaller.setClassesToBeBound(IcdEntry.class);

        return new StaxEventItemReaderBuilder<IcdEntry>()
                .name("icdEntryReader")
                .resource(new FileSystemResource(inputSource))
                .addFragmentRootElements("entry")
                .unmarshaller(icdEntryMarshaller)
                .build();
    }


    @Bean
    public ItemWriter<IcdEntry> icdEntryJdbcBatchItemWriter(DataSource dataSource,
                                                            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new JdbcBatchItemWriterBuilder<IcdEntry>()
                .dataSource(dataSource)
                .namedParametersJdbcTemplate(namedParameterJdbcTemplate)
                .sql("INSERT into icd(id, rec_code, mkb_name, mkb_code, actual, id_parent) " +
                        "values(:id, :recCode, :mbkName, :mkbCode, :actual, :idParent)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    @Bean
    public ItemReader<IcdEntry> icdEntryJdbcItemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<IcdEntry>()
                .dataSource(dataSource)
                .name("icdEntryJdbcItemReader")
                .sql("SELECT id, rec_code as recCode, mkb_name as mbkName, mkb_code as mkbCode, actual as actual, id_parent as idParent " +
                        "from icd")
                .rowMapper(new BeanPropertyRowMapper<>(IcdEntry.class))
                .build();
    }

    @Bean
    public ItemWriter<IcdEntry> icdDocumentWriter(MongoOperations mongoOperations,
                                                  NamedParameterJdbcOperations namedParameterJdbcOperations) {

        MongoItemWriter<IcdDocument> mongoWriter = new MongoItemWriterBuilder<IcdDocument>()
                .collection("icds")
                .delete(false)
                .template(mongoOperations)
                .build();

        return new CustomIcdMongoWriter(namedParameterJdbcOperations,
                mongoWriter);
    }


    @Bean
    public Step writeToTemporaryInMemoryDatabaseStep(StepBuilderFactory stepBuilderFactory,
                                                     ItemReader<IcdEntry> icdEntryXmlItemReader,
                                                     ItemWriter<IcdEntry> icdEntryJdbcBatchItemWriter) {
        return stepBuilderFactory
                .get("writeToTemporaryInMemoryDatabaseStep")
                .<IcdEntry, IcdEntry>chunk(1000)
                .reader(icdEntryXmlItemReader)
                .writer(icdEntryJdbcBatchItemWriter)
                .build();
    }

    @Bean
    public Step writeToMongoDbStep(StepBuilderFactory stepBuilderFactory,
                                   ItemReader<IcdEntry> icdEntryJdbcItemReader,
                                   ItemWriter<IcdEntry> icdDocumentWriter) {
        return stepBuilderFactory
                .get("writeToMongoDbStep")
                .<IcdEntry, IcdEntry>chunk(1000)
                .reader(icdEntryJdbcItemReader)
                .writer(icdDocumentWriter)
                .build();
    }

    @Bean
    public Step prepareStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("prepareJobStep")
                .tasklet(cleanUpTasklet())
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter cleanUpTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(prepareForJobService);
        adapter.setTargetMethod("prepare");

        return adapter;
    }

    @Bean
    public Job importIcdJob(JobBuilderFactory jobBuilderFactory,
                            Step prepareStep,
                            Step writeToTemporaryInMemoryDatabaseStep,
                            Step writeToMongoDbStep) {
        return jobBuilderFactory.get("importIcdJob")
                .start(prepareStep)
                .next(writeToTemporaryInMemoryDatabaseStep)
                .next(writeToMongoDbStep)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        log.info("Start import icd");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        log.info("End import icd");
                    }
                })
                .build();
    }
}
