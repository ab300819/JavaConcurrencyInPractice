package com.exercise.demo.common;

import com.exercise.demo.aspect.Audience;
import com.exercise.demo.aspect.Audiences;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configure {

    @Bean
    public Audience audience() {
        return new Audience();
    }

    @Bean
    public Audiences audiences() {
        return new Audiences();
    }

}
