package com.alura.services;

import com.alura.autentication.TokenService;
import com.alura.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    @Autowired
    private TokenService tokenService;

    public String obtenerEmail(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            String token = authorizationHeader.replace("Bearer ", "");
            String email = tokenService.obtenerEmail(token);
            if (email != null) {
                return email;
            }
        }
        return null;
    }

    public Boolean esPropietario(HttpServletRequest request, Usuario usuario){
        return obtenerEmail(request).equals(usuario.getEmail());
    }
}
