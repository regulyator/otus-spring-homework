package ru.otus.icdimporter.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.otus.icdimporter.model.IcdEntry;

import javax.sql.DataSource;

@Configuration
public class JobConfiguration {

    @Bean
    @StepScope
    public ItemStreamReader<IcdEntry> icdEntryItemReader(@Value("#{jobParameters['source']}") String inputSource) {
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
    public JdbcBatchItemWriter<IcdEntry> icdEntryJdbcBatchItemWriter(DataSource dataSource,
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
    public ItemStreamReader<IcdEntry> icdEntryJdbcItemReader(DataSource dataSource) {

        return new JdbcCursorItemReaderBuilder<IcdEntry>()
                .dataSource(dataSource)
                .name("icdEntryJdbcItemReader")
                .sql("SELECT id, rec_code as recCode, mkb_name as mbkName, mkb_code as mkbCode, actual as actual, id_parent as idParent " +
                        "from icd order by id ACS")
                .rowMapper(new BeanPropertyRowMapper<>(IcdEntry.class))
                .build();
    }

    @Bean
    protected Step writeToTemporaryInMemoryDatabaseStep(StepBuilderFactory stepBuilderFactory,
                                                        ItemStreamReader<IcdEntry> icdEntryItemReader,
                                                        JdbcBatchItemWriter<IcdEntry> icdEntryJdbcBatchItemWriter) {
        return stepBuilderFactory
                .get("writeToTemporaryInMemoryDatabase")
                .<IcdEntry, IcdEntry>chunk(100)
                .reader(icdEntryItemReader)
                .writer(icdEntryJdbcBatchItemWriter)
                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(JobBuilderFactory jobBuilderFactory,
                   @Qualifier("writeToTemporaryInMemoryDatabaseStep") Step writeToTemporaryInMemoryDatabaseStep) {
        return jobBuilderFactory.get("importIcdJob")
                .start(writeToTemporaryInMemoryDatabaseStep)
                .build();
    }
}
