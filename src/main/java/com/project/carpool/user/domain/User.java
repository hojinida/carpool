package com.project.carpool.user.domain;

import com.project.carpool.room.domain.Room;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity @Getter
@RequiredArgsConstructor
public class User implements UserDetails {
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private Status status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Star star;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;
    public void updateRoom(Room room){
        this.room = room;
    }
    public void updateName(String name){
        this.name=name;
    }
    public void updatePassword(String password){
        this.password=password;
    }
    @Builder
    public User(String email, String password, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.star = Star.builder().point(0L).number(0L).build();
        this.status= Status.WAIT;
    }

    public void statusReady(){
        this.status= Status.READY;
    }

    public void statusWait(){
        this.status=Status.WAIT;
    }
    public void statusComplete(){
        this.status=Status.COMPLETE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
