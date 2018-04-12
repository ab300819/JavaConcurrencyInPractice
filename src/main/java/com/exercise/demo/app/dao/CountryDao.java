package com.exercise.demo.app.dao;

import com.exercise.demo.app.dto.CountryDto;
import org.apache.ibatis.annotations.Param;


/**
 * Project: ExerciseTimer
 * Package: com.exercise.database.dao
 * Author: mason
 * Time: 11:33
 * Date: 2018-03-29
 * Created with IntelliJ IDEA
 */
public interface CountryDao {

    CountryDto selectCountry(@Param("id") Long id);

}
