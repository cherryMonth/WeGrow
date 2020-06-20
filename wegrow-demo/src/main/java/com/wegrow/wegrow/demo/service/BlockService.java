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
    List<Block> listAllBlock();

    /**
     * 创建Block并指定User id
     */
    int createBlock(BlockParam blockParam, String principalName);

    /**
     * 给定block内容、Block ID和User name对Block进行更新
     *
     * @param blockParam
     * @param principalName
     * @param blockId
     * @return
     */
    @Transactional
    int updateBlock(BlockParam blockParam, String principalName, Integer blockId);

    /**
     * 根据UserName删除其所属的Block
     *
     * @param principalName
     * @param blockId
     * @return
     */
    int deleteBlock(String principalName, Integer blockId);

    /**
     * 根据UserName批量删除其所属的Block
     *
     * @param principalName
     * @param blockIds
     * @return
     */
    int deleteBlock(String principalName, List<Integer> blockIds);

    /**
     * 根据关键词批量返回Block
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Block> listBlock(String keyword, int pageNum, int pageSize);

    /**
     * 根据关键词批量返回User所属的Block
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Block> listBlock(String principalName, String keyword, int pageNum, int pageSize);

    /**
     * 根据id返回Block
     *
     * @param id
     * @return
     */
    Block getBlock(Integer id);

    /**
     * 批量修改用户所属的Block status
     *
     * @param ids
     * @param status
     * @return
     */
    int updateBlockStatus(String principalName, List<Integer> ids, Integer status);
}
