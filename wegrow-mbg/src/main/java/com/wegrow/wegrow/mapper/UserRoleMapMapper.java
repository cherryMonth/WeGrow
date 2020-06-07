package com.wegrow.wegrow.mapper;

import com.wegrow.wegrow.model.UserRoleMap;
import com.wegrow.wegrow.model.UserRoleMapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapMapper {
    long countByExample(UserRoleMapExample example);

    int deleteByExample(UserRoleMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRoleMap record);

    int insertSelective(UserRoleMap record);

    List<UserRoleMap> selectByExample(UserRoleMapExample example);

    UserRoleMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRoleMap record, @Param("example") UserRoleMapExample example);

    int updateByExample(@Param("record") UserRoleMap record, @Param("example") UserRoleMapExample example);

    int updateByPrimaryKeySelective(UserRoleMap record);

    int updateByPrimaryKey(UserRoleMap record);
}