package com.alura.Repository;


import com.alura.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Boolean existsByNombre(String nombre);
}