package edu.mirea.myinvest.mapper;


import edu.mirea.myinvest.domain.dto.type.security.SecurityTypeResponse;
import edu.mirea.myinvest.domain.model.SecurityType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SecurityTypeMapper {

    SecurityTypeResponse toResponse(SecurityType type);

    List<SecurityTypeResponse> toResponse(List<SecurityType> types);

}
