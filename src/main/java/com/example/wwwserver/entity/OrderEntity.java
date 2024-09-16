package com.example.wwwserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id; // Unique ID for the Order

    @NotNull(message = "Seller ID cannot be null")
    private UUID sellerId;

    @NotNull(message = "Buyer ID cannot be null")
    private UUID buyerId;

    @NotNull(message = "Product ID cannot be null")
    private UUID productId;

    @NotNull(message = "Address cannot be null")
    private UUID address;

    @NotNull
    private Boolean completed = false; // Default to false

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @NotNull
    private Boolean rejected = false; // Default to false

    @NotNull
    private Boolean accepted = false; // Default to false

    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptDate; // Optional

    @NotNull
    private Boolean canceled = false; // Default to false

    @Temporal(TemporalType.TIMESTAMP)
    private Date compDate; // Optional

    // Constructor
    public OrderEntity() {
        this.createdAt = new Date(); // Set the default created date to current date
    }
}
