package com.wegrow.wegrow.mapper;

import com.wegrow.wegrow.model.LocalAuth;
import com.wegrow.wegrow.model.LocalAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LocalAuthMapper {
    long countByExample(LocalAuthExample example);

    int deleteByExample(LocalAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LocalAuth record);

    int insertSelective(LocalAuth record);

    List<LocalAuth> selectByExample(LocalAuthExample example);

    LocalAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LocalAuth record, @Param("example") LocalAuthExample example);

    int updateByExample(@Param("record") LocalAuth record, @Param("example") LocalAuthExample example);

    int updateByPrimaryKeySelective(LocalAuth record);

    int updateByPrimaryKey(LocalAuth record);
}