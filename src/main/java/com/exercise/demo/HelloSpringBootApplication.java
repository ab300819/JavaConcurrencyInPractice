package com.exercise.demo;

import com.exercise.demo.aspect.Audience;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Hello world!
 */

@SpringBootApplication
@MapperScan(basePackages = {"com.exercise.demo"})
public class HelloSpringBootApplication {

    @Bean
    public Audience audience() {
        return new Audience();
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringBootApplication.class, args);
    }


}
