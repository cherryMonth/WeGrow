package com.wegrow.wegrow.demo.service;

import com.wegrow.wegrow.demo.dto.UpdateUserPasswordParam;
import com.wegrow.wegrow.demo.dto.UserInfoUpdateParam;
import com.wegrow.wegrow.demo.dto.UserParam;
import com.wegrow.wegrow.model.LocalAuth;
import com.wegrow.wegrow.model.Permission;
import com.wegrow.wegrow.model.Roles;
import com.wegrow.wegrow.model.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    /**
     * 根据用户名获取后台管理员
     */
    User getUserByUsername(String username);

    /**
     * 注册功能
     */
    User register(UserParam userParam);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 刷新token的功能
     *
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户id获取用户
     */
    User getItem(Integer id);

    /**
     * 根据用户名或昵称分页查询用户
     */
    List<User> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    String update(String principalName, UserInfoUpdateParam userInfoUpdateParam);

    /**
     * 删除指定用户
     */
    int delete(Integer id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Integer userId, List<Integer> roleIds);

    /**
     * 获取用户的角色
     */
    List<Roles> getRolesList(Integer userId);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<Permission> getPermissionList(Integer userId);

    /**
     * 根据用户的ID返回密码
     */
    LocalAuth getLocalAuth(Integer userId);

    /**
     * 修改密码
     */
    int updatePassword(String principalName, UpdateUserPasswordParam updatePasswordParam);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 根据用户名修改登陆时间
     */
    void updateLoginTimeByUsername(String username);

    /**
     * 获取用户的概述信息
     */
    HashMap<String, String> getSummaryUserInfo(Integer userId);
}
