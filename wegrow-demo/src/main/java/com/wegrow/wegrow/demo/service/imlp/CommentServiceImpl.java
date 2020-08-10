package com.wegrow.wegrow.demo.service.imlp;

import com.github.pagehelper.PageHelper;
import com.wegrow.wegrow.demo.dao.NameIdMapDao;
import com.wegrow.wegrow.demo.dto.CommentParam;
import com.wegrow.wegrow.demo.dto.ReplyParam;
import com.wegrow.wegrow.demo.service.CommentService;
import com.wegrow.wegrow.mapper.CommentMapper;
import com.wegrow.wegrow.mapper.ReplyMapper;
import com.wegrow.wegrow.model.Comment;
import com.wegrow.wegrow.model.CommentExample;
import com.wegrow.wegrow.model.ReplyExample;
import com.wegrow.wegrow.model.Reply;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private NameIdMapDao nameIdMapDao;

    @Override
    public Integer createComment(String principalName, CommentParam blockCommentParam) {
        Comment comment = new Comment();
        comment.setUserId(nameIdMapDao.getId(principalName));
        BeanUtils.copyProperties(blockCommentParam, comment);
        return commentMapper.insertSelective(comment);
    }

    @Override
    public Integer createReplyComment(String principalName, ReplyParam replyParam) {
        Reply reply = new Reply();
        reply.setReplyId(nameIdMapDao.getId(principalName));
        BeanUtils.copyProperties(replyParam, reply);
        return replyMapper.insertSelective(reply);
    }

    @Override
    // 批量删除评论
    public int deleteComment(String principalName, List<Integer> commentId) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andUserIdEqualTo(nameIdMapDao.getId(principalName)).andIdIn(commentId);
        Comment comment = new Comment();
        comment.setStatus(true);
        return commentMapper.updateByExample(comment, commentExample);
    }

    @Override
    // 批量删除回复
    public int deleteReplyComment(String principalName, List<Integer> replyId) {
        ReplyExample replyExample = new ReplyExample();
        replyExample.createCriteria().andReplyIdEqualTo(nameIdMapDao.getId(principalName)).andIdIn(replyId);
        Reply reply = new Reply();
        reply.setStatus(true);
        return replyMapper.updateByExample(reply, replyExample);
    }

    @Override
    // 获取文章的评论内容
    public List<List<Object>> getCommentList(String targetType, Integer targetId, int pageNum, int pageSize) {
        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause("ID asc");
        commentExample.createCriteria().andTargetTypeEqualTo(targetType).andTargetIdEqualTo(targetId);
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        List<List<Object>> commentResult = new ArrayList<>();
        ReplyExample replyExample = new ReplyExample();
        for (Comment comment : comments) {
            List<Object> objectList = new ArrayList<>();
            objectList.add(comment);
            replyExample.createCriteria().andCommentIdEqualTo(comment.getId());
            objectList.addAll(replyMapper.selectByExample(replyExample));
            replyExample.clear();
            commentResult.add(objectList);
        }
        return commentResult;
    }
}
