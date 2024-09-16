package com.example.wwwserver.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // Unique ID for Address

    @NotNull(message = "User ID cannot be null")
    private UUID userid; // Referencing user's ID

    @NotNull(message = "Street cannot be null")
    @NotEmpty(message = "Street cannot be empty")
    private String street;

    @NotNull(message = "City cannot be null")
    @NotEmpty(message = "City cannot be empty")
    private String city;

    @NotNull(message = "State cannot be null")
    @NotEmpty(message = "State cannot be empty")
    private String state;

    @NotNull(message = "Country cannot be null")
    @NotEmpty(message = "Country cannot be empty")
    private String country;

    @NotNull(message = "Postal code cannot be null")
    @NotEmpty(message = "Postal code cannot be empty")
    private String postalCode;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Constructor
    public void Address() {
        this.createdAt = new Date(); // Set the default created date to current date
    }
}
