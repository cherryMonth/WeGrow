package com.wegrow.wegrow.demo.config;

import com.wegrow.wegrow.demo.authentication.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// @EnableWebSecurity是Spring Security用于启用Web安全的注解。
@EnableWebSecurity(debug = true) // 打印出请求的详细信息
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")  // 设置访问ADMIN角色才能访问的页面
                .antMatchers("/user/api/**").hasRole("USER")  // 设置访问USER角色才能访问的页面
                .antMatchers("/app/api/**").permitAll()  // 这个页面允许游客访问
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }

    @Bean // 创建一个User Bean并交给Spring进行管理
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // 添加一个用户，并且这个用户的角色为普通用户
        manager.createUser(User.withUsername("user").password("123").roles("USER").build());

        // 添加一个用户，并且这个用户的角色为管理员和普通用户
        manager.createUser(User.withUsername("admin").password("123").roles("USER", "ADMIN").build());
        return manager;
    }

    @Bean // 接口用于执行密码的单向转换，以便安全地存储密码，返回一个密码加密器
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
