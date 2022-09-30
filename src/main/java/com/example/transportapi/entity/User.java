package com.example.transportapi.entity;

import com.example.transportapi.dto.UserRegistrationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String firstname;
    private String lastname;
    private String mobileNumber;
    private String email;

    @JsonIgnore
    private String roles;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "address_id")
    private Address address;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<BusPass> passes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public User(UserRegistrationDTO userRegistrationDTO) {
        this.username = userRegistrationDTO.getUsername();
        this.password = userRegistrationDTO.getPassword();
        this.firstname = userRegistrationDTO.getFirstname();
        this.lastname = userRegistrationDTO.getLastname();
        this.mobileNumber = userRegistrationDTO.getMobileNumber();
        this.email = userRegistrationDTO.getEmail();
        this.address = userRegistrationDTO.getAddress();
    }
}
