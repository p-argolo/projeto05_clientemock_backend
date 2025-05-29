package com.simulador.backend.model.user;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "user")
public class User {
  @Id private String id;

  private String email;
  private String password;
  private List<Role> roles;
}



