package com.exercise.demo.controller;

import com.exercise.demo.mybatis.dao.CountryDao;
import com.exercise.demo.mybatis.dto.CountryDto;
import com.exercise.demo.aspect.component.Animal;
import com.exercise.demo.aspect.component.Food;
import com.exercise.demo.aspect.component.Person;
import com.exercise.demo.aspect.component.Performance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lenovo on 2017/8/17.
 */

@RestController
public class ExternalController {

    private static Logger logger = LoggerFactory.getLogger(ExternalController.class);

    @Autowired
    CountryDao countryDao;

    @Autowired
    Animal animal;

    @Autowired
    Performance performance;

    @Autowired
    Person person;

    @GetMapping("/ws")
    @ResponseBody
    public CountryDto serviceController() {

        animal.walk(1);
        performance.perform();
        person.speak();
        ((Food) person).eat();
        CountryDto result = countryDao.selectCountry(1L);
        logger.debug(result.toString());
        return result;
    }
}
