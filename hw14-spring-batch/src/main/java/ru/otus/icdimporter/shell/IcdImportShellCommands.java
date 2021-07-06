package ru.otus.icdimporter.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

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
    public void createAuthor(@ShellOption({"AuthorFio"}) String authorFio) {
        /*authorService.create(authorFio);*/
    }


}
