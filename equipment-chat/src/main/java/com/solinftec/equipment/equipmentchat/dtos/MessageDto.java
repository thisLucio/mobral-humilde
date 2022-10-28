package com.solinftec.equipment.equipmentchat.dtos;

public class MessageDto {
    private Long userId;
    private Long equipmentId;
    private String timestamp;
    private String content;
    private String sender;
    private String to;

    public MessageDto() { }

    public MessageDto(Long userId, Long equipmentId, String timestamp, String content, String sender) {
        this.userId = userId;
        this.equipmentId = equipmentId;
        this.timestamp = timestamp;
        this.content = content;
        this.sender = sender;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
