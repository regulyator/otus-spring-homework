package ru.otus.icdimporter.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.otus.icdimporter.model.IcdEntry;
import ru.otus.icdimporter.tasklets.IcdWriter;

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
    @StepScope
    public ItemProcessor<IcdEntry, String> icdEntryProcessor() {
        return item -> item.toString();
    }


    @Bean
    @StepScope
    public ItemWriter<String> icdEntryItemWriter() {
        return items -> System.out.println(items);
    }

    @Bean
    protected Step step1(StepBuilderFactory stepBuilderFactory,
                         ItemStreamReader<IcdEntry> icdEntryItemReader,
                         ItemProcessor<IcdEntry, String> icdEntryStringItemProcessor,
                         ItemWriter<String> stringItemWriter) {
        return stepBuilderFactory
                .get("step1")
                .<IcdEntry, String>chunk(5)
                .reader(icdEntryItemReader)
                .processor(icdEntryStringItemProcessor)
                .writer(stringItemWriter)
                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(JobBuilderFactory jobBuilderFactory,
                   @Qualifier("step1") Step step1) {
        return jobBuilderFactory.get("firstBatchJob")
                .start(step1)
                .build();
    }
}
