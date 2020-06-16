package com.wegrow.wegrow.demo.dao;

import com.wegrow.wegrow.model.LocalAuth;
import org.apache.ibatis.annotations.Param;


public interface UserLocalAuthDao {

    LocalAuth getLocalAuth(@Param("useId") Integer userId);

}
