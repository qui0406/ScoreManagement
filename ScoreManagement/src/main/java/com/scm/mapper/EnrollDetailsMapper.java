package com.scm.mapper;

import com.scm.dto.requests.EnrollClassRequest;
import com.scm.mapper.decorator.EnrollDetailsMapperDecorator;
import com.scm.pojo.EnrollDetails;
import com.scm.services.EnrollDetailsService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(EnrollDetailsMapperDecorator.class)
public interface EnrollDetailsMapper {
    EnrollDetails toEnrollDetails(EnrollClassRequest request);
}
