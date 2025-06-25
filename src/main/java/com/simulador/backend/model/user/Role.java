package com.simulador.backend.model.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "role")
public class Role {
  @Id private String id;

  private RoleName name;
}
