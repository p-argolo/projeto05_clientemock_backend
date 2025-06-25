package com.simulador.backend.model.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;

    private String email;
    private String password;
    private List<Role> roles;
    private BigDecimal balance;
}



