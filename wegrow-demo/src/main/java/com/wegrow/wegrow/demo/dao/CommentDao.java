package com.wegrow.wegrow.demo.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDao {
    public List<Object> getComment(@Param("targetType") String targetType, @Param("targetId") Integer targetId);

    public List<Object> getReplyByComment(@Param("targetId") Integer targetId);
}