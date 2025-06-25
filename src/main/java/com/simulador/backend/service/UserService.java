package com.simulador.backend.service;

import com.simulador.backend.dto.LoginUserDTO;
import com.simulador.backend.dto.RecoveryJwtTokenDto;
import com.simulador.backend.model.user.User;
import com.simulador.backend.model.user.UserDetailsImpl;
import com.simulador.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserRepository userRepository;

    //Autentica o usuário com base nas credenciais fornecidas
    public RecoveryJwtTokenDto authenticateUser(LoginUserDTO loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication =
                authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    //Encontrar usuario utilizando seu id
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}