package edu.mirea.vitality.blog.service.system;

import edu.mirea.vitality.blog.config.security.SuperUserConfig;
import edu.mirea.vitality.blog.domain.model.system.ConfigUnit;
import edu.mirea.vitality.blog.domain.model.system.SystemPropertyKey;
import edu.mirea.vitality.blog.exception.system.ConfigUnitNotFoundProblem;
import edu.mirea.vitality.blog.exception.user.ForbiddenAccessProblem;
import edu.mirea.vitality.blog.repository.ConfigUnitRepository;
import edu.mirea.vitality.blog.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @class ConfigService
 * @brief Класс сервиса для управления конфигурациями.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ConfigService {

    /**
     * Имя кэша для конфигураций.
     */
    public static final String CONFIGURATIONS_CACHE_NAME = "configurations";

    private final ConfigUnitRepository repository;
    private final CacheManager cacheManager;
    private final SuperUserConfig superUserConfig;
    private final UserService userService;

    /**
     * Выполняет инициализацию загрузки конфигурации.
     */
    @PostConstruct
    public void init() {
        var units = repository.findAll();

        for (SystemPropertyKey key : SystemPropertyKey.values()) {
            if (units.stream().noneMatch(unit -> unit.getKey().name().equals(key.name()))) {
                save(ConfigUnit.builder()
                        .key(key)
                        .type(key.getType())
                        .value(key.getDefaultValue())
                        .customHandler(key.getCustomHandler())
                        .build());
            }
        }
        updateCache();
    }

    /**
     * Сохраняет конфигурацию.
     * @param unit Конфигурация для сохранения.
     * @return Сохраненная конфигурация.
     */
    public ConfigUnit save(ConfigUnit unit) {
        return repository.save(unit);
    }

    /**
     * Получает все конфигурации.
     * @return Список всех конфигураций.
     */
    @Cacheable(CONFIGURATIONS_CACHE_NAME)
    public List<ConfigUnit> getProperties() {
        return repository.findAll();
    }

    /**
     * Получает конфигурацию по ключу.
     * @param key Ключ конфигурации.
     * @return Конфигурация.
     */
    @Cacheable(CONFIGURATIONS_CACHE_NAME)
    public ConfigUnit getProperty(SystemPropertyKey key) {
        return repository.findByKey(key)
                .orElseThrow(() -> new RuntimeException("Config not found"));
    }

    /**
     * Обновляет конфигурацию.
     * @param key Ключ конфигурации.
     * @param value Значение конфигурации.
     */
    public void updateUnit(SystemPropertyKey key, String value) {
        var user = userService.getCurrentUser();
        if (user != null && superUserConfig.isSuperuser(user.getId())) {

            var unit = repository.findByKey(key)
                    .orElseThrow(() -> new ConfigUnitNotFoundProblem(key.name()));

            unit.setValue(value);
            save(unit);

            log.info("Config '{}' updated to '{}'", key, value);

            updateCache();
        } else {
            throw new ForbiddenAccessProblem();
        }
    }

    /**
     * Обновляет кэш.
     */
    public void updateCache() {
        Cache cache = cacheManager.getCache(CONFIGURATIONS_CACHE_NAME);
        if (cache != null) {
            cache.clear();
        }
    }
}

