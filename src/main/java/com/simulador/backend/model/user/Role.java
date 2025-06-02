package com.simulador.backend.model.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "role")
public class Role {
  @Id
  private String id;

  private RoleName name;

  // Construtor padrão
  public Role() {
  }

  // Construtor com campos
  public Role(String id, RoleName name) {
    this.id = id;
    this.name = name;
  }

  // Getters e Setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RoleName getName() {
    return name;
  }

  public void setName(RoleName name) {
    this.name = name;
  }
}