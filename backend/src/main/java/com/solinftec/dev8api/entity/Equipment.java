package com.solinftec.dev8api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "")
    private String description;

    @NotEmpty(message = "")
    @Size(min = 7, max = 7, message = "Color must have 7 characters long")
    private String color;

    @NotNull(message = "")
    @Max(value = 90, message = "Latitude deve estar entre -90 e 90")
    @Min(value = -90, message = "Latitude deve estar entre -90 e 90")
    private double latitude;

    @NotNull(message = "")
    @Max(value = 180, message = "Longitude deve estar entre -180 e 180")
    @Min(value = -180, message = "Longitude deve estar entre -180 e 180")
    private double longitude;
}
