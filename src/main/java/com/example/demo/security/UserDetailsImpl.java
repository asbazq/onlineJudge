package com.example.demo.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.Users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    private Users users; // 컴포지션

    public UserDetailsImpl(Users users) {
        this.users = users;
    }

    public Users getUsers() {
        return users;
    }

    // 해당 Users 의 권한의 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // users.getRole()을 Collection 타입으로 받기위해
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return String.valueOf(users.getRole());
            }
        });
        // Collection<GrantedAuthority> collection = new ArrayList<>();
        // member.getRole().forEach(r-> { // r : return
        // collection.add(()-> String.valueOf(r));
        // });
        return collection;
        // 권한이 존재안할 시
        // return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
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