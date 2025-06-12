package com.example.aplicativo.login.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.aplicativo.usuario.model.Login;
import com.example.aplicativo.usuario.model.Usuario;

import lombok.Getter;

@Getter
public class ModelUserDetailsImpl implements UserDetails {

    private final Login login;

    public ModelUserDetailsImpl(Login login) {
        this.login = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(
                login.getUser().getRole().getRole().getName().name()));
    }

    @Override
    public String getPassword() {
        return login.getSenha();
    }

    @Override
    public String getUsername() {
        return login.getEmail();
    }

    public Usuario getModelUser() {
        return this.login.getUser();
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