package com.simulador.backend.service;

import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MetadataService {
    @Value("${jwe.secret}")
    private String jweKey;

    @Value("${jws.secret}")
    private String jwsKey;

    //Recebe um token JWE criptografado, realiza a decriptação e verifica a assinatura JWS
    public String metadataReceiver(String request) throws Exception {
        String tokenCriptografado = request;

        // Decodifica e decripta o token JWE usando chave UTF-8
        JWEObject jweObject = JWEObject.parse(tokenCriptografado);
        jweObject.decrypt(new DirectDecrypter(jweKey.getBytes(java.nio.charset.StandardCharsets.UTF_8)));

        // Extrai o JWT assinado do payload do JWE
        SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();

        // Verifica a assinatura do JWT com a chave JWS em UTF-8
        JWSVerifier verifier = new MACVerifier(jwsKey.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        if (!signedJWT.verify(verifier)) {
            throw new RuntimeException("Assinatura do token inválida");
        }

        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

        // Converte as claims para JSON e retorna
        Gson gson = new Gson();
        return gson.toJson(claims.getClaims());
    }
}
