package com.solinftec.dev8api.util;

import com.solinftec.dev8api.dto.ApiUserDTO;

public class ApiUserDTOFactoryUtil {

    public ApiUserDTO createUserDTOSaved() {
        return ApiUserDTO.builder()
                .id(1L)
                .username("fabio")
                .email("fabio@email.com")
                .photoUrl("unknown.png")
                .admin(false)
                .enabled(false)
                .build();
    }
}
