package com.wegrow.wegrow.demo.service;

import com.wegrow.wegrow.demo.dto.BlockDto;
import com.wegrow.wegrow.model.Block;
import java.util.List;

/**
 * Repository：持久层，用于标注数据访问组件，即DAO组件。
 * Service：业务层，用于标注业务逻辑层主键。
 * Controller：控制层，用于标注控制层组件。
 * Component：当你不确定是属于哪一层的时候使用。
 */
public interface DemoService {
    List<Block> listAllBlock();

    int createBlock(BlockDto blockDto);

    int updateBlock(Integer id, BlockDto blockDto);

    int deleteBlock(Integer id);

    List<Block> listBlock(int pageNum, int pageSize);

    Block getBlock(Integer id);
}
