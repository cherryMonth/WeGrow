package com.wegrow.wegrow.demo.dao;

import com.wegrow.wegrow.model.Block;
import com.wegrow.wegrow.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * 根据用户名和BlockID返回Block
 */

public interface UserBlockMapDao {
    /**
     * 给定用户名和Block ID判断两者是否存在关系
     *
     * @param userName
     * @param blockId
     * @return
     */
    Block getUserBlockAuth(@Param("userName") String userName, @Param("blockId") Integer blockId);
}
