package com.alura.Repository;

import com.alura.modelo.Estatus;
import com.alura.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Boolean existsByTitulo(String titulo);

    Boolean existsByMensaje(String mensaje);

    Boolean existsByEstatus(Estatus estatus);

    Page<Topico> findAllByUsuarioId(Long idUsuario, Pageable paginacion);

    Page<Topico> findAllByCursoId(Long idCurso, Pageable paginacion);

    Page<Topico> findAllByEstatus(Estatus status, Pageable paginacion);
}
