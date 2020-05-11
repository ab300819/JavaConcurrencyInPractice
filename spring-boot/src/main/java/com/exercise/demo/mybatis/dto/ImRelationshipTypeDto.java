package com.exercise.demo.mybatis.dto;

/**
 * 关系类型表
 * im_relationship_type
 *
 * @author mason
 * @date 2019-04-21 23:56:54
 */
public class ImRelationshipTypeDto {
    /**
     * 主键 id
     * im_relationship_type.id
     */
    private Integer id;

    /**
     * 关系名称
     * im_relationship_type.relationship
     */
    private String relationship;

    /**
     * 关系类型，0-好友，1-群聊
     * im_relationship_type.type
     */
    private Byte type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}