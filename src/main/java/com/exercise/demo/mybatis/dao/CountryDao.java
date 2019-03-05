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
 */
@Mapper
public interface CountryDao {

    String returnSql = "id,country_name,country_code";

    @SelectProvider(type = CountryDaoProvider.class, method = "selectCountry")
    CountryDto selectCountry(CountryDto countryDto);

    class CountryDaoProvider {

        public String selectCountry(CountryDto countryDto) {

            String sql = "SELECT " + returnSql + " FROM country";
            if (countryDto.getId() != null) {
                sql += " WHERE id=#{id}";
            }
            return sql;
        }
    }
}
