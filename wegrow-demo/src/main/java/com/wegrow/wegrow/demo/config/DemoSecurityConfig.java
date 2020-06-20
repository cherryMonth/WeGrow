package com.wegrow.wegrow.demo.config;

import com.wegrow.wegrow.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.wegrow.wegrow.demo.service.UserService;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DemoSecurityConfig extends SecurityConfig {
    @Autowired
    private UserService demoAdminService;

    @Bean
    public UserDetailsService userDetailsService() {
        // java 8 lambda表达式
        return username -> demoAdminService.loadUserByUsername(username);
    }
}
