package com.scm.mapper;

import com.scm.dto.requests.UserRequest;
import com.scm.dto.responses.UserResponse;
import com.scm.pojo.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-12T14:19:32+0700",
    comments = "version: 1.6.0, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setFirstName( request.getFirstName() );
        user.setLastName( request.getLastName() );
        user.setUsername( request.getUsername() );
        user.setPassword( request.getPassword() );
        user.setEmail( request.getEmail() );
        user.setPhone( request.getPhone() );
        user.setGender( request.isGender() );
        user.setAddress( request.getAddress() );
        user.setDob( request.getDob() );
        user.setAvatar( request.getAvatar() );

        return user;
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setId( user.getId() );
        userResponse.setUsername( user.getUsername() );
        userResponse.setFirstName( user.getFirstName() );
        userResponse.setLastName( user.getLastName() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setPhone( user.getPhone() );
        userResponse.setGender( user.isGender() );
        userResponse.setAddress( user.getAddress() );
        userResponse.setRole( user.getRole() );
        userResponse.setAvatar( user.getAvatar() );

        return userResponse;
    }
}
