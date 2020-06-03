package com.wegrow.wegrow.mapper;

import com.wegrow.wegrow.model.OAuth;
import com.wegrow.wegrow.model.OAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OAuthMapper {
    long countByExample(OAuthExample example);

    int deleteByExample(OAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OAuth record);

    int insertSelective(OAuth record);

    List<OAuth> selectByExample(OAuthExample example);

    OAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OAuth record, @Param("example") OAuthExample example);

    int updateByExample(@Param("record") OAuth record, @Param("example") OAuthExample example);

    int updateByPrimaryKeySelective(OAuth record);

    int updateByPrimaryKey(OAuth record);
}