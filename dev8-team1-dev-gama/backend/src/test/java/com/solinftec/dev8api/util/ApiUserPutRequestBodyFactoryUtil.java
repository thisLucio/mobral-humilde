package com.solinftec.dev8api.util;

import com.solinftec.dev8api.requests.ApiUserPutRequestBody;

public class ApiUserPutRequestBodyFactoryUtil {

    public ApiUserPutRequestBody createUserToBeSaved() {
        return ApiUserPutRequestBody.builder()
                .username("samuel")
                .password("samuel")
                .email("samuel@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }

    public ApiUserPutRequestBody createUserWithPhotoURLEmpty() {
        return ApiUserPutRequestBody.builder()
                .id(1L)
                .username("samuel")
                .password("samuel")
                .admin(false)
                .build();
    }
}
