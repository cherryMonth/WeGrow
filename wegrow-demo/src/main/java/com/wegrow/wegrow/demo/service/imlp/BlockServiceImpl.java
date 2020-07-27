package com.wegrow.wegrow.demo.service.imlp;

import com.github.pagehelper.PageHelper;
import com.wegrow.wegrow.api.BlockStatus;
import com.wegrow.wegrow.demo.dao.NameIdMapDao;
import com.wegrow.wegrow.demo.dao.UserBlockMapDao;
import com.wegrow.wegrow.demo.dto.BlockParam;
import com.wegrow.wegrow.demo.service.BlockService;
import com.wegrow.wegrow.demo.service.FollowService;
import com.wegrow.wegrow.mapper.BlockMapper;
import com.wegrow.wegrow.model.Block;
import com.wegrow.wegrow.model.BlockExample;
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
    private NameIdMapDao nameIdMapDao;

    @Autowired
    private UserBlockMapDao userBlockMapDao;

    @Autowired
    private FollowService followService;


    @Override
    public int createBlock(BlockParam blockParam, String username) {
        Block block = new Block();
        block.setUserId(nameIdMapDao.getId(username));
        BeanUtils.copyProperties(blockParam, block);
        int count = blockMapper.insertSelective(block);
        if (count > 0) {
            return block.getId();
        }
        return count;
    }

    @Override
    public int updateBlock(BlockParam blockParam, String username, Integer blockId) {
        // 检查用户是否有对block的拥有权
        Block block = userBlockMapDao.getUserBlockAuth(username, blockId);
        if (block == null) {
            return 0;
        }
        BeanUtils.copyProperties(blockParam, block);
        int count = blockMapper.updateByPrimaryKeySelective(block);
        if (count > 0) {
            return block.getId();
        }
        return count;
    }

    @Override
    public int deleteBlock(String principalName, Integer blockId) {
        Block block = userBlockMapDao.getUserBlockAuth(principalName, blockId);
        if (block == null) {
            return 0;
        }
        block.setStatus(BlockStatus.DELETE.ordinal());
        return blockMapper.updateByPrimaryKey(block);
    }

    @Override
    public int deleteBlock(String principalName, List<Integer> blockIds) {
        BlockExample blockExample = new BlockExample();
        blockExample.createCriteria().andIdIn(blockIds).
                andUserIdEqualTo(nameIdMapDao.getId(principalName)).andStatusGreaterThan(BlockStatus.DELETE.ordinal());
        Block block = new Block();
        block.setStatus(BlockStatus.DELETE.ordinal());
        return blockMapper.updateByExampleSelective(block, blockExample);
    }

    @Override
    // 用户根据关键词查询发布的文章
    public List<Block> listBlock(String keyword, int pageNum, int pageSize) {
        BlockExample blockExample = new BlockExample();
        // 这里要填数据库中的名称
        blockExample.setOrderByClause("TITLE desc");
        // 只能查询发布的文章
        BlockExample.Criteria criteria = blockExample.createCriteria().andStatusEqualTo(BlockStatus.PUBLIC.ordinal());

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andTitleLike("%" + keyword + "%");
        }
        PageHelper.startPage(pageNum, pageSize);
        return blockMapper.selectByExampleWithBLOBs(blockExample);
    }

    @Override
    public List<Block> listBlockByUserId(Integer userId, int pageNum, int pageSize) {
        BlockExample blockExample = new BlockExample();
        blockExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(BlockStatus.PUBLIC.ordinal());
        blockExample.setOrderByClause("TITLE desc");
        PageHelper.startPage(pageNum, pageSize);
        return blockMapper.selectByExampleWithBLOBs(blockExample);
    }

    @Override
    // 用户查询自己的所有文章，支持关键词查询
    public List<Block> listBlock(String principalName, String keyword, int pageNum, int pageSize) {
        BlockExample blockExample = new BlockExample();
        // 这里要填数据库中的名称
        blockExample.setOrderByClause("TITLE desc");

        BlockExample.Criteria criteria = blockExample.createCriteria().
                andUserIdEqualTo(nameIdMapDao.getId(principalName)).andStatusGreaterThanOrEqualTo(BlockStatus.PUBLIC.ordinal());

        if (!StringUtils.isEmpty(keyword)) {
            criteria.andTitleLike("%" + keyword + "%");
        }
        PageHelper.startPage(pageNum, pageSize);
        return blockMapper.selectByExampleWithBLOBs(blockExample);
    }

    @Override
    // 登陆的用户根据ID返回所属的文章
    public Block getBlock(String principalName, Integer id) {
        BlockExample blockExample = new BlockExample();
        blockExample.createCriteria().andUserIdEqualTo(nameIdMapDao.getId(principalName)).
                andIdEqualTo(id).andStatusGreaterThanOrEqualTo(BlockStatus.DRAFT.ordinal());
        List<Block> blockList = blockMapper.selectByExampleWithBLOBs(blockExample);
        if (blockList.size() > 0) {
            return blockList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Block getBlockByUserPermission(String principalName, Integer id) {
        Block block = blockMapper.selectByPrimaryKey(id);
        // 如果文章不存在则返回NULL
        if (null == block) {
            return null;
        } else if (block.getStatus().equals(BlockStatus.PUBLIC.ordinal())) {
            // 如果文章状态为公开，则返回Block
            return block;
        } else if (block.getUserId().equals(nameIdMapDao.getId(principalName))) {
            // 如果文章的所有者查看则返回block
            return block;
        } else if (block.getStatus().equals(BlockStatus.FANS.ordinal()) &&
                followService.getUserPairFollowedStatus(nameIdMapDao.getId(principalName), block.getUserId()).equals(1)) {
            // 如果是作者的粉丝且文章可以被粉丝查看则返回block
            return block;
        }
        return null;
    }

    @Override
    public int updateBlockStatus(String principalName, List<Integer> ids, Integer status) {
        Block block = new Block();
        block.setStatus(status);
        BlockExample blockExample = new BlockExample();
        blockExample.createCriteria().andIdIn(ids).andUserIdEqualTo(nameIdMapDao.getId(principalName)).
                andStatusGreaterThanOrEqualTo(BlockStatus.DRAFT.ordinal());
        return blockMapper.updateByExampleSelective(block, blockExample);
    }

    @Override
    public List<Block> listBlockByStatus(String principalName, Integer status, int pageNum, int pageSize) {
        BlockExample blockExample = new BlockExample();
        // 这里要填数据库中的名称
        blockExample.setOrderByClause("TITLE desc");
        BlockExample.Criteria criteria = blockExample.createCriteria().
                andUserIdEqualTo(nameIdMapDao.getId(principalName)).andStatusEqualTo(status).
                andStatusGreaterThan(BlockStatus.DELETE.ordinal());
        PageHelper.startPage(pageNum, pageSize);
        return blockMapper.selectByExampleWithBLOBs(blockExample);
    }
}
