package com.simulador.backend.dto;
import com.simulador.backend.model.transaction.OperationType;

public record OperationDTO(
        Double value,
        String receiverId,
        OperationType operationType
) {
}