package com.solinftec.dev8api.util;

import com.solinftec.dev8api.requests.ApiUserPostRequestBody;

public class ApiUserPostRequestBodyFactoryUtil {

    public ApiUserPostRequestBody createUserToBeSaved() {
        return ApiUserPostRequestBody.builder()
                .username("samuel")
                .password("samuel")
                .email("samuel@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }
}
