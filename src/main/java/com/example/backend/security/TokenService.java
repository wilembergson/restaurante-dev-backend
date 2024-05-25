package com.example.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.exceptions.DefaultError;
import com.example.backend.model.entity.Customer;
import com.example.backend.model.entity.Emploeey;
import com.example.backend.security.dto.ValidateTokenDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateCustomerToken(Customer usr) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("devmotel-auth-api")
                    .withSubject(usr.getLogin())
                    .withClaim("role", usr.getRole())
                    .withClaim("cpf", usr.getCpf())
                    .withExpiresAt(tempoExpiracao())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro na geração do token.", exception);
        }
    }

    public String generateEmploeeyToken(Emploeey usr) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("devmotel-auth-api")
                    .withSubject(usr.getLogin())
                    .withClaim("role", usr.getRole())
                    .withClaim("registration", usr.getRegistration())
                    .withExpiresAt(tempoExpiracao())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro na geração do token.", exception);
        }
    }

    private DecodedJWT getDecodeJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("devmotel-auth-api")
                    .build()
                    .verify(token);
        } catch (TokenExpiredException e) {
            throw new DefaultError("Sessão expirada.", HttpStatus.FORBIDDEN);
        } catch (JWTDecodeException e) {
            throw new DefaultError("Token inválido.", HttpStatus.FORBIDDEN);
        }
    }

    public ValidateTokenDTO validateToken(String token) {
        DecodedJWT decodedJWT = getDecodeJWT(token);
        String login = decodedJWT.getSubject();
        String role = decodedJWT.getClaim("role").asString();
        return new ValidateTokenDTO(login, role);
    }

    public Long getCustomerCpf(String requestToken) {
        String token = requestToken.replace("Bearer ", "");
        DecodedJWT decodedJWT = getDecodeJWT(token);
        return decodedJWT.getClaim("cpf").asLong();
    }

    public Long getEmploeeyRegistration(String requestToken) {
        String token = requestToken.replace("Bearer ", "");
            DecodedJWT decodedJWT = getDecodeJWT(token);
            return decodedJWT.getClaim("registration").asLong();
    }

    private Instant tempoExpiracao() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
