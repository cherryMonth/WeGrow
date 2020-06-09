package com.wegrow.wegrow.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

// @EnableWebSecurity是Spring Security用于启用Web安全的注解。
@EnableWebSecurity(debug = true) // 打印出请求的详细信息
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()  // authorizeRequests()下管理路径访问控制
                // hasRole会自动拼接ROLE_，如果不想拼接直接使用数据库字段，则使用hasAuthority
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest().authenticated()  // 所有请求都要添加验证
                .and()
                .formLogin().permitAll();  // 只有formLogin管理登陆表单配置，允许所有用户访问
    }

    @Bean // 接口用于执行密码的单向转换，以便安全地存储密码，返回一个密码加密器
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
