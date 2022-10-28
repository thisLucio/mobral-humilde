package com.solinftec.dev8api.util;

import com.solinftec.dev8api.entity.Equipment;

public class EquipmentFactoryUtil {

    public Equipment createEquipmentToBeSaved() {
        return Equipment.builder()
                .description("Triturador")
                .color("#FFFFFF")
                .latitude(90)
                .longitude(180)
                .build();
    }

    public Equipment createOtherEquipmentToBeSaved() {
        return Equipment.builder()
                .description("Perfuradeira")
                .color("#FFFFFF")
                .latitude(90)
                .longitude(180)
                .build();
    }

    public Equipment createEquipmentSaved() {
        return Equipment.builder()
                .id(1L)
                .description("Triturador")
                .color("#FFFFFF")
                .latitude(90)
                .longitude(180)
                .build();
    }

    public Equipment changeEquipment(Equipment equipment) {
        equipment.setDescription("Descascador");
        equipment.setColor("#000000");
        equipment.setLatitude(45);
        equipment.setLongitude(90);
        return equipment;
    }

    public Equipment createEquipmentWithSmallerLatitude() {
        return Equipment.builder()
                .id(1L)
                .description("Perfuradeira")
                .color("#FFFFFF")
                .latitude(-91)
                .longitude(180)
                .build();
    }

    public Equipment createEquipmentWithLargerLatitude() {
        return Equipment.builder()
                .id(1L)
                .description("Perfuradeira")
                .color("#FFFFFF")
                .latitude(91)
                .longitude(180)
                .build();
    }

    public Equipment createEquipmentWithSmallerLongitude() {
        return Equipment.builder()
                .id(1L)
                .description("Perfuradeira")
                .color("#FFFFFF")
                .latitude(90)
                .longitude(-181)
                .build();
    }

    public Equipment createEquipmentWithLargerLongitude() {
        return Equipment.builder()
                .id(1L)
                .description("Perfuradeira")
                .color("#FFFFFF")
                .latitude(90)
                .longitude(181)
                .build();
    }

    public Equipment createEquipmentWithSizeOversizedColor() {
        return Equipment.builder()
                .id(1L)
                .description("Triturador")
                .color("#FFFFFFF")
                .latitude(90)
                .longitude(180)
                .build();
    }
}
