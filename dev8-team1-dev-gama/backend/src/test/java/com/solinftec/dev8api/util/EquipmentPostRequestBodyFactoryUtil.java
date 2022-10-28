package com.solinftec.dev8api.util;

import com.solinftec.dev8api.requests.EquipmentPostRequestBody;

public class EquipmentPostRequestBodyFactoryUtil {

    public EquipmentPostRequestBody createEquipmentToBeSaved() {
        return EquipmentPostRequestBody.builder()
                .description("Triturador")
                .color("#FFFFFF")
                .latitude(90)
                .longitude(180)
                .build();
    }

    public EquipmentPostRequestBody createEquipmentWithoutColor() {
        return EquipmentPostRequestBody.builder()
                .description("Perfuradeira")
                .latitude(90)
                .longitude(180)
                .build();
    }

    public EquipmentPostRequestBody createEquipmentWithColorNameInvalid() {
        return EquipmentPostRequestBody.builder()
                .description("Perfuradeira")
                .color("amarela")
                .latitude(90)
                .longitude(180)
                .build();
    }
}
