package com.wegrow.wegrow.demo.service.imlp;

import com.wegrow.wegrow.demo.dao.NameIdMapDao;
import com.wegrow.wegrow.demo.dto.BlockCommentParam;
import com.wegrow.wegrow.demo.service.BlockCommentService;
import com.wegrow.wegrow.mapper.BlockCommentMapper;
import com.wegrow.wegrow.mapper.BlockParentChildMapMapper;
import com.wegrow.wegrow.model.BlockComment;
import com.wegrow.wegrow.model.BlockCommentExample;
import com.wegrow.wegrow.model.BlockParentChildMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockCommentServiceImpl implements BlockCommentService {

    @Autowired
    private BlockCommentMapper blockCommentMapper;

    @Autowired
    private NameIdMapDao nameIdMapDao;

    @Autowired
    private BlockParentChildMapMapper blockParentChildMapMapper;

    @Override
    public Integer createComment(String principalName, Integer blockId, BlockCommentParam blockCommentParam) {
        BlockComment blockComment = new BlockComment();
        blockComment.setUserId(nameIdMapDao.getId(principalName));
        BeanUtils.copyProperties(blockCommentParam, blockComment);
        blockCommentMapper.insertSelective(blockComment);
        // 若该评论不是根评论，则添加评论关系映射
        if (!blockComment.getStatus()) {
            BlockParentChildMap blockParentChildMap = new BlockParentChildMap();
            BeanUtils.copyProperties(blockCommentParam, blockParentChildMap);
            blockParentChildMap.setChildId(blockComment.getId());
            blockParentChildMapMapper.insertSelective(blockParentChildMap);
        }
        return blockComment.getId();
    }

    @Override
    public List<BlockComment> getCommentById(Integer blockId, int pageNum, int pageSize) {
        BlockCommentExample blockCommentExample = new BlockCommentExample();
//        blockCommentExample
        return null;
    }

    @Override
    public int deleteComment(String principalName, Integer commentId) {
        BlockCommentExample blockCommentExample = new BlockCommentExample();
        blockCommentExample.createCriteria().andUserIdEqualTo(nameIdMapDao.getId(principalName)).andIdEqualTo(commentId);
        return blockCommentMapper.deleteByExample(blockCommentExample);
    }
}
