package edu.mirea.vitality.blog.config.quarts;

import edu.mirea.vitality.blog.config.quarts.jobs.CommentDeleteJob;
import edu.mirea.vitality.blog.domain.model.system.SystemPropertyKey;
import edu.mirea.vitality.blog.service.system.ConfigService;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
@RequiredArgsConstructor
public class QuartzSubmitJobs {

    public static final String COMMENTS_DELETE_JOB_NAME = "CommentsDelete";
    public static final String COMMENTS_DELETE_TRIGGER = COMMENTS_DELETE_JOB_NAME + "Trigger";
    private final ConfigService configService;

    @Bean(name = "commentsDeleteJob")
    public JobDetailFactoryBean jobCommentsDelete() {
        return QuartzConfig.createJobDetail(CommentDeleteJob.class, COMMENTS_DELETE_JOB_NAME);
    }

    @Bean(name = "commentsDeleteTrigger")
    public SimpleTriggerFactoryBean triggerCommentsDelete(@Qualifier("commentsDeleteJob") JobDetail jobDetail) {
        Long interval = configService.getProperty(SystemPropertyKey.QUARTZ_COMMENT_DELETE_JOB_INTERVAL).getLongValue();
        return QuartzConfig.createTrigger(
                jobDetail,
                interval,
                COMMENTS_DELETE_TRIGGER);
    }
}