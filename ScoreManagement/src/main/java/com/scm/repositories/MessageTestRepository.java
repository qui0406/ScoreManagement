package com.scm.repositories;

import com.scm.pojo.MessageTest;

import java.util.List;

public interface MessageTestRepository {
    List<MessageTest> findByOrderByTimestampAsc();
    void save(MessageTest messageTest);
    List<MessageTest> findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
            Long senderId1, Long receiverId1,
            Long receiverId2, Long senderId2);
}
