package edu.mirea.vitality.blog.service.sheduled;

import edu.mirea.vitality.blog.config.quarts.QuartzSubmitJobs;
import edu.mirea.vitality.blog.config.security.SuperUserConfig;
import edu.mirea.vitality.blog.domain.dto.system.UpdateJobIntervalRequest;
import edu.mirea.vitality.blog.domain.model.system.SystemPropertyKey;
import edu.mirea.vitality.blog.service.UserService;
import edu.mirea.vitality.blog.exception.sheduled.InvalidIntervalProblem;
import edu.mirea.vitality.blog.exception.system.JobNotFoundProblem;
import edu.mirea.vitality.blog.exception.user.ForbiddenAccessProblem;
import edu.mirea.vitality.blog.service.system.ConfigService;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class JobSchedulingService {

    private final Scheduler scheduler;
    private final SuperUserConfig superUserConfig;
    private final UserService userService;
    private final ConfigService configService;


    /**
     * Изменение интервала работы
     *
     * @param trigger  название триггера
     * @param interval интервал работы
     */
    public void changeJobInterval(String trigger, int interval) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(trigger);
            Trigger oldTrigger = scheduler.getTrigger(triggerKey);
            if (oldTrigger != null) {
                Trigger newTrigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(interval)
                                .repeatForever())
                        .startNow()
                        .build();

                scheduler.rescheduleJob(triggerKey, newTrigger);
            }
        } catch (SchedulerException ignored) {

        }
    }

    /**
     * Метод вычисляет общее количество секунд на основе запроса на изменение интервала выполнения задания
     *
     * @param request Запрос на изменение интервала выполнения задания
     * @return Общее количество секунд
     */
    private int calculateTotalSeconds(UpdateJobIntervalRequest request) {
        int days = isNull(request.days()) ? 0 : request.days() * 24 * 60 * 60;
        int hours = isNull(request.hours()) ? 0 : request.hours() * 60 * 60;
        int minutes = isNull(request.minutes()) ? 0 : request.minutes() * 60;
        int second = isNull(request.seconds()) ? 0 : request.seconds();
        int result = days + hours + minutes + second;
        if (result < 10) {
            throw new InvalidIntervalProblem(result);
        }
        return result;
    }


    /**
     * Изменение интервала удаления комментариев
     *
     * @param request запрос на изменение интервала
     * @param job     название работы
     */
    public void updateInterval(UpdateJobIntervalRequest request, String job) {
        var user = userService.getCurrentUser();
        if (!superUserConfig.isSuperuser(user.getId())) {
            throw new ForbiddenAccessProblem();
        }

        // С потенциальным расширением функционала
        switch (job) {
            case "comments":
                var totalSeconds = calculateTotalSeconds(request);
                configService.updateUnit(SystemPropertyKey.QUARTZ_COMMENT_DELETE_JOB_INTERVAL, String.valueOf(totalSeconds));
                changeJobInterval(
                        QuartzSubmitJobs.COMMENTS_DELETE_TRIGGER, totalSeconds
                );
                break;
            default:
                throw new JobNotFoundProblem();
        }
    }
}