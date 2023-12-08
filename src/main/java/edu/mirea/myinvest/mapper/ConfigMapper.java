package edu.mirea.myinvest.mapper;

import edu.mirea.myinvest.domain.dto.system.ConfigUnitResponse;
import edu.mirea.myinvest.domain.model.system.ConfigUnit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConfigMapper {
    ConfigUnitResponse toResponse(ConfigUnit configUnit);

    List<ConfigUnitResponse> toResponse(List<ConfigUnit> configUnits);
}