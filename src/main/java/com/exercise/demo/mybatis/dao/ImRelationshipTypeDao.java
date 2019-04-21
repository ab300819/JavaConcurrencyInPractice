package com.exercise.demo.mybatis.dao;

import com.exercise.demo.mybatis.dto.ImRelationshipTypeDto;

public interface ImRelationshipTypeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ImRelationshipTypeDto record);

    int insertSelective(ImRelationshipTypeDto record);

    ImRelationshipTypeDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImRelationshipTypeDto record);

    int updateByPrimaryKey(ImRelationshipTypeDto record);
}