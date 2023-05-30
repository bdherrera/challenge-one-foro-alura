package com.alura.Repository;

import com.alura.modelo.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Boolean existsByMensaje(String mensaje);

    Page<Respuesta> findAllByTopicoId(Long idTopico, Pageable paginacion);

    Page<Respuesta> findAllByUsuarioId(Long idUsuario, Pageable paginacion);
}
