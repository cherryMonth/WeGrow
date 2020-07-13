package com.wegrow.wegrow.demo.dao;

import com.wegrow.wegrow.model.Permission;
import com.wegrow.wegrow.model.Resource;
import com.wegrow.wegrow.model.Roles;
import com.wegrow.wegrow.model.UserRoleMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 如果是实体的model，则使用Mapper即可
 * 如果model只是用来构建关系，连接多个model，则使用Dao对这个model的Mapper进行封装
 */

public interface UserRoleMapDao {
    // 批量插入用户角色关系
    int insertList(@Param("list") List<UserRoleMap> userRoleMapList);

    // 获取用户所有的角色
    List<Roles> getRolesList(@Param("userId") Integer userId);

    // 获取用户所有的权限
    List<Permission> getPermissionList(@Param("userId") Integer userId);

    // 获取所有用户可以访问的资源
    List<Resource> getResourceList(@Param("userId") Integer userId);
}
