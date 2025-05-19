package br.app.mentesaudavel.api.modules.security.services;

import br.app.mentesaudavel.api.modules.user.domain.model.User;
import br.app.mentesaudavel.api.shared.exceptions.InternalServerErrorException;
import br.app.mentesaudavel.api.shared.exceptions.UnauthorizedAccessException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Service
public class JwtService {

    @Value("${api.security.token.issuer}")
    private String issuer;

    @Value("${api.security.token.secret}")
    private String secret;

    private Algorithm algorithm;

    @PostConstruct
    public void initAlgorithm() {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    public String generate(User user) {
        try {
            Instant expiresAt = this.generateExpirationDate();

            return JWT.create()
                    .withIssuer(this.issuer)
                    .withSubject(String.valueOf(user.getId()))
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new InternalServerErrorException(
                    "Error during JWT generation",
                    Map.of("message", new String[] {ex.getMessage()})
            );
        }
    }

    public String validate(String token) {
        try {
            return JWT.require(algorithm)
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            throw new UnauthorizedAccessException(
                    "Invalid or expired token provided.",
                    Map.of("message", new String[] {ex.getMessage()})
            );
        }
    }

    public Instant getExpirationDate(String token) {
        try {
            return JWT.require(this.algorithm)
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getExpiresAtAsInstant();
        } catch(JWTVerificationException ex) {
            throw new UnauthorizedAccessException(
                    "Invalid or expired token provided.",
                    Map.of("message", new String[] {ex.getMessage()})
            );
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(24)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
