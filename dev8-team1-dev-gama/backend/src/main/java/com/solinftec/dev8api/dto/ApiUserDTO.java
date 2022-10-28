package com.solinftec.dev8api.dto;

import com.solinftec.dev8api.entity.ApiUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiUserDTO {

    private Long id;
    private String username;
    private String email;
    private String photoUrl;
    private Boolean admin;

    public ApiUserDTO(ApiUser apiUser) {
        this.id = apiUser.getId();
        this.username = apiUser.getUsername();
        this.email = apiUser.getEmail();
        this.photoUrl = apiUser.getUrlPhoto();
        this.admin = apiUser.isAdmin();
    }
}
