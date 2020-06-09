package com.wegrow.wegrow.security.component;

import java.util.Map;

import javax.security.auth.login.Configuration;

/**
 * 动态权限相关业务类
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     */
    Map<String, Configuration> loadDataSource();
}
