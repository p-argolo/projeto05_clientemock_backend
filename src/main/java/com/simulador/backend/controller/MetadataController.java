package com.simulador.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simulador.backend.dto.LocationDTO;
import com.simulador.backend.service.MetadataService;
import com.simulador.backend.service.SdkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metadata")
public class MetadataController {
    @Autowired
    private MetadataService metadataService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SdkService sdkService;

    @PostMapping("/send-operation-location")
    public ResponseEntity<?> sendOperationLocation(@RequestHeader("Authorization") String authHeader, @RequestBody LocationDTO locationDTO) {
        try {
            String tokenRequest = authHeader.replace("Bearer ", "");

            String response = sdkService.sendToSDK(tokenRequest, locationDTO);
            System.out.println("Token recebido: " + response);

            String tokenResponse = response.replace("Bearer ", "");

            String locaisSegurosJson = metadataService.metadataReceiver(tokenResponse);
            Object json = objectMapper.readValue(locaisSegurosJson, Object.class);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro em: " + e.getStackTrace()[0]);
            return ResponseEntity.badRequest().body("Erro ao processar o token: " + e.getMessage());
        }
    }
}