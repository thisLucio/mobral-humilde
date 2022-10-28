package com.solinftec.dev8api.util;

import com.solinftec.dev8api.requests.ApiUserPutRequestBody;

public class ApiUserPutRequestBodyFactoryUtil {

    public ApiUserPutRequestBody createUserToBeSaved() {
        return ApiUserPutRequestBody.builder()
                .id(1L)
                .username("carlos")
                .password("carlos")
                .email("carlos@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }

    public ApiUserPutRequestBody createUserWithSameUserName() {
        return ApiUserPutRequestBody.builder()
                .id(1L)
                .username("carlos")
                .admin(false)
                .build();
    }

    public ApiUserPutRequestBody createUserWithSameEmail() {
        return ApiUserPutRequestBody.builder()
                .id(1L)
                .email("carlos@email.com")
                .admin(false)
                .build();
    }

    public ApiUserPutRequestBody createUserWithDifferenceUserName() {
        return ApiUserPutRequestBody.builder()
                .id(1L)
                .username("fabio")
                .admin(false)
                .build();
    }

    public ApiUserPutRequestBody createUserWithDifferenceEmail() {
        return ApiUserPutRequestBody.builder()
                .id(1L)
                .email("fabio@email.com")
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
