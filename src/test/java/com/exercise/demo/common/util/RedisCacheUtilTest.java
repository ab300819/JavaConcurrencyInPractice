package com.exercise.demo.common.util;

import com.exercise.demo.message.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheUtilTest {

    @Autowired
    RedisCacheUtil redisCacheUtil;

    @Test
    public void testGet() {
        String source1="hello";
        MessageDto source2=new MessageDto();
        source2.setContent("hello world");

        redisCacheUtil.set("target1",source1);
        assertThat("hello",equalTo( redisCacheUtil.get("target1")));

        redisCacheUtil.set("target2",source2);
        MessageDto messageDto=redisCacheUtil.get("target2");
        log.debug("{}",messageDto);
    }

}