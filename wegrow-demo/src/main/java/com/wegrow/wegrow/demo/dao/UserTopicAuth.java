package com.wegrow.wegrow.demo.dao;

import com.wegrow.wegrow.model.Topic;
import org.apache.ibatis.annotations.Param;

/**
 * 根据用户名和TopicID返回Topic
 */
public interface UserTopicAuth {
    Topic getUserTopicAuth(@Param("userName") String userName, @Param("topicId") Integer topicId);
}
