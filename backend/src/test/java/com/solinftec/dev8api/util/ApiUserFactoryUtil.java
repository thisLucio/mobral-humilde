package com.solinftec.dev8api.util;

import com.solinftec.dev8api.entity.ApiUser;

public class ApiUserFactoryUtil {

    public ApiUser createUserToBeSaved() {
        return ApiUser.builder()
                .username("samuel")
                .password("samuel")
                .email("samuel@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }

    public ApiUser createUserToBeSavedWithUsernameSame() {
        ApiUser apiUser;
        apiUser = createUserToBeSaved();
        apiUser.setEmail("samuel-falico@email.com");
        return apiUser;
    }

    public ApiUser createUserToBeSavedWithEmailSame() {
        ApiUser apiUser;
        apiUser = createUserToBeSaved();
        apiUser.setUsername("samuel-falico");
        return apiUser;
    }

    public ApiUser createOtherUserToBeSaved() {
        return ApiUser.builder()
                .username("gabriel")
                .password("gabriel")
                .email("gabriel@email.com")
                .urlPhoto("snoopy.png")
                .admin(false)
                .build();
    }

    public ApiUser createUserSaved() {
        return ApiUser.builder()
                .id(1L)
                .username("samuel")
                .password("samuel")
                .email("samuel@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }

    public ApiUser changeUserSaved() {
        return ApiUser.builder()
                .id(1L)
                .username("carlos")
                .password("carlos")
                .email("carlos@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }

    public ApiUser createUserWithUsernameEmpty() {
        return ApiUser.builder()
                .id(1L)
                .password("samuel")
                .email("samuel@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }

    public ApiUser createUserWithPasswordEmpty() {
        return ApiUser.builder()
                .id(1L)
                .username("samuel")
                .email("samuel@email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }

    public ApiUser createUserWithEmailEmpty() {
        return ApiUser.builder()
                .id(1L)
                .username("samuel")
                .password("samuel")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }

    public ApiUser createUserWithEmailNotValidated() {
        return ApiUser.builder()
                .id(1L)
                .username("samuel")
                .password("samuel")
                .email("samuel-email.com")
                .urlPhoto("unknown.png")
                .admin(false)
                .build();
    }
}
