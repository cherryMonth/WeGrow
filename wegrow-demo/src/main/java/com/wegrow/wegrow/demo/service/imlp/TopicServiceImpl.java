package com.wegrow.wegrow.demo.service.imlp;

import com.github.pagehelper.PageHelper;
import com.wegrow.wegrow.demo.dto.TopicParam;
import com.wegrow.wegrow.demo.service.TopicService;
import com.wegrow.wegrow.mapper.TopicMapper;
import com.wegrow.wegrow.mapper.UserMapper;
import com.wegrow.wegrow.model.Topic;
import com.wegrow.wegrow.model.TopicExample;
import com.wegrow.wegrow.model.UserExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Topic> getAllTopic() {
        // 直接查询一个Example 代表全表查询
        return topicMapper.selectByExample(new TopicExample());
    }

    @Override
    public int createTopic(TopicParam topicParam, String userName) {
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicParam, topic);
        TopicExample topicExample = new TopicExample();
        topicExample.createCriteria().andTopicNameEqualTo(topicParam.getTopicName());
        if (topicMapper.selectByExample(topicExample).size() > 0) {
            return 0;
        }

        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(userName);
        topic.setUserId(userMapper.selectByExample(userExample).get(0).getId());
        return topicMapper.insert(topic);
    }

    @Override
    public int updateTopic(Integer id, TopicParam topicParam) {
        TopicExample topicExample = new TopicExample();
        topicExample.createCriteria().andTopicNameEqualTo(topicParam.getTopicName()).andIdNotEqualTo(id);
        if (topicMapper.selectByExample(topicExample).size() > 0) {
            return 0;
        }
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicParam, topic);
        topic.setId(id);
        return topicMapper.updateByPrimaryKeySelective(topic);
    }

    @Override
    public int deleteTopic(Integer id) {
        return topicMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteTopic(List<Integer> ids) {
        TopicExample topicExample = new TopicExample();
        topicExample.createCriteria().andIdIn(ids);
        return topicMapper.deleteByExample(topicExample);
    }

    @Override
    public List<Topic> listTopic(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        TopicExample topicExample = new TopicExample();
        // 这里要填数据库中的名称
        topicExample.setOrderByClause("TOPIC_NAME desc");
        TopicExample.Criteria criteria = topicExample.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andTopicNameLike("%" + keyword + "%");
        }
        return topicMapper.selectByExample(topicExample);
    }

    @Override
    public Topic getTopic(Integer id) {
        return topicMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateStatus(List<Integer> ids, Integer status) {
        Topic topic = new Topic();
        topic.setStatus(status);
        TopicExample topicExample = new TopicExample();
        topicExample.createCriteria().andIdIn(ids);
        return topicMapper.updateByExampleSelective(topic, topicExample);
    }
}
