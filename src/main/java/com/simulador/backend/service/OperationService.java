package com.simulador.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simulador.backend.dto.AuthorizationDTO;
import com.simulador.backend.dto.LocationDTO;
import com.simulador.backend.dto.OperationDTO;
import com.simulador.backend.model.transaction.Operation;
import com.simulador.backend.model.user.User;
import com.simulador.backend.model.user.UserDetailsImpl;
import com.simulador.backend.repository.OperationRepository;
import com.simulador.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OperationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SdkService sdkService;
    @Autowired
    private MetadataService metadataService;
    @Autowired
    private ObjectMapper objectMapper;

    // Serviço de transação
    @Transactional
    public Operation createTransaction(OperationDTO operationDTO) throws Exception {
        // 1. Pegar o ID do remetente do JWT
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
        String senderId = principal.getId();
        User sender = userService.findById(senderId).orElseThrow(() -> new Exception("User not found"));


        // 2. Pegar receiverId e amount do DTO
        String receiverId = operationDTO.receiverId();
        BigDecimal amount = operationDTO.amount();
        User receiver = userService.findById(receiverId)
                .orElseThrow(() -> new Exception("Receiver not found"));
        System.out.println(sender.getEmail());

        // 3. Criar operação
        Operation operation = new Operation();
        operation.setSenderId(senderId);
        operation.setAmount(amount);
        operation.setReceiverId(receiverId);
        operation.setTimestamp(LocalDateTime.now());
        operation.setOperationType(operationDTO.operationType());
        operation.setWifi(operationDTO.wifi());
        operation.setLocation(operationDTO.location());

        // 4.Valida saldo disponível
        if (!checkBalance(operation, sender)) {
            throw new Exception("Insufficient balance");
        }

        // 5. Atualizar saldo dos usuários
        updateBalance(operation, sender, receiver);

        // 6. Persistência no banco de dados
        operationRepository.save(operation);
        userRepository.save(sender);
        userRepository.save(receiver);

        return operation;
    }

    // Valida se a operação é autorizada
    public boolean validadeTransactionLocation(OperationDTO operationDTO, String token) throws Exception {
        // 1. Busca as autorizações do usuario via SDK
        LocationDTO locationDTO = new LocationDTO(operationDTO.wifi(), operationDTO.location());
        String tokenResponse = sdkService.sendToSDK(token, locationDTO);
        String authorizationsJson = metadataService.metadataReceiver(tokenResponse);
        AuthorizationDTO authorizationDTO = objectMapper.readValue(authorizationsJson, AuthorizationDTO.class);

        String operationType = operationDTO.operationType().toString(); // Ex: "PIX", "TED", etc.
        BigDecimal amount = operationDTO.amount();
        System.out.println("Valor da operacao: " + amount.toString());// Valor da transação (pode ser null para operações sem valor)


        // 2. Valida operações sem valor (ex: CHANGE_PASSWORD, REGISTER_VIRTUAL_CARD)
        if (operationType.equals("CHANGE_PASSWORD")) {
            return authorizationDTO.canChangePassword(); // Só permite se CAN_CHANGE_PASSWORD = true
        }
        if (operationType.equals("REGISTER_VIRTUAL_CARD")) {
            return authorizationDTO.canRegisterVirtualCard(); // Só permite se CAN_REGISTER_VIRTUAL_CARD = true
        }

        // 3. Valida operações com valor monetário (PIX, TED, LOAN, etc.)
        switch (operationType) {
            case "PIX":
                if (!authorizationDTO.canMakePix()) return false;
                if (authorizationDTO.pixHasLimit() && amount != null) {
                    return amount.compareTo(authorizationDTO.pixLimit()) <= 0; // amount <= PIX_LIMIT
                }
                break;

            case "TED":
                if (!authorizationDTO.canMakeTed()) return false;
                if (authorizationDTO.tedHasLimit() && amount != null) {
                    System.out.println("Limite de TED: " + authorizationDTO.tedLimit().toString());
                    System.out.println(authorizationDTO);
                    return amount.compareTo(authorizationDTO.tedLimit()) <= 0; // amount <= TED_LIMIT
                }
                break;

            case "LOAN":
                if (!authorizationDTO.canMakeLoan()) return false;
                if (authorizationDTO.loanHasLimit() && amount != null) {
                    return amount.compareTo(authorizationDTO.loanLimit()) <= 0; // amount <= LOAN_LIMIT
                }
                break;

            case "BANK_SPLIT":
                if (!authorizationDTO.canMakeBankSplit()) return false;
                if (authorizationDTO.bankSplitHasLimit() && amount != null) {
                    return amount.compareTo(authorizationDTO.bankSplitLimit()) <= 0; // amount <= BANK_SPLIT_LIMIT
                }
                break;

            default:
                return false;
        }

        // 4. Se a operação não tem limite definido, mas é permitida, autoriza
        return true;
    }

    // Verifica se o remetente tem saldo suficiente para realizar a operação
    public boolean checkBalance(Operation operation, User sender) throws Exception {
        return sender.getBalance().compareTo(operation.getAmount()) >= 0;
    }

    //Atualiza os saldos dos usuários envolvidos na operação
    public void updateBalance(Operation operation, User sender, User receiver) throws Exception {
        sender.setBalance(sender.getBalance().subtract(operation.getAmount()));
        receiver.setBalance(receiver.getBalance().add(operation.getAmount()));
    }
}
