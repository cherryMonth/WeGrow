package com.wegrow.wegrow.mapper;

import com.wegrow.wegrow.model.BlockParentChildMap;
import com.wegrow.wegrow.model.BlockParentChildMapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlockParentChildMapMapper {
    long countByExample(BlockParentChildMapExample example);

    int deleteByExample(BlockParentChildMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BlockParentChildMap record);

    int insertSelective(BlockParentChildMap record);

    List<BlockParentChildMap> selectByExample(BlockParentChildMapExample example);

    BlockParentChildMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BlockParentChildMap record, @Param("example") BlockParentChildMapExample example);

    int updateByExample(@Param("record") BlockParentChildMap record, @Param("example") BlockParentChildMapExample example);

    int updateByPrimaryKeySelective(BlockParentChildMap record);

    int updateByPrimaryKey(BlockParentChildMap record);
}