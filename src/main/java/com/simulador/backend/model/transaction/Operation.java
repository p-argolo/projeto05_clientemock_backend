package com.simulador.backend.model.transaction;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Operation {
    private Double amount;
    @Indexed(unique = true)
    @Id
    private String transactionId;
    private String senderId;
    private String receiverId;
    private LocalDateTime timestamp;
    private OperationType operationType;

}
