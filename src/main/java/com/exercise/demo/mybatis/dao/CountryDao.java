package com.exercise.demo.mybatis.dao;

import com.exercise.demo.mybatis.dto.CountryDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;


/**
 * @author mason
 * @date 2019-03-29
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
