package com.exercise.demo.schedule;

import com.exercise.demo.common.controller.ProcessController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

//@Component
public class Schedule {

    private static Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @Scheduled(cron = "0/5 * * * * ?")
    public void outputEveryFiveSecond() {

        logger.debug("计数：{}", nowTime(new Date()));
    }

    private String nowTime(Date date) {

        LocalTime localTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        String hour = localTime.getHour() < 10 ? "0" + localTime.getHour() : String.valueOf(localTime.getHour());
        String minute = localTime.getMinute() < 10 ? "0" + localTime.getMinute() : String.valueOf(localTime.getMinute());
        String second = localTime.getSecond() < 10 ? "0" + localTime.getSecond() : String.valueOf(localTime.getSecond());

        return hour + ":" + minute + ":" + second;

    }

}
