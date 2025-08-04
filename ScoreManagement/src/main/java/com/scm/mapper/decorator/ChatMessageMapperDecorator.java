//package com.scm.mapper.decorator;
//
//import com.scm.dto.SenderDTO;
//import com.scm.dto.requests.ChatMessageRequest;
//import com.scm.dto.responses.ChatMessageResponse;
//import com.scm.mapper.ChatMessageMapper;
//import com.scm.mapper.ClassDetailMapper;
//import com.scm.pojo.Conversation;
//import com.scm.pojo.Message;
//import com.scm.pojo.User;
//import com.scm.repositories.ConversationRepository;
//import com.scm.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ChatMessageMapperDecorator implements ChatMessageMapper {
//    @Autowired
//    @Qualifier("delegate")
//    private ClassDetailMapper delegate;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private ConversationRepository conversationRepository;
//
//    @Override
//    public ChatMessageResponse toChatMessageResponse(Message chatMessage) {
//        ChatMessageResponse response = new ChatMessageResponse();
//
//        response.setConversationId(conversationRepository.findById(chatMessage.getConversationId().toString()));
//        response.setContent(chatMessage.getMessage());
//        response.setCreatedDate(chatMessage.getCreatedDate());
//        return response;
//    }
//
//    @Override
//    public Message toChatMessage(ChatMessageRequest request) {
//        Message message = new Message();
//
//        User u = userRepository.findById(request.getUserId());
//        Conversation conversation = conversationRepository.findById(request.getConversationId());
//
//        message.setMessage(request.getMessage());
//        message.setConversationId(conversation);
//        return message;
//    }
//}
