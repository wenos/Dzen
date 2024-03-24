package edu.mirea.vitality.blog.service.system;

import edu.mirea.vitality.blog.config.security.SuperUserConfig;
import edu.mirea.vitality.blog.domain.model.system.SystemPropertyKey;
import edu.mirea.vitality.blog.mapper.ConfigUnitRepository;
import edu.mirea.vitality.blog.service.UserService;
import edu.mirea.vitality.blog.domain.model.system.ConfigUnit;
import edu.mirea.vitality.blog.exception.system.ConfigUnitNotFoundProblem;
import edu.mirea.vitality.blog.exception.user.ForbiddenAccessProblem;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConfigService {
    public static final String CONFIGURATIONS_CACHE_NAME = "configurations";
    private final ConfigUnitRepository repository;
    private final CacheManager cacheManager;
    private final SuperUserConfig superUserConfig;
    private final UserService userService;

    /**
     * Первоначальная загрузка конфигурации
     */
    @PostConstruct
    public void init() {
        // Загружаем конфигурацию
        var units = repository.findAll();

        // Проходим по всем ключам конфигурации
        for (SystemPropertyKey key : SystemPropertyKey.values()) {
            // Если конфигурация не найдена, то создаем ее
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
     * Сохранение конфигурации
     *
     * @param unit конфигурация
     * @return сохраненная конфигурация
     */
    public ConfigUnit save(ConfigUnit unit) {
        return repository.save(unit);
    }

    /**
     * Получение всех конфигураций
     *
     * @return список конфигураций
     */
    @Cacheable(CONFIGURATIONS_CACHE_NAME)
    public List<ConfigUnit> getProperties() {
        return repository.findAll();
    }

    /**
     * Получение конфигурации по ключу
     *
     * @param key ключ конфигурации
     * @return конфигурация
     */
    @Cacheable(CONFIGURATIONS_CACHE_NAME)
    public ConfigUnit getProperty(SystemPropertyKey key) {
        return repository.findByKey(key).orElseThrow(() -> new RuntimeException("Config not found"));
    }

    /**
     * Обновление конфигурации
     *
     * @param key   ключ конфигурации
     * @param value значение конфигурации
     */
    public void updateUnit(SystemPropertyKey key, String value) {
        var user = userService.getCurrentUser();
        if (user != null && superUserConfig.isSuperuser(user.getId())) {

            var unit = repository.findByKey(key).orElseThrow(() -> new ConfigUnitNotFoundProblem(key.name()));

            // Проверка была выполнена на уровне дто
            unit.setValue(value);
            save(unit);

            // Но если в конфиге хранить секреты, лучше так не делать
            log.info("Config '{}' updated to '{}'", key, value);

            // Обновляем кэш
            updateCache();
        } else {
            throw new ForbiddenAccessProblem();
        }
    }

    /**
     * Обновление кэша
     */
    public void updateCache() {
        Cache cache = cacheManager.getCache(CONFIGURATIONS_CACHE_NAME);
        if (cache != null) {
            cache.clear();
        }
    }
}
