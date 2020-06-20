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
     * 获取所有的Topic
     */
    List<Topic> getAllTopic();

    /**
     * 创建Topic
     */
    int createTopic(TopicParam topicParam, String userName);

    /**
     * 修改Topic
     */
    @Transactional
    int updateTopic(Integer id, TopicParam topicParam);

    /**
     * 删除Topic
     */
    int deleteTopic(Integer id);

    /**
     * 批量删除Topic
     */
    int deleteTopic(List<Integer> id);

    /**
     * 分页查询
     */
    List<Topic> listTopic(String keyword, int pageNum, int pageSize);

    /**
     * 获取品牌
     */
    Topic getTopic(Integer id);

    /**
     * 批量修改审核状态
     */
    int updateStatus(List<Integer> ids, Integer status);
}
