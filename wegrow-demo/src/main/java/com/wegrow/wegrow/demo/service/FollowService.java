package com.wegrow.wegrow.demo.service;

import com.wegrow.wegrow.demo.dto.FollowParam;
import com.wegrow.wegrow.model.Follow;

import java.util.List;

public interface FollowService {

    /**
     * 创建关注
     */
    int createRelationship(FollowParam followParam, String principalName);

    /**
     * 获取全部的用户关注表
     *
     * @return
     */
    List<Follow> listAllRelationship();

    /**
     * 判断一个人是否关注了另一个人
     *
     * @param following
     * @param followed
     * @return
     */
    Integer getUserPairFollowedStatus(Integer following, Integer followed);

    /**
     * 获取指定用户的关注表
     *
     * @param principalName
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Follow> listFollowingByPrincipalName(String principalName, int pageNum, int pageSize);

    /**
     * 获取关注指定用户的列表
     *
     * @param principalName
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Follow> listFollowedByPrincipalName(String principalName, int pageNum, int pageSize);

    /**
     * 获取指定的关注关系
     *
     * @param id
     * @return
     */
    Follow getFollow(Integer id);

    /**
     * 用户取消关注
     *
     * @param principalName
     * @param id
     * @return
     */
    int delete(String principalName, Integer id);

    /**
     * 批量取消关注
     *
     * @param principalName
     * @param ids
     * @param status
     * @return
     */
    int updateFollowStatus(String principalName, List<Integer> ids, Integer status);
}
