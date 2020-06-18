package com.wegrow.wegrow.demo.dao;

import com.wegrow.wegrow.model.LocalAuth;
import org.apache.ibatis.annotations.Param;

/**
 * 这个是通过xml和mapper实现dao
 * 这里使用了userID作为关键字查询密码，由于用户密码输入框不包含serID，所以也要在Service中定义getLocalAuth
 * 然后在Impl中实现这个getLocalAuth，然后在其他的地方只使用service的getLocalAuth即可
 */
public interface UserLocalAuthDao {

    LocalAuth getLocalAuth(@Param("userId") Integer userId);

}
