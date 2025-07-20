package com.scm.dto.requests;

import com.fasterxml.jackson.databind.cfg.DefaultCacheProvider;
import org.apache.kafka.clients.producer.internals.Sender;

import java.util.List;

public class MessageRequest {
    Sender sender;
    List<Recipient> to;
    String subject;
    String htmlContent;


}
