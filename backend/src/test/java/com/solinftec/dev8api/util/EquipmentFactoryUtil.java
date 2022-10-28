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

    public Equipment changeEquipmentSaved() {
        return Equipment.builder()
                .description("Descascador")
                .color("#000000")
                .latitude(45)
                .longitude(90)
                .build();
    }

    public Equipment createEquipmentWithoutDescription() {
        return Equipment.builder()
                .id(1L)
                .color("#FFFFFF")
                .latitude(-91)
                .longitude(180)
                .build();
    }

    public Equipment createEquipmentWithoutColor() {
        return Equipment.builder()
                .id(1L)
                .description("Perfuradeira")
                .latitude(-91)
                .longitude(180)
                .build();
    }

    public Equipment createEquipmentWithBiggerColor() {
        return Equipment.builder()
                .id(1L)
                .description("Triturador")
                .color("#FFFFFFF")
                .latitude(90)
                .longitude(180)
                .build();
    }

    public Equipment createEquipmentWithBiggerLatitude() {
        return Equipment.builder()
                .id(1L)
                .description("Perfuradeira")
                .color("#FFFFFF")
                .latitude(91)
                .longitude(180)
                .build();
    }

    public Equipment createEquipmentWithBiggerLongitude() {
        return Equipment.builder()
                .id(1L)
                .description("Perfuradeira")
                .color("#FFFFFF")
                .latitude(90)
                .longitude(181)
                .build();
    }

    public Equipment createEquipmentWithSmallerColor() {
        return Equipment.builder()
                .id(1L)
                .description("Triturador")
                .color("#FFFFF")
                .latitude(90)
                .longitude(180)
                .build();
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

    public Equipment createEquipmentWithSmallerLongitude() {
        return Equipment.builder()
                .id(1L)
                .description("Perfuradeira")
                .color("#FFFFFF")
                .latitude(90)
                .longitude(-181)
                .build();
    }
}
