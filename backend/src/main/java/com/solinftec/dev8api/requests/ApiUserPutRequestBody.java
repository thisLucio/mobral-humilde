package com.solinftec.dev8api.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiUserPutRequestBody {

    @NotNull
    private long id;

    private String username;

    private String password;

    @Email
    private String email;

    private String urlPhoto;

    private Boolean admin;

    private MultipartFile photo;
}
