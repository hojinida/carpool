package com.project.carpool.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity @Getter
@RequiredArgsConstructor
public class User {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

    public void updateEmail(String email){
        this.email=email;
    }
    public void updatePassword(String password){
        this.password=password;
    }
    public void updateName(String name){
        this.name=name;
    }
    public void updatePhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }
    @Builder
    public User(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
