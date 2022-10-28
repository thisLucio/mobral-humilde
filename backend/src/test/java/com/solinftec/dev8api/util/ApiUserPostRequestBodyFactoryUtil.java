package com.solinftec.dev8api.util;

import com.solinftec.dev8api.requests.ApiUserPostRequestBody;

public class ApiUserPostRequestBodyFactoryUtil {

    public ApiUserPostRequestBody createUserToBeSaved() {
        return ApiUserPostRequestBody.builder()
                .username("samuel")
                .password("9r%3ZF")
                .email("samuel@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }

    public ApiUserPostRequestBody createUserWithPasswordCorrect() {
        return ApiUserPostRequestBody.builder()
                .username("samuel")
                .password("9r%3ZF")
                .email("samuel@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }

    public ApiUserPostRequestBody createUserWithLowerPassword() {
        return ApiUserPostRequestBody.builder()
                .username("samuel")
                .password("mpbwgf")
                .email("samuel@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }

    public ApiUserPostRequestBody createUserWithUpperPassword() {
        return ApiUserPostRequestBody.builder()
                .username("samuel")
                .password("CWRXJH")
                .email("samuel@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }
}
