package com.simulador.backend.model.user;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user") // Corrigido o parêntese
public class User {
  @Id
  private String id;

  private String email;
  private String password;
  private List<Role> roles;

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  public List<Role> getRoles() {
    return this.roles;
  }

  public String getId() {
    return this.id;
  }
}

