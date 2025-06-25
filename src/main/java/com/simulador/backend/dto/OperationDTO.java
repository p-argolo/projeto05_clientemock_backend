package com.simulador.backend.dto;

import com.simulador.backend.model.transaction.Location;
import com.simulador.backend.model.transaction.OperationType;
import com.simulador.backend.model.transaction.Wifi;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OperationDTO(
        BigDecimal amount,
        String transactionId,
        String receiverId,
        LocalDateTime timestamp,
        OperationType operationType,
        Location location,
        Wifi wifi
) {
}