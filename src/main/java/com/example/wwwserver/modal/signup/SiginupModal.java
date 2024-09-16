package com.example.wwwserver.modal.signup;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SiginupModal {
     private String image;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Phone cannot be null")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 8 characters long")
    private String password;

    @NotNull(message = "Role cannot be null")
    private String role;

    private String tokenpass;

    private Double lat;
    private Double longi;
}
