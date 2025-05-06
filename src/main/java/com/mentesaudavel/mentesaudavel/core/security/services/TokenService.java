package com.mentesaudavel.mentesaudavel.core.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mentesaudavel.mentesaudavel.core.entities.User;
import com.mentesaudavel.mentesaudavel.core.exceptions.InternalServerErrorException;
import com.mentesaudavel.mentesaudavel.core.exceptions.UnauthorizedAccessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String SECRET;

    @Value("${api.security.token.issuer}")
    private String TOKEN_ISSUER;

    public String generateAccessToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.SECRET);

            Instant expirationDate = this.generateAccessTokenExpirationDate();

            return JWT.create()
                    .withIssuer(this.TOKEN_ISSUER)
                    .withSubject(String.valueOf(user.getId()))
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new InternalServerErrorException("Error during access token generation: "
                    .concat(ex.getMessage()));
        }
    }

    public String validateAccessToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.SECRET);

            return JWT.require(algorithm)
                    .withIssuer(this.TOKEN_ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            throw new UnauthorizedAccessException("Could not validate access token: "
                    .concat(ex.getMessage()));
        }
    }

    private Instant generateAccessTokenExpirationDate() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.UTC);
    }
}
