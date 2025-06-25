package com.simulador.backend.model.transaction;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "Transactions")
@Data
public class Operation {
    private BigDecimal amount;
    @Indexed(unique = true)
    @Id
    private String transactionId;
    private String senderId;
    private String receiverId;
    private LocalDateTime timestamp;
    private OperationType operationType;
    private Wifi wifi;
    private Location location;
}
