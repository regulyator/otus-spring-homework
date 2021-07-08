package ru.otus.icdimporter.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.LocalDateTime;

import static ru.otus.icdimporter.config.JobConfiguration.SOURCE_FILE_JOB_PARAMETER;

@ShellComponent
@ShellCommandGroup("Icd importer commands")
public class IcdImportShellCommands {
    private final Job importIcdJob;
    private final JobLauncher jobLauncher;

    public IcdImportShellCommands(Job importIcdJob, JobLauncher jobLauncher) {
        this.importIcdJob = importIcdJob;
        this.jobLauncher = jobLauncher;
    }

    @ShellMethod(key = "import-icd", value = "Import icd")
    public void importIcd(@ShellOption(value = {"source"}, help = "source icd file") String sourceFile) throws Exception {
        JobExecution execution = jobLauncher.run(importIcdJob, new JobParametersBuilder()
                .addString(SOURCE_FILE_JOB_PARAMETER, sourceFile)
                .addString("execution_timestamp", LocalDateTime.now().toString())
                .toJobParameters());
        System.out.println(execution);
    }


}
