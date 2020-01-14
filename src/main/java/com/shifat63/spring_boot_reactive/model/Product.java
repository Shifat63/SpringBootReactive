package com.shifat63.spring_boot_reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Set;

// Author: Shifat63

@Data
@EqualsAndHashCode(exclude = {"showroomSet", "brand"})
@Document
public class Product implements Serializable {
    @Id
    private String id;

    @NotBlank(message = "Name must not be empty")
    @Size(min = 1,max = 200, message = "Name must be between 1 to 200 characters")
    private String name;

    @NotNull(message = "Price must not be empty")
    @Positive(message = "Price must be positive")
    @Digits(integer = 10, fraction = 2, message = "Preparation time can have maximum 10 integer and 2 fraction digits")
    private Double price;

    @NotNull(message = "Available must not be empty")
    private Boolean available;

    @NotBlank(message = "Description must not be empty")
    @Size(min = 1,max = 500, message = "Description must be between 1 to 500 characters")
    private String description;

    @DBRef(lazy = true)
    private Set<Showroom> showroomSet;

    @DBRef(lazy = true)
    private Brand brand;
}
