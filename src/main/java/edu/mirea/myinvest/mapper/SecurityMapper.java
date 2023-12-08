package edu.mirea.myinvest.mapper;


import edu.mirea.myinvest.domain.dto.security.SecurityResponse;
import edu.mirea.myinvest.domain.model.Security;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SecurityTypeMapper.class})
public interface SecurityMapper {


    SecurityResponse toResponse(Security security);

    List<SecurityResponse> toResponse(List<Security> securities);
}
