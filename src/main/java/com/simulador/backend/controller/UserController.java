package com.simulador.backend.controller;

import com.simulador.backend.dto.LoginUserDTO;
import com.simulador.backend.dto.RecoveryJwtTokenDto;
import com.simulador.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(
      @RequestBody LoginUserDTO loginUserDto) {
    RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
    return new ResponseEntity<>(token, HttpStatus.OK);
  }

  @GetMapping("/authentication")
  public ResponseEntity<String> getAuthenticationTest() {
    return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
  }

  @GetMapping("/authentication/customer")
  public ResponseEntity<String> getCustomerAuthenticationTest() {
    return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
  }

  @GetMapping("/authentication/admin")
  public ResponseEntity<String> getAdminAuthenticationTest() {
    return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
  }
}
