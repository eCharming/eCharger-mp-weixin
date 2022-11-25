package com.example.demo;

import com.example.demo.Service.Redis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    Redis redis;


    @Test
    void contextLoads() {
        redis.setMessage("1","2","111");
    }

}
