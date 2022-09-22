package com.example.transportapi.payload;

import com.example.transportapi.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String mobileNumber;
    private String email;
    private Address address;
}
