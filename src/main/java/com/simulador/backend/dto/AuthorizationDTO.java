package com.simulador.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record AuthorizationDTO(
        @JsonProperty("CAN_MAKE_PIX") boolean canMakePix,
        @JsonProperty("PIX_HAS_LIMIT") boolean pixHasLimit,
        @JsonProperty("PIX_LIMIT") BigDecimal pixLimit,
        @JsonProperty("LOAN_HAS_LIMIT") boolean loanHasLimit,
        @JsonProperty("LOAN_LIMIT") BigDecimal loanLimit,
        @JsonProperty("CAN_MAKE_TED") boolean canMakeTed,
        @JsonProperty("CAN_REGISTER_VIRTUAL_CARD") boolean canRegisterVirtualCard,
        @JsonProperty("CAN_MAKE_BANK_SPLIT") boolean canMakeBankSplit,
        @JsonProperty("BANK_SPLIT_HAS_LIMIT") boolean bankSplitHasLimit,
        @JsonProperty("TED_HAS_LIMIT") boolean tedHasLimit,
        @JsonProperty("CAN_MAKE_LOAN") boolean canMakeLoan,
        @JsonProperty("TED_LIMIT") BigDecimal tedLimit,
        @JsonProperty("CAN_CHANGE_PASSWORD") boolean canChangePassword,
        @JsonProperty("BANK_SPLIT_LIMIT") BigDecimal bankSplitLimit
) {
}