package com.solinftec.email.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class EmailStructure {
    @NotEmpty
    private String username;

    @NotEmpty
    @Email
    private String email;
}
