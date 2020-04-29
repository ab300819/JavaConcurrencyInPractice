package com.exercise.demo.mybatis.dao;

import com.exercise.demo.mybatis.dto.ImRelationshipDto;

public interface ImRelationshipDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ImRelationshipDto record);

    int insertSelective(ImRelationshipDto record);

    ImRelationshipDto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ImRelationshipDto record);

    int updateByPrimaryKey(ImRelationshipDto record);
}