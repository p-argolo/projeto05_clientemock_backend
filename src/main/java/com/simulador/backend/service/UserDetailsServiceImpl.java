package com.simulador.backend.service;

import com.simulador.backend.model.user.User;
import com.simulador.backend.model.user.UserDetailsImpl;
import com.simulador.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    return new UserDetailsImpl(user);
  }
}
