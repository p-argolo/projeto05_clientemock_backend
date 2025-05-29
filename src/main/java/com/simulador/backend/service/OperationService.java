package com.simulador.backend.service;

import com.simulador.backend.dto.OperationDTO;
import com.simulador.backend.model.user.User;
import com.simulador.backend.model.user.UserDetailsImpl;
import com.simulador.backend.model.transaction.Operation;
import com.simulador.backend.repository.OperationRepository;
import com.simulador.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class OperationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationRepository operationRepository;

    // Serviço de transação
    public Operation createTransaction(OperationDTO operationDTO) throws Exception {
        // 1. Pegar o ID do remetente do JWT
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
        String senderId = principal.getId();

        // 2. Pegar receiverId e amount do DTO
        String receiverId = operationDTO.receiverId();
        double amount = operationDTO.value();

        // 3. Carregar os usuários via UserRepository
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new Exception("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new Exception("Receiver not found"));

        // 4. Criar operação
        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setSenderId(senderId);
        operation.setReceiverId(receiverId);
        operation.setTimestamp(LocalDateTime.now());

        // 7. Persistência no banco de dados
        operationRepository.save(operation);
        userRepository.save(sender);
        userRepository.save(receiver);

        return operation;
    }
}
