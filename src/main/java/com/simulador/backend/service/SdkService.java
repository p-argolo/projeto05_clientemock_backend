package com.simulador.backend.service;

import com.simulador.backend.client.SafetyPlaceClient;
import com.simulador.backend.dto.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SdkService {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private SafetyPlaceClient safetyPlaceClient;

    // Encaminha os dados do locationDTO e o token JWT para o SDK de locais seguros.
    public String sendToSDK(String token, LocationDTO locationDTO) throws Exception {
        return safetyPlaceClient.sendToSdk(token, locationDTO);
    }
}
