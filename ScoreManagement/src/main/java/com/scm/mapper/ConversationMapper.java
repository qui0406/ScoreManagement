package com.scm.mapper;

import com.scm.dto.requests.ConversationRequest;
import com.scm.dto.responses.ConversationResponse;
import com.scm.pojo.Conversation;
import com.scm.mapper.decorator.ConversationMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(ConversationMapperDecorator.class)
public interface ConversationMapper {
    Conversation toConversation(ConversationRequest request);
    ConversationResponse toConversationResponse(Conversation request);
}
