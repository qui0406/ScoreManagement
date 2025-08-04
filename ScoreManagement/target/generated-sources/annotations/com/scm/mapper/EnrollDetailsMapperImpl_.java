package com.scm.mapper;

import com.scm.dto.requests.EnrollClassRequest;
import com.scm.pojo.EnrollDetails;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-04T12:54:59+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
@Qualifier("delegate")
public class EnrollDetailsMapperImpl_ implements EnrollDetailsMapper {

    @Override
    public EnrollDetails toEnrollDetails(EnrollClassRequest request) {
        if ( request == null ) {
            return null;
        }

        EnrollDetails enrollDetails = new EnrollDetails();

        return enrollDetails;
    }
}
