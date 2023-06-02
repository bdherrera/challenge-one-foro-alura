package com.alura.autentication;

import com.alura.modelo.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String apiSecret;

    final String issuer = "David herrera";
    final int horasAdicionales = 24;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion(horasAdicionales))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("No se pudo generar el token");
        }
    }

    public String obtenerEmail(String token) {
        if (token == null) {
            throw new RuntimeException("El token es nulo");
        }
        DecodedJWT decodificador = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            decodificador = JWT.require(algorithm)
                    .withIssuer("David herrera")
                    .build()
                    .verify(token);
            decodificador.getSubject();
        } catch (JWTVerificationException e) {
            System.out.println(e);
        }
        if (decodificador == null){
            return null;
        }
        return decodificador.getSubject();
    }

    private Instant generarFechaExpiracion(int horasDuracion) {
        return LocalDateTime.now().plusHours(horasDuracion).toInstant(ZoneOffset.of("-03:00"));
    }
}