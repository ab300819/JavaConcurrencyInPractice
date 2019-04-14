package com.exercise.demo.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
//@Component
public class Schedule {


    @Scheduled(cron = "0/5 * * * * ?")
    public void outputEveryFiveSecond() {

        log.debug("计数：{}", nowTime(new Date()));
    }

    private String nowTime(Date date) {

        LocalTime localTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        String hour = localTime.getHour() < 10 ? "0" + localTime.getHour() : String.valueOf(localTime.getHour());
        String minute = localTime.getMinute() < 10 ? "0" + localTime.getMinute() : String.valueOf(localTime.getMinute());
        String second = localTime.getSecond() < 10 ? "0" + localTime.getSecond() : String.valueOf(localTime.getSecond());

        return hour + ":" + minute + ":" + second;

    }

}
