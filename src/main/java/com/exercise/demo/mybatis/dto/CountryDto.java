package com.exercise.demo.mybatis.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Project: ExerciseTimer
 * Package: com.exercise.database
 * Author: mason
 * Time: 13:54
 * Date: 2018-03-09
 * Created with IntelliJ IDEA
 */
public class CountryDto {

    private long id;
    private String countryName;
    private String countryCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.valueToTree(this);
        return json.toString();
    }
}
