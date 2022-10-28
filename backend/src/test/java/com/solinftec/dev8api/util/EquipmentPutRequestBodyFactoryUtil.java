package com.solinftec.dev8api.util;

import com.solinftec.dev8api.requests.EquipmentPostRequestBody;
import com.solinftec.dev8api.requests.EquipmentPutRequestBody;

public class EquipmentPutRequestBodyFactoryUtil {

    public EquipmentPutRequestBody createEquipmentToBeSaved() {
        return EquipmentPutRequestBody.builder()
                .id(1L)
                .description("Pulverizador")
                .color("#FFFFFF")
                .latitude(45)
                .longitude(90)
                .build();
    }

    public EquipmentPutRequestBody createEquipmentWithoutColor() {
        return EquipmentPutRequestBody.builder()
                .id(1L)
                .description("Pulverizador")
                .latitude(45)
                .longitude(90)
                .build();
    }

    public EquipmentPutRequestBody createEquipmentWithColorNameInvalid() {
        return EquipmentPutRequestBody.builder()
                .id(1L)
                .description("Pulverizador")
                .color("amarela")
                .latitude(45)
                .longitude(90)
                .build();
    }
}
