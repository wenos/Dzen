package edu.mirea.myinvest.domain.model.system;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * Это немного необычный подход, но в рамках данного проекта имеет место быть
 */
@Getter
@RequiredArgsConstructor
public enum SystemPropertyKey {

    QUARTZ_COMMENT_DELETE_JOB_INTERVAL("Интервал проверки для удаления комментариев, c", ConfigFieldType.NUMBER, "1800", true),
    QUARTZ_OLD_COMMENT_DELETE_MINUTES("Удалить комментарии старше, мин", ConfigFieldType.NUMBER, "1051200", false),
    SYSTEM_REGISTRATION_ENABLED("Разрешена регистрация", ConfigFieldType.BOOLEAN, "true", false);

    private final String title;
    private final ConfigFieldType type;
    private final String defaultValue;
    private final Boolean customHandler;
}
