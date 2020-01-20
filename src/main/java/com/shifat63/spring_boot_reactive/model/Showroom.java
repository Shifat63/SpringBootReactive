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
public class Showroom implements Serializable {
    @Id
    private String id;

    @NotBlank(message = "Name must not be empty")
    @Size(min = 1,max = 100, message = "Name must be between 1 to 100 characters")
    private String name;

    @NotBlank(message = "Address must not be empty")
    @Size(min = 1,max = 200, message = "Address must be between 1 to 200 characters")
    private String address;

    @Override
    public String toString() {
        return this.id;
    }
}
