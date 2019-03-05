package com.exercise.demo.common.controller;

import com.exercise.demo.annotation.TestArg;
import com.exercise.demo.common.dto.Parameter;
import com.exercise.demo.mybatis.dao.CountryDao;
import com.exercise.demo.mybatis.dto.CountryDto;
import com.exercise.demo.aspect.component.Animal;
import com.exercise.demo.aspect.component.Food;
import com.exercise.demo.aspect.component.Person;
import com.exercise.demo.aspect.component.Performance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lenovo on 2017/8/17.
 */

@RestController
public class ProcessController {

    private static Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Autowired
    CountryDao countryDao;

    @Autowired
    Animal animal;

    @Autowired
    Performance performance;

    @Autowired
    Person person;

    @PostMapping("/ws")
    public CountryDto serviceController(@TestArg(success = false,num = 10,message = "Fuck you!") Parameter parameter) {

        animal.walk(1);
        performance.perform();
        person.speak();
        ((Food) person).eat();
        CountryDto result = countryDao.selectCountry(1L);
        logger.debug(result.toString());
        logger.debug("{}", parameter);

        return result;
    }
}
