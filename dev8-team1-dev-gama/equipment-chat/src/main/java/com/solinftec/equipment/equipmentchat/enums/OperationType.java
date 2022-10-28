package com.solinftec.equipment.equipmentchat.enums;

public enum OperationType {
    CREATE("CREATE"),
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    public String getDescription() {
        return description;
    }

    private OperationType(String description) {
        this.description = description;
    }

    private String description;
}
