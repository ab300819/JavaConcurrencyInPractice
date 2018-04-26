package com.exercise.demo.Controller;

import com.exercise.demo.app.dao.CountryDao;
import com.exercise.demo.app.dto.CountryDto;
import com.exercise.demo.app.service.Food;
import com.exercise.demo.app.service.Person;
import com.exercise.demo.aspect.Performance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lenovo on 2017/8/17.
 */

@Controller
public class ExternalController {

    private static Logger logger = LoggerFactory.getLogger(ExternalController.class);

    @Autowired
    CountryDao countryDao;

    @Autowired
    Performance performanceImpl;

    @Autowired
    Person personImpl;

    @RequestMapping("/ws")
    @ResponseBody
    public CountryDto serviceController() {

        performanceImpl.perform();
        personImpl.speak();
        ((Food) personImpl).eat();
        CountryDto result = countryDao.selectCountry(1L);
        logger.debug(result.toString());
        return result;
    }
}
