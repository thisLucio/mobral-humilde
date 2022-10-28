package com.solinftec.equipment.equipmentchat.dtos;

import com.solinftec.equipment.equipmentchat.enums.OperationType;

public class EquipmentDto {
    private Long id;

    private String description;

    private String color;

    private double latitude;

    private double longitude;

    private OperationType operationType;

    public EquipmentDto(Long id, String description, String color, double latitude, double longitude, OperationType operationType ) {
        this.id = id;
        this.description = description;
        this.color = color;
        this.latitude = latitude;
        this.longitude = longitude;
        this.operationType = operationType;
    }

    public EquipmentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "EquipmentDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", operationType=" + operationType +
                '}';
    }
}
