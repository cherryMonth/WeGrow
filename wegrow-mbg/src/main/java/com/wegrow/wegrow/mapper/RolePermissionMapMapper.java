package com.wegrow.wegrow.mapper;

import com.wegrow.wegrow.model.RolePermissionMap;
import com.wegrow.wegrow.model.RolePermissionMapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionMapMapper {
    long countByExample(RolePermissionMapExample example);

    int deleteByExample(RolePermissionMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RolePermissionMap record);

    int insertSelective(RolePermissionMap record);

    List<RolePermissionMap> selectByExample(RolePermissionMapExample example);

    RolePermissionMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RolePermissionMap record, @Param("example") RolePermissionMapExample example);

    int updateByExample(@Param("record") RolePermissionMap record, @Param("example") RolePermissionMapExample example);

    int updateByPrimaryKeySelective(RolePermissionMap record);

    int updateByPrimaryKey(RolePermissionMap record);
}