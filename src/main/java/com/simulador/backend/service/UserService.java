package com.simulador.backend.service;

import com.simulador.backend.dto.LoginUserDTO;
import com.simulador.backend.dto.RecoveryJwtTokenDto;
import com.simulador.backend.model.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtTokenService jwtTokenService;

  public RecoveryJwtTokenDto authenticateUser(LoginUserDTO loginUserDto) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

    Authentication authentication =
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
  }

}