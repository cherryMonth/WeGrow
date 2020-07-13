package com.wegrow.wegrow.demo.service;

import com.wegrow.wegrow.demo.dto.TopicParam;
import com.wegrow.wegrow.model.Topic;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Topic Service
 */
public interface TopicService {
    /**
     * 获取用户所有的Topic数量
     */
    Integer getAllTopicLength(String principalName);

    /**
     * 创建用户自己的Topic
     */
    int createTopic(String principalName, TopicParam topicParam);

    /**
     * 用户修改自己的Topic
     */
    @Transactional
    int updateTopic(String principalName, Integer id, TopicParam topicParam);

    /**
     * 用户删除Topic
     */
    int deleteTopic(String principalName, Integer id);

    /**
     * 用户批量删除Topic
     */
    int deleteTopic(String principalName, List<Integer> id);

    /**
     * 用户分页查询
     */
    List<Topic> listTopic(String principalName, String keyword, int pageNum, int pageSize);

    /**
     * 用户获取品牌
     */
    Topic getTopic(String principalName, Integer id);
}
