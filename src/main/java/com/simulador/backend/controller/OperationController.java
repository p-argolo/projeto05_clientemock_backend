package com.simulador.backend.controller;

import com.simulador.backend.dto.OperationDTO;
import com.simulador.backend.model.transaction.Operation;
import com.simulador.backend.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping("/transactions")


public class OperationController {
    @Autowired
    private OperationService operationService;

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody OperationDTO operationDTO) {
        try {
            Operation created = operationService.createTransaction(operationDTO);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Erro ao criar transação: " + e.getMessage());
        }
    }

    @PostMapping("/validate-transaction-location")
    public ResponseEntity<Boolean> validateTransactionLocation(@RequestHeader("Authorization") String authHeader, @RequestBody OperationDTO operationDTO) {
        try {
            String tokenRequest = authHeader.replace("Bearer ", "");
            Boolean isValid = operationService.validadeTransactionLocation(operationDTO, tokenRequest);
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
