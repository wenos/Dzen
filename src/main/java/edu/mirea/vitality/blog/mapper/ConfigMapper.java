package edu.mirea.vitality.blog.mapper;

import edu.mirea.vitality.blog.domain.dto.system.ConfigUnitResponse;
import edu.mirea.vitality.blog.domain.model.system.ConfigUnit;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @file ConfigMapper.java
 * @brief Этот файл содержит интерфейс ConfigMapper.
 */

/**
 * @interface ConfigMapper
 * @brief Этот интерфейс предоставляет методы для преобразования объектов конфигурации между сущностью и объектом ответа.
 */
@Mapper(componentModel = "spring")
public interface ConfigMapper {

    /**
     * @brief Преобразует сущность конфигурационного блока в объект ответа конфигурации.
     * @param configUnit Сущность конфигурационного блока.
     * @return Ответ конфигурации.
     */
    ConfigUnitResponse toResponse(ConfigUnit configUnit);

    /**
     * @brief Преобразует список сущностей конфигурационных блоков в список объектов ответа конфигурации.
     * @param configUnits Список сущностей конфигурационных блоков.
     * @return Список ответов конфигурации.
     */
    List<ConfigUnitResponse> toResponse(List<ConfigUnit> configUnits);
}
