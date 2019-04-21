package com.exercise.demo.mybatis.dto;

import java.util.Date;

/**
 * 关系表
 * im_relationship
 *
 * @author mason
 * @date 2019-04-21 23:56:54
 */
public class ImRelationshipDto {
    /**
     * 主键id
     * im_relationship.id
     */
    private Integer id;

    /**
     * 关系id
     * im_relationship.relationship_id
     */
    private Integer relationshipId;

    /**
     * 关系用户
     * im_relationship.relationship_user
     */
    private Integer relationshipUser;

    /**
     * 关系创建者
     * im_relationship.creator
     */
    private String creator;

    /**
     * 创建时间
     * im_relationship.create_time
     */
    private Date createTime;

    /**
     * 关系修改者
     * im_relationship.modifier
     */
    private String modifier;

    /**
     * 修改时间
     * im_relationship.modify_time
     */
    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Integer relationshipId) {
        this.relationshipId = relationshipId;
    }

    public Integer getRelationshipUser() {
        return relationshipUser;
    }

    public void setRelationshipUser(Integer relationshipUser) {
        this.relationshipUser = relationshipUser;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}