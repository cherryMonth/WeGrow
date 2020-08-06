package com.wegrow.wegrow.mapper;

import com.wegrow.wegrow.model.BlockComment;
import com.wegrow.wegrow.model.BlockCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BlockCommentMapper {
    long countByExample(BlockCommentExample example);

    int deleteByExample(BlockCommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BlockComment record);

    int insertSelective(BlockComment record);

    List<BlockComment> selectByExample(BlockCommentExample example);

    BlockComment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BlockComment record, @Param("example") BlockCommentExample example);

    int updateByExample(@Param("record") BlockComment record, @Param("example") BlockCommentExample example);

    int updateByPrimaryKeySelective(BlockComment record);

    int updateByPrimaryKey(BlockComment record);
}