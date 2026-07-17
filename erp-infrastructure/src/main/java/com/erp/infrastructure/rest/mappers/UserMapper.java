package com.erp.infrastructure.rest.mappers;

import com.erp.domain.customer.CustomerInfo;
import com.erp.infrastructure.rest.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "address", expression = "java(dto.address().street() + \" \" + dto.address().suite())")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "zipcode", source = "address.zipcode")
    @Mapping(target = "companyName", source = "company.name")
    @Mapping(target = "state", constant = "N/A")
    @Mapping(target = "country", constant = "N/A")
    CustomerInfo toCustomerInfo(UserDTO dto);
}
