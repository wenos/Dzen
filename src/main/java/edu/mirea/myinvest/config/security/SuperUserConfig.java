package edu.mirea.myinvest.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


/**
 * Класс конфигурации суперпользователя
 */
@Component
@Configuration
@Getter
@RequiredArgsConstructor
public class SuperUserConfig {

    @Value("${superuser.default.password}")
    private String superuserDefaultPassword;

    @Value("${superuser.id}")
    private Long superuserId = 0L;


    @Value("${superuser.enabled}")
    private boolean superuserEnabled = false;


    /**
     * Является ли пользователь суперпользователем
     *
     * @param userId ID пользователя для проверки
     * @return true, если пользователь суперпользователь
     */
    public boolean isSuperuser(Long userId) {
        return userId.equals(superuserId) && superuserEnabled;
    }
}
