package com.wegrow.wegrow.demo.config;

import com.wegrow.wegrow.demo.authentication.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @EnableWebSecurity是Spring Security用于启用Web安全的注解。
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/myLogin.html")
                // 指定Login的controller
                .loginProcessingUrl("/login")
                .successHandler(new SecurityAuthenticationSuccessHandler())
                .failureHandler(new SecurityAuthenticationFailureHandler())
                // 使登录页不设限访问
                .permitAll()
                .and()
                .csrf().disable();
    }
}
