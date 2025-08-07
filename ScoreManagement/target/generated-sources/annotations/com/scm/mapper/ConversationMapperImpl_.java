package com.scm.mapper;

import com.scm.dto.requests.ConversationRequest;
import com.scm.dto.responses.ConversationResponse;
import com.scm.pojo.Conversation;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T08:44:49+0700",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
@Qualifier("delegate")
public class ConversationMapperImpl_ implements ConversationMapper {

    @Override
    public Conversation toConversation(ConversationRequest request) {
        if ( request == null ) {
            return null;
        }

        Conversation conversation = new Conversation();

        return conversation;
    }

    @Override
    public ConversationResponse toConversationResponse(Conversation request) {
        if ( request == null ) {
            return null;
        }

        ConversationResponse conversationResponse = new ConversationResponse();

        if ( request.getId() != null ) {
            conversationResponse.setId( String.valueOf( request.getId() ) );
        }
        conversationResponse.setCreatedAt( request.getCreatedAt() );

        return conversationResponse;
    }
}
