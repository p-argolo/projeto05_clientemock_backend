package com.simulador.backend.model.transaction;

public enum OperationType {
    PIX, BANK_SPLIT, TED, LOAN, REGISTER_VIRTUAL_CARD,CHANGE_PASSWORD
}

// PIX,                     Operação via PIX (pagamento instantâneo)
//BANK_SPLIT,               Divisão de valor em contas bancárias diferentes
//TED,                      Transferência eletrônica disponível (método tradicional de transferência no Brasil)
//LOAN,                     Empréstimo
//REGISTER_VIRTUAL_CARD,    Cadastro de cartão virtual
//CHANGE_PASSWORD           Troca de senha
