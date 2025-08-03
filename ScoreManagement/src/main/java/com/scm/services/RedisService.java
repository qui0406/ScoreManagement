package com.scm.services;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    void setValue(String key, Object value);
    void setValue(String key, Object value, long timeout, TimeUnit unit);
    Object getValue(String key);
    boolean hasKey(String key);
    void deleteKey(String key);
}
