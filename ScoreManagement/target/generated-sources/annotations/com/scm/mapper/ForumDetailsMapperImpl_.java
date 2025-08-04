package com.scm.mapper;

import com.scm.dto.requests.ForumDetailsRequest;
import com.scm.dto.responses.ForumDetailsResponse;
import com.scm.pojo.ForumDetails;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-03T22:50:33+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
@Qualifier("delegate")
public class ForumDetailsMapperImpl_ implements ForumDetailsMapper {

    @Override
    public ForumDetailsResponse toForumDetailsResponse(ForumDetails forum) {
        if ( forum == null ) {
            return null;
        }

        ForumDetailsResponse forumDetailsResponse = new ForumDetailsResponse();

        forumDetailsResponse.setForum( forum.getForum() );
        forumDetailsResponse.setMessage( forum.getMessage() );
        forumDetailsResponse.setCreatedAt( forum.getCreatedAt() );

        return forumDetailsResponse;
    }

    @Override
    public ForumDetails toForum(ForumDetailsRequest request) {
        if ( request == null ) {
            return null;
        }

        ForumDetails forumDetails = new ForumDetails();

        forumDetails.setMessage( request.getMessage() );
        forumDetails.setCreatedAt( request.getCreatedAt() );

        return forumDetails;
    }
}
