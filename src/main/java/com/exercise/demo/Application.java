package com.exercise.demo;

import com.exercise.demo.common.util.CommonUtil;
import com.exercise.demo.mybatis.dao.ImUserDao;
import com.exercise.demo.mybatis.dto.ImUserDto;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 */
@MapperScan(value = "com.exercise.demo.mybatis")
@EnableScheduling
@SpringBootApplication
public class Application {

    private final ImUserDao imUserDao;

    @Autowired
    public Application(ImUserDao imUserDao) {
        this.imUserDao = imUserDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            ImUserDto userDto1 = new ImUserDto();
            userDto1.setUserId(CommonUtil.getUUID());
            userDto1.setUserName("test1");
            imUserDao.insertSelective(userDto1);



        };
    }

}
