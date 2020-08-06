package com.wegrow.wegrow.demo.dao;

import com.wegrow.wegrow.model.BlockComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 根据文章ID返回评论
 *
 */
public interface BlockCommentDao {
    public List<BlockComment> getCommentById(@Param("blockId") Integer blockId);
}
