package com.wegrow.wegrow.demo.service;

import com.wegrow.wegrow.demo.dto.CommentParam;
import com.wegrow.wegrow.demo.dto.ReplyParam;

import java.util.List;

public interface CommentService {

    /**
     * 添加一条评论
     */
    Integer createComment(String principalName, CommentParam blockCommentParam);

    /**
     * 添加一条回复
     */
    Integer createReplyComment(String principalName, ReplyParam replyParam);

    /**
     * 删除评论
     *
     * @param principalName
     * @param commentId
     * @return
     */
    int deleteComment(String principalName, List<Integer> commentId);

    /**
     * 删除回复评论
     *
     * @param principalName
     * @param replyId
     * @return
     */
    int deleteReplyComment(String principalName, List<Integer> replyId);

    /**
     * 获取评论
     * @return
     */
    List<Object> getCommentList(String targetType, Integer targetId, int pageNum, int pageSize);
}
