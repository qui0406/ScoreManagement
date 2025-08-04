package com.scm.repositories;

import com.scm.pojo.WebSocketSession;

import java.util.List;

public interface WebSocketSessionRepository {
    void save(WebSocketSession webSocketSession);

    void deleteSocketSession(String socketSessionId);

    WebSocketSession findSocketSession(String socketSessionId);

    List<WebSocketSession> findAllSocketSessionsById(String userId);
}
