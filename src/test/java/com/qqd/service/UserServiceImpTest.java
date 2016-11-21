package com.qqd.service;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by liujianyang on 2016/11/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImpTest {

    @Autowired
    UserService userService;
    @Test
    public void findUserByName() throws Exception {

        System.out.println("----------------"+JSON.toJSONString(userService.findUserByName("cat"),true));
    }

}