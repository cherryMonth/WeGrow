package com.wegrow.wegrow.demo.service;

import com.wegrow.wegrow.demo.dto.BlockParam;
import com.wegrow.wegrow.model.Block;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository：持久层，用于标注数据访问组件，即DAO组件。
 * Service：业务层，用于标注业务逻辑层主键。
 * Controller：控制层，用于标注控制层组件。
 * Component：当你不确定是属于哪一层的时候使用。
 */
public interface BlockService {

    /**
     * 创建Block并指定User id
     */
    int createBlock(BlockParam blockParam, String principalName);

    /**
     * 给定block内容、Block ID和User name对Block进行更新(只能修改草稿及以上的状态)
     *
     * @param blockParam
     * @param principalName
     * @param blockId
     * @return
     */
    @Transactional
    int updateBlock(BlockParam blockParam, String principalName, Integer blockId);

    /**
     * 根据UserName删除其所属的Block(只能删除草稿及以上的状态)
     *
     * @param principalName
     * @param blockId
     * @return
     */
    int deleteBlock(String principalName, Integer blockId);

    /**
     * 根据UserName批量删除其所属的Block(只能删除草稿及以上的状态)
     *
     * @param principalName
     * @param blockIds
     * @return
     */
    int deleteBlock(String principalName, List<Integer> blockIds);

    /**
     * 根据关键词批量返回Block (只能查询公开状态的block)
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Block> listBlock(String keyword, int pageNum, int pageSize);

    /**
     * 根据关键词批量返回User所属的Block (只能查询公开及以上的文章)
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Block> listBlock(String principalName, String keyword, int pageNum, int pageSize);

    /**
     * 根据用户ID返回Block
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Block> listBlockByUserId(Integer userId, int pageNum, int pageSize);

    /**
     * 根据id返回用户所属的Block，只能返回被大于等于草稿的文章
     *
     * @param id
     * @return
     */
    Block getBlock(String principalName, Integer id);

    /**
     * 根据id返回Block，需要判断用户是否有对文章查阅的权限
     *
     * @param id
     * @return
     */
    Block getBlockByUserPermission(String principalName, Integer id);

    /**
     * 批量修改用户所属的Block status(状态需要大于等于草稿)
     *
     * @param ids
     * @param status
     * @return
     */
    int updateBlockStatus(String principalName, List<Integer> ids, Integer status);

    /**
     * 返回用户具有某种状态的文章 (状态需要大于等于公开)
     * 0：被封禁
     * 1：正常
     * 2：草稿
     * 3：被删除
     * @param principalName
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Block> listBlockByStatus(String principalName, Integer status, int pageNum, int pageSize);

    /**
     * 获取block的总数目
     * @return
     */
    long getCountNum(String principalName, Integer status);
}
