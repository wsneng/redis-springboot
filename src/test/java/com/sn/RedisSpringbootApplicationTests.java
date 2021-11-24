package com.sn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sn.domain.User;
import com.sn.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@SpringBootTest
class RedisSpringbootApplicationTests {
    // spring当中提供了一个对象 RedisTemplate
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {
        // 操作redis的一个对象 BoundValueOperations 对象封装了key对应的内容，
        // 可以设置key的值 可以获得key的值，获得key的过期时间，设置key的过期时间
        BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps("users");

        // 获得key对应的值
        Object o = ops.get();

        if (o == null) {  // 从redis当中没有获得值
            List<User> allUsers = userMapper.findAllUsers();
            System.out.println("从数据库当中获得值：" + allUsers);

            // 将获得值存放在redis中
            //将list集合转换成一个json
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String allUser = objectMapper.writeValueAsString(allUsers);
                ops.set(allUser);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("从redis获得值：" + o);
        }
    }


/*
    @Autowired
    UserMapper userMapper;
    @Test
    void TestMybatis() {
        List<User> allUsers = userMapper.findAllUsers();
        System.out.println(allUsers);
    }*/
}
