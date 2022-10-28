package com.solinftec.dev8api.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiUserPostRequestBody {

    @NotEmpty(message = "")
    private String username;

    @NotEmpty(message = "")
    private String password;

    @Email
    @NotEmpty(message = "Email cannot be null")
    private String email;

    private String urlPhoto;

    private Boolean admin;

    private MultipartFile photo;
}
