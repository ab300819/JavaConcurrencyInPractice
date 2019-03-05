package com.exercise.demo.mybatis.dao;

import com.exercise.demo.mybatis.dto.CountryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;


/**
 * Project: ExerciseTimer
 * Package: com.exercise.database.dao
 * Author: mason
 * Time: 11:33
 * Date: 2018-03-29
 * Created with IntelliJ IDEA
 * https://zhuanlan.zhihu.com/p/32754570
 */
@Mapper
public interface CountryDao {

    @SelectProvider(type = CountryDto.class,method = "selectCountry")
    CountryDto selectCountry(@Param("id") Long id);

}
