package com.exercise.demo.mybatis.dto;

import java.util.Date;

/**
 * 消息表
 * im_message
 *
 * @author mason
 * @date 2019-04-21 23:56:54
 */
public class ImMessageDto {
    /**
     * 主键id
     * im_message.id
     */
    private Integer id;

    /**
     * 关系id
     * im_message.relationship_id
     */
    private String relationshipId;

    /**
     * 消息内容
     * im_message.message
     */
    private String message;

    /**
     * 消息类型
     * im_message.message_type
     */
    private Byte messageType;

    /**
     * 创建者
     * im_message.creator
     */
    private String creator;

    /**
     * 创建时间
     * im_message.create_time
     */
    private Date createTime;

    /**
     * 修改者
     * im_message.modifier
     */
    private String modifier;

    /**
     * 修改时间
     * im_message.modifier_time
     */
    private Date modifierTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Byte getMessageType() {
        return messageType;
    }

    public void setMessageType(Byte messageType) {
        this.messageType = messageType;
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

    public Date getModifierTime() {
        return modifierTime;
    }

    public void setModifierTime(Date modifierTime) {
        this.modifierTime = modifierTime;
    }
}