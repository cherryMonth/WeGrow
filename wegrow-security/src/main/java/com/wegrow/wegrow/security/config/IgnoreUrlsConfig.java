package com.wegrow.wegrow.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.ignored") // 读取配置文件中的默认忽略url
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();
}
