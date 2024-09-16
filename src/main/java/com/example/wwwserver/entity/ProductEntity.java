package com.example.wwwserver.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Product name cannot be null")
    @NotEmpty(message = "Product name cannot be empty")
    private String name;

    @NotNull(message = "Title cannot be null")
    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Description cannot be null")
    @NotEmpty(message = "Description cannot be empty")
    @Column(length = 1000)
    private String description;

    @NotNull(message = "Price cannot be null")
    private Double price;

    private Integer avltime;

    @NotNull(message = "Category cannot be null")
    @NotEmpty(message = "Category cannot be empty")
    private String category;

    @NotNull(message = "Start time cannot be null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date starttime;

    @NotNull(message = "End time cannot be null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endtime;

    private String image;
}
