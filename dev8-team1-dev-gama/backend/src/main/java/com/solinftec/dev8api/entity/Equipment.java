package com.solinftec.dev8api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Range(min = -90, max = 90, message = "")
    private double latitude;

    @NotNull(message = "")
    @Range(min = -180, max = 180, message = "")
    private double longitude;
}
