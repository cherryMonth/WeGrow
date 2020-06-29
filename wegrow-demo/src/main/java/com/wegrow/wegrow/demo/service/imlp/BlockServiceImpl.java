package com.wegrow.wegrow.demo.service.imlp;

import com.github.pagehelper.PageHelper;
import com.wegrow.wegrow.demo.dao.UserBlockMapDao;
import com.wegrow.wegrow.demo.dto.BlockParam;
import com.wegrow.wegrow.demo.service.BlockService;
import com.wegrow.wegrow.mapper.BlockMapper;
import com.wegrow.wegrow.mapper.UserMapper;
import com.wegrow.wegrow.model.Block;
import com.wegrow.wegrow.model.BlockExample;
import com.wegrow.wegrow.model.User;
import com.wegrow.wegrow.model.UserExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockMapper blockMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserBlockMapDao userBlockMapDao;

    @Override
    public List<Block> listAllBlock() {
        return blockMapper.selectByExampleWithBLOBs(new BlockExample());
    }

    @Override
    public int createBlock(BlockParam blockParam, String username) {
        Block block = new Block();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        block.setUserId(userMapper.selectByExample(userExample).get(0).getId());
        BeanUtils.copyProperties(blockParam, block);
        return blockMapper.insertSelective(block);
    }

    @Override
    public int updateBlock(BlockParam blockParam, String username, Integer blockId) {
        // 检查用户是否有对block的拥有权
        if (userBlockMapDao.getUserBlockAuth(username, blockId) == null) {
            return 0;
        }
        Block block = new Block();
        BeanUtils.copyProperties(blockParam, block);
        block.setId(blockId);
        return blockMapper.updateByPrimaryKeySelective(block);
    }

    @Override
    public int deleteBlock(String principalName, Integer blockId) {
        if (userBlockMapDao.getUserBlockAuth(principalName, blockId) == null) {
            return 0;
        }
        return blockMapper.deleteByPrimaryKey(blockId);
    }

    @Override
    public int deleteBlock(String principalName, List<Integer> blockIds) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(principalName);
        User user = userMapper.selectByExample(userExample).get(0);
        BlockExample blockExample = new BlockExample();
        blockExample.createCriteria().andIdIn(blockIds).andUserIdEqualTo(user.getId());
        return blockMapper.deleteByExample(blockExample);
    }

    @Override
    public List<Block> listBlock(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        BlockExample blockExample = new BlockExample();
        // 这里要填数据库中的名称
        blockExample.setOrderByClause("TITLE desc");
        BlockExample.Criteria criteria = blockExample.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andTitleLike("%" + keyword + "%");
        }
        return blockMapper.selectByExampleWithBLOBs(blockExample);
    }

    @Override
    public List<Block> listBlock(String principalName, String keyword, int pageNum, int pageSize) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(principalName);
        User user = userMapper.selectByExample(userExample).get(0);

        PageHelper.startPage(pageNum, pageSize);
        BlockExample blockExample = new BlockExample();
        // 这里要填数据库中的名称
        blockExample.setOrderByClause("TITLE desc");
        BlockExample.Criteria criteria = blockExample.createCriteria().andUserIdEqualTo(user.getId());

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andTitleLike("%" + keyword + "%");
        }
        return blockMapper.selectByExampleWithBLOBs(blockExample);
    }

    @Override
    public Block getBlock(Integer id) {
        return blockMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateBlockStatus(String principalName, List<Integer> ids, Integer status) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(principalName);
        User user = userMapper.selectByExample(userExample).get(0);
        Block block = new Block();
        block.setStatus(status);
        BlockExample blockExample = new BlockExample();
        blockExample.createCriteria().andIdIn(ids).andUserIdEqualTo(user.getId());
        return blockMapper.updateByExampleSelective(block, blockExample);
    }
}
