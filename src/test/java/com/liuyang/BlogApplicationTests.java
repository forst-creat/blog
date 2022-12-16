package com.liuyang;

import com.liuyang.bean.Type;
import com.liuyang.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private TypeService typeService;

    @Test
    void contextLoads() {

    }

}
