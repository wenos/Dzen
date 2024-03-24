package edu.mirea.vitality.blog.mapper;

import edu.mirea.vitality.blog.domain.dto.system.ConfigUnitResponse;
import edu.mirea.vitality.blog.domain.model.system.ConfigUnit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConfigMapper {
    ConfigUnitResponse toResponse(ConfigUnit configUnit);

    List<ConfigUnitResponse> toResponse(List<ConfigUnit> configUnits);
}