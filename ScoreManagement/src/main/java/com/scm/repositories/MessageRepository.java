package com.scm.repositories;

import com.scm.pojo.Message;

import java.util.List;

public interface MessageRepository {
    void create(Message message);
    void update(Message message);
    void delete(Message message);

    Message findById(String id);

    List<Message> findAllMessagesByUserId(String userId, String page);
}
