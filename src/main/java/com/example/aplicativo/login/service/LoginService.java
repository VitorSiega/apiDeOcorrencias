package com.example.aplicativo.login.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.aplicativo.login.dto.JwtTokenDto;
import com.example.aplicativo.login.dto.LoginDto;
import com.example.aplicativo.login.model.ModelUserDetailsImpl;

@Service
public class LoginService {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public LoginService(JwtTokenService jwtTokenService, AuthenticationManager authenticationManager) {
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
    }

    public JwtTokenDto autenticarUsuario(LoginDto loginUserDto) {
        // Cria o token de autenticação
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDto.email(), loginUserDto.senha());
        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        ModelUserDetailsImpl modelUserDetails = (ModelUserDetailsImpl) authentication.getPrincipal();
        // Gera o JWT token
        return new JwtTokenDto(jwtTokenService.generateToken(modelUserDetails));
    }

}
