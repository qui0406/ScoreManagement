package com.scm.services.impl;

import com.scm.pojo.WebSocketSession;
import com.scm.repositories.WebSocketSessionRepository;
import com.scm.services.WebSocketSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebSocketSessionServiceImpl implements WebSocketSessionService {
    @Autowired
    private WebSocketSessionRepository webSocketSessionRepository;

    @Override
    public WebSocketSession create(WebSocketSession webSocketSession) {
        webSocketSessionRepository.save(webSocketSession);
        return webSocketSession;
    }

    @Override
    public void delete(String socketSessionId) {
        webSocketSessionRepository.deleteSocketSession(socketSessionId);
    }
}
