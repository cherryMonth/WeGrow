package com.wegrow.wegrow.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置类
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.wegrow.wegrow.mapper", "com.wegrow.wegrow.demo.dao"})
public class MyBatisConfig {
}
