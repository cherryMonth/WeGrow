package com.wegrow.wegrow.demo.service.imlp;

import com.github.pagehelper.PageHelper;
import com.wegrow.wegrow.demo.dao.UserTopicAuth;
import com.wegrow.wegrow.demo.dto.TopicParam;
import com.wegrow.wegrow.demo.service.TopicService;
import com.wegrow.wegrow.mapper.TopicMapper;
import com.wegrow.wegrow.api.TopicStatus;
import com.wegrow.wegrow.model.TopicExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import com.wegrow.wegrow.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wegrow.wegrow.demo.dao.NameIdMapDao;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private NameIdMapDao nameIdMapDao;

    @Autowired
    private UserTopicAuth userTopicAuth;

    @Override
    public Integer getAllTopicLength(String principalName) {
        // 直接查询一个Example 代表全表查询
        TopicExample topicExample = new TopicExample();
        topicExample.createCriteria().andUserIdEqualTo(nameIdMapDao.getId(principalName)).
                andStatusEqualTo(TopicStatus.PRIVATE.ordinal());
        return topicMapper.selectByExample(topicExample).size();
    }

    @Override
    public int createTopic(String principalName, TopicParam topicParam) {

        Integer userId = nameIdMapDao.getId(principalName);
        TopicExample topicExample = new TopicExample();

        // 如果用户所属的topic已经存在，则不允许创建
        topicExample.createCriteria().andTopicNameEqualTo(topicParam.getTopicName()).andUserIdEqualTo(userId);
        if (topicMapper.selectByExample(topicExample).size() > 0) {
            return -1;
        }

        Topic topic = new Topic();
        BeanUtils.copyProperties(topicParam, topic);
        topic.setUserId(userId);
        topic.setStatus(TopicStatus.PRIVATE.ordinal());
        topicMapper.insert(topic);
        return topic.getId();
    }

    @Override
    public int updateTopic(String principalName, Integer id, TopicParam topicParam) {
        // 如果用户不存在该topic，则不允许修改
        Topic topic = userTopicAuth.getUserTopicAuth(principalName, id);
        if (topic == null) {
            return 0;
        }

        // 如果查询到名字相同ID不同，说明已经存在同名Topic，不允许修改
        Integer userId = nameIdMapDao.getId(principalName);
        TopicExample topicExample = new TopicExample();
        topicExample.createCriteria().andUserIdEqualTo(userId).andTopicNameEqualTo(topicParam.getTopicName()).
                andIdNotEqualTo(id);

        if (topicMapper.selectByExample(topicExample).size() > 0) {
            return -1;
        }

        BeanUtils.copyProperties(topicParam, topic);
        topicMapper.updateByPrimaryKeySelective(topic);
        return topic.getId();
    }

    @Override
    public int deleteTopic(String principalName, Integer id) {
        Topic topic = userTopicAuth.getUserTopicAuth(principalName, id);
        if (topic == null) {
            return 0;
        } else {
            topic.setStatus(TopicStatus.DELETE.ordinal());
            return topicMapper.updateByPrimaryKey(topic);
        }
    }

    @Override
    public int deleteTopic(String principalName, List<Integer> ids) {
        TopicExample topicExample = new TopicExample();
        topicExample.createCriteria().andUserIdEqualTo(nameIdMapDao.getId(principalName)).andIdIn(ids);
        Topic topic = new Topic();
        topic.setStatus(TopicStatus.DELETE.ordinal());
        return topicMapper.updateByExampleSelective(topic, topicExample);
    }

    @Override
    public List<Topic> listTopic(String principalName, String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        TopicExample topicExample = new TopicExample();
        // 这里要填数据库中的名称
        topicExample.setOrderByClause("TOPIC_NAME desc");
        TopicExample.Criteria criteria = topicExample.createCriteria().
                andUserIdEqualTo(nameIdMapDao.getId(principalName));
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andTopicNameLike("%" + keyword + "%");
        }
        return topicMapper.selectByExample(topicExample);
    }

    @Override
    public Topic getTopic(String principalName, Integer id) {
        return userTopicAuth.getUserTopicAuth(principalName, id);
    }

}
