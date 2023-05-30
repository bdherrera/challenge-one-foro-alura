package com.alura.Repository;

import com.alura.modelo.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Boolean existsByNombre(String nombre);

    Page<Curso> findAllByCategoriaId(Long idCategoria, Pageable paginacion);
}
