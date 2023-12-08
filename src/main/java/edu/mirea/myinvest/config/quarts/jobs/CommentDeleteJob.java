package edu.mirea.myinvest.config.quarts.jobs;

import edu.mirea.myinvest.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DisallowConcurrentExecution
public class CommentDeleteJob implements Job {
    @Autowired
    private CommentService commentService;

    /**
     * Выполняет задание по удалению старых комментариев
     *
     * @param context Контекст выполнения задания Quartz
     */@Override
    public void execute(JobExecutionContext context) {
        log.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        commentService.deleteOldComments();
        log.info("Job ** {} ** completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(), context.getNextFireTime());
    }
}