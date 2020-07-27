package com.wegrow.wegrow.demo.service.imlp;

import com.github.pagehelper.PageHelper;
import com.wegrow.wegrow.demo.dao.NameIdMapDao;
import com.wegrow.wegrow.demo.dto.FollowParam;
import com.wegrow.wegrow.demo.service.FollowService;
import com.wegrow.wegrow.mapper.FollowMapper;
import com.wegrow.wegrow.mapper.UserMapper;
import com.wegrow.wegrow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NameIdMapDao nameIdMapDao;

    @Override
    public int createRelationship(FollowParam followParam, String principalName) {
        // 只有当前用户和表单提交user id的满足时才能创建关系
        User user = userMapper.selectByPrimaryKey(followParam.getUserId());

        if (null == user || !user.getId().equals(nameIdMapDao.getId(principalName))) {
            return 0;
        }

        Follow follow = new Follow();
        follow.setUserId(followParam.getUserId());
        follow.setFollowedUserId(followParam.getFollowedUserId());
        follow.setStatus(1);
        return followMapper.insertSelective(follow);
    }

    @Override
    public List<Follow> listAllRelationship() {
        return followMapper.selectByExample(new FollowExample());
    }

    @Override
    public List<Follow> listFollowingByPrincipalName(String principalName, int pageNum, int pageSize) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(principalName);
        User user = userMapper.selectByExample(userExample).get(0);
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andUserIdEqualTo(user.getId());
        PageHelper.startPage(pageNum, pageSize);
        return followMapper.selectByExample(followExample);
    }

    @Override
    public List<Follow> listFollowedByPrincipalName(String principalName, int pageNum, int pageSize) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(principalName);
        User user = userMapper.selectByExample(userExample).get(0);
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andFollowedUserIdEqualTo(user.getId());
        PageHelper.startPage(pageNum, pageSize);
        return followMapper.selectByExample(followExample);
    }

    @Override
    public Follow getFollow(Integer id) {
        return followMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(String principalName, Integer id) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(principalName);
        User user = userMapper.selectByExample(userExample).get(0);
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andUserIdEqualTo(user.getId()).andIdEqualTo(id);
        return followMapper.deleteByExample(followExample);
    }

    @Override
    public int updateFollowStatus(String principalName, List<Integer> ids, Integer status) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(principalName);
        User user = userMapper.selectByExample(userExample).get(0);
        Follow follow = new Follow();
        follow.setStatus(status);
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andIdIn(ids).andUserIdEqualTo(user.getId());
        return followMapper.updateByExampleSelective(follow, followExample);
    }

    @Override
    public Integer getUserPairFollowedStatus(Integer following, Integer followed) {
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andUserIdEqualTo(following).andFollowedUserIdEqualTo(followed);
        List<Follow> followList = followMapper.selectByExample(followExample);
        if (followList.size() > 0) {
            return followList.get(0).getStatus();
        }
        return null;
    }
}
