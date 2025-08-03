package com.scm.mapper;

import com.scm.dto.requests.ForumRequest;
import com.scm.dto.responses.ForumResponse;
import com.scm.pojo.Forum;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-03T13:27:14+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
@Qualifier("delegate")
public class ForumMapperImpl_ implements ForumMapper {

    @Override
    public ForumResponse toResponse(Forum forum) {
        if ( forum == null ) {
            return null;
        }

        ForumResponse forumResponse = new ForumResponse();

        forumResponse.setContent( forum.getContent() );
        forumResponse.setCreatedAt( forum.getCreatedAt() );

        return forumResponse;
    }

    @Override
    public Forum toForum(ForumRequest request) {
        if ( request == null ) {
            return null;
        }

        Forum forum = new Forum();

        forum.setContent( request.getContent() );
        forum.setCreatedAt( request.getCreatedAt() );

        return forum;
    }
}
