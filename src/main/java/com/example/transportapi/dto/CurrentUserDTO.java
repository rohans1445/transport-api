package com.example.transportapi.dto;

import com.example.transportapi.entity.Address;
import com.example.transportapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentUserDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String mobileNumber;
    private String email;
    private String roles;
    private Address address;

    public CurrentUserDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.roles = user.getRoles();
        this.mobileNumber = user.getMobileNumber();
    }

}
