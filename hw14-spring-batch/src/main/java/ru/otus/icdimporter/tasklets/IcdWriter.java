package ru.otus.icdimporter.tasklets;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import ru.otus.icdimporter.model.IcdEntry;
import ru.otus.icdimporter.model.IcdTree;

public class IcdWriter implements Tasklet, StepExecutionListener {
    private IcdTree<IcdEntry> icdEntryTree;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println(icdEntryTree);
        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        icdEntryTree= (IcdTree<IcdEntry>) stepExecution
                .getJobExecution()
                .getExecutionContext().get("icdEntryTree");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
