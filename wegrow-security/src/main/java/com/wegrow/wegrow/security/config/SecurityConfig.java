package com.wegrow.wegrow.security.config;

import com.wegrow.wegrow.security.component.DynamicSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired(required = false) // 表示忽略当前要注入的bean，如果有直接注入，没有跳过，不会报错。
    private DynamicSecurityService dynamicSecurityService;


}
