package com.solinftec.dev8api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiUserPostRequestBody {

    @NotEmpty(message = "")
    private String username;

    @NotEmpty(message = "")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "PASSWORD_WEAK")
    @JsonProperty("password")
    private String password;

    @Email
    @NotEmpty(message = "Email cannot be null")
    private String email;

    private String urlPhoto;

    private Boolean admin;

    private MultipartFile photo;
}
