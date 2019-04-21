package com.exercise.demo.mybatis.dao;

import com.exercise.demo.mybatis.dto.ImMessageDto;

public interface ImMessageDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ImMessageDto record);

    int insertSelective(ImMessageDto record);

    ImMessageDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImMessageDto record);

    int updateByPrimaryKey(ImMessageDto record);
}