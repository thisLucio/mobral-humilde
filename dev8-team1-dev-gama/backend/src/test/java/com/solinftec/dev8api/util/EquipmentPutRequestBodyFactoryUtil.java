package com.solinftec.dev8api.util;

import com.solinftec.dev8api.requests.EquipmentPutRequestBody;

public class EquipmentPutRequestBodyFactoryUtil {

    public EquipmentPutRequestBody createEquipmentToBeSaved() {
        return EquipmentPutRequestBody.builder()
                .id(0L)
                .description("Triturador")
                .color("#FFFFFF")
                .latitude(90)
                .longitude(180)
                .build();
    }
}
