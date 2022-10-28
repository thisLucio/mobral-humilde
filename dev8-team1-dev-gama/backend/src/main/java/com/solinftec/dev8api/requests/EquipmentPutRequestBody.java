package com.solinftec.dev8api.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentPutRequestBody {

    @NotNull
    private Long id;

    @NotEmpty(message = "")
    private String description;

    @NotEmpty(message = "")

    @Size(min = 7, max = 7, message = "Color must have 7 characters long")
    private String color;

    @NotNull(message = "")
    @Range(min = -90, max = 90, message = "")
    private double latitude;

    @NotNull(message = "")
    @Range(min = -180, max = 180, message = "")
    private double longitude;
}
