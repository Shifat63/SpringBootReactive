package com.shifat63.spring_boot_reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

// Author: Shifat63

@Data
@EqualsAndHashCode
@Document
public class Brand implements Serializable {
    @Id
    private String id;

    @NotBlank(message = "Name must not be empty")
    @Size(min = 1,max = 200, message = "Name must be between 1 to 200 characters")
    private String name;

    @NotBlank(message = "Description must not be empty")
    @Size(min = 1,max = 500, message = "Description must be between 1 to 500 characters")
    private String description;

    @Override
    public String toString() {
        return this.id;
    }
}
