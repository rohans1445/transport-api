package com.example.transportapi.dto;

import com.example.transportapi.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserRegistrationDTO {

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Password must not be blank")
    private String password;

    private String firstname;
    private String lastname;
    private String mobileNumber;
    private String email;
    private Address address;
}
