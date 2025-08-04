package com.scm.services;

import com.scm.pojo.WebSocketSession;

public interface WebSocketSessionService {
    WebSocketSession create(WebSocketSession webSocketSession);
    void delete(String socketSessionId);
}
