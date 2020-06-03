package com.wegrow.wegrow.mapper;

import com.wegrow.wegrow.model.UsersExtends;
import com.wegrow.wegrow.model.UsersExtendsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersExtendsMapper {
    long countByExample(UsersExtendsExample example);

    int deleteByExample(UsersExtendsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UsersExtends record);

    int insertSelective(UsersExtends record);

    List<UsersExtends> selectByExample(UsersExtendsExample example);

    UsersExtends selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UsersExtends record, @Param("example") UsersExtendsExample example);

    int updateByExample(@Param("record") UsersExtends record, @Param("example") UsersExtendsExample example);

    int updateByPrimaryKeySelective(UsersExtends record);

    int updateByPrimaryKey(UsersExtends record);
}