package com.exercise.demo.common.config;

import com.exercise.demo.message.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Test
    public void cacheTest(){
        String source1="hello";
        MessageDto source2=new MessageDto();
        source2.setContent("hello world");

        redisTemplate.opsForValue().set("target1",source1);
        assertThat("hello",equalTo( redisTemplate.opsForValue().get("target1")));

        redisTemplate.opsForValue().set("target2",source2);
        log.debug("{}",redisTemplate.opsForValue().get("target2"));
    }

}