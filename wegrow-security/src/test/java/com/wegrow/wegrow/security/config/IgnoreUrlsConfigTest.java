package com.wegrow.wegrow.security.config;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class IgnoreUrlsConfigTest {

    @Autowired
    IgnoreUrlsConfig ignoreUrlsConfig;

    @org.junit.jupiter.api.Test
    void getUrls() {
        System.out.println(ignoreUrlsConfig.getUrls());
    }
}