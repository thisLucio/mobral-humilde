package com.solinftec.dev8api.kafka;

import com.solinftec.dev8api.entity.Equipment;
import com.solinftec.dev8api.enums.OperationType;
import lombok.Data;

@Data
public class EquipmentStructure {
    private Long id;
    private String description;
    private String color;
    private double latitude;
    private double longitude;
    private OperationType operationType;

    public EquipmentStructure(Equipment equipment, OperationType operationType) {
        this.id = equipment.getId();
        this.description = equipment.getDescription();
        this.color = equipment.getColor();
        this.latitude = equipment.getLatitude();
        this.longitude = equipment.getLongitude();
        this.operationType = operationType;
    }
}