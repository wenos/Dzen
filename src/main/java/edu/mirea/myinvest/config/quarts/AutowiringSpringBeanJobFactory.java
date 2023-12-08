package edu.mirea.myinvest.config.quarts;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * Предоставляет возможность инъекции зависимостей в Quartz Job при его создании
 */
public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    // Фабрика для инъекции зависимостей
    private transient AutowireCapableBeanFactory beanFactory;

    /**
     * Устанавливает контекст приложения для фабрики
     *
     * @param context Контекст приложения
     */
    @Override
    public void setApplicationContext(final ApplicationContext context) {
        beanFactory = context.getAutowireCapableBeanFactory();
    }

    /**
     * Создает экземпляр задания (Job) и инъектирует зависимости в него
     *
     * @param bundle Информация о задании, запускаемом триггером
     * @return Экземпляр задания (Job) с инъектированными зависимостями
     * @throws Exception В случае возникновения ошибки при создании задания
     */
    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        final Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job);
        return job;
    }
}