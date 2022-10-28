package com.solinftec.dev8api.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class UserStructure {
    @NotEmpty
    private String username;

    @NotEmpty
    @Email
    private String email;
}
