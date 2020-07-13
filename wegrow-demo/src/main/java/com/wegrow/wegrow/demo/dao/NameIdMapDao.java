package com.wegrow.wegrow.demo.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 根据用户名返回Id
 */
public interface NameIdMapDao {
    public Integer getId(@Param("userName") String username);
}
