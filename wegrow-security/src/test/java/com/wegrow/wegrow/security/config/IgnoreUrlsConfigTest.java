package com.wegrow.wegrow.security.config;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)  // SpringRunner 继承了SpringJUnit4ClassRunner，没有扩展任何功能；使用前者，名字简短而已。
class IgnoreUrlsConfigTest {

    @Autowired
    IgnoreUrlsConfig ignoreUrlsConfig;

    @Test
    void setUrls() {
    }

    @Test
    void getUrls() {
        System.out.println(ignoreUrlsConfig.getUrls());
    }

    @Test
    void testSetUrls() {
    }

    @Test
    void testGetUrls() {
        System.out.println(ignoreUrlsConfig.getUrls().size());
    }
}