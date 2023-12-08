package edu.mirea.myinvest.config.quarts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Конфигурация для Quartz Scheduler
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzConfig {
    private final ApplicationContext applicationContext;
    private final DataSource dataSource;
    /**
     * Создает фабрику заданий Spring Bean
     *
     * @return Фабрика заданий Spring Bean
     */
    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }
    /**
     * Создает фабрику планировщика заданий Quartz
     *
     * @param triggers Массив триггеров
     * @return Фабрика планировщика заданий Quartz
     */
    @Bean
    public SchedulerFactoryBean scheduler(Trigger... triggers) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("org.quartz.scheduler.instanceName", "NeNinjaScheduler");
        properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
        properties.setProperty("org.quartz.jobStore.driverDelegateClass",
                "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setQuartzProperties(properties);
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        if (ArrayUtils.isNotEmpty(triggers)) {
            schedulerFactory.setTriggers(triggers);
        }
        return schedulerFactory;
    }
    /**
     * Создает фабрику простого триггера для задания Quartz
     *
     * @param jobDetail       Детали задания, к которому привязывается триггер
     * @param pollFrequencyS Частота повторения задания в секундах
     * @param triggerName     Имя триггера
     * @return Фабрика триггера для задания Quartz
     */
    public static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, Long pollFrequencyS, String triggerName) {
        log.debug("createTrigger(jobDetail={}, pollFrequencyS={}, triggerName={})", jobDetail.toString(), pollFrequencyS, triggerName);
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(pollFrequencyS * 1000);
        factoryBean.setName(triggerName);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.REPEAT_INDEFINITELY);
        return factoryBean;
    }
    /**
     * Создает и настраивает фабрику для создания экземпляра задания Quartz
     *
     * @param jobClass Класс, представляющий задание, которое нужно выполнить
     * @param jobName  Название задания
     * @return Фабрика для создания экземпляра задания Quartz
     */
    static JobDetailFactoryBean createJobDetail(Class jobClass, String jobName) {
        log.debug("createJobDetail(jobClass={}, jobName={})", jobClass.getName(), jobName);
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setName(jobName);
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);
        return factoryBean;
    }
}
