package com.wegrow.wegrow.mapper;

import com.wegrow.wegrow.model.BlockExtends;
import com.wegrow.wegrow.model.BlockExtendsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlockExtendsMapper {
    long countByExample(BlockExtendsExample example);

    int deleteByExample(BlockExtendsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BlockExtends record);

    int insertSelective(BlockExtends record);

    List<BlockExtends> selectByExample(BlockExtendsExample example);

    BlockExtends selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BlockExtends record, @Param("example") BlockExtendsExample example);

    int updateByExample(@Param("record") BlockExtends record, @Param("example") BlockExtendsExample example);

    int updateByPrimaryKeySelective(BlockExtends record);

    int updateByPrimaryKey(BlockExtends record);
}