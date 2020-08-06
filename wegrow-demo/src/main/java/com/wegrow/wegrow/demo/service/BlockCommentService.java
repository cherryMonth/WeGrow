package com.wegrow.wegrow.demo.service;

import com.wegrow.wegrow.demo.dto.BlockCommentParam;
import com.wegrow.wegrow.model.BlockComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlockCommentService {

    /**
     * 添加一条评论
     */
    Integer createComment(String principalName, Integer blockId, BlockCommentParam blockCommentParam);

    /**
     * 分页显示文章的评论
     *
     * @param blockId
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<BlockComment> getCommentById(@Param("blockId") Integer blockId, int pageNum, int pageSize);

    /**
     * 删除评论
     *
     * @param principalName
     * @param commentId
     * @return
     */
    int deleteComment(String principalName, Integer commentId);
}
