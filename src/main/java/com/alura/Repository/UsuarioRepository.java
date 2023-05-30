package com.alura.Repository;

import com.alura.modelo.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByEmail(String email);

    Page<Usuario> findAllByActivo(boolean activo, Pageable pageable);

    UserDetails findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> buscarPorEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM Usuario u")
    Boolean hayUsuarios();
}
