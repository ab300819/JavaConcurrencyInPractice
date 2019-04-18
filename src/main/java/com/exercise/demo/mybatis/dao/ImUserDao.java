package com.exercise.demo.mybatis.dao;

import com.exercise.demo.mybatis.dto.ImUserDto;

public interface ImUserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ImUserDto record);

    int insertSelective(ImUserDto record);

    ImUserDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImUserDto record);

    int updateByPrimaryKey(ImUserDto record);
}