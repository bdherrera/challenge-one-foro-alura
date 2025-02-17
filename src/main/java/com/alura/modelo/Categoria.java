package com.alura.modelo;

import com.alura.ServicesDTO.categoria.ModificarCategoriaRequest;
import com.alura.ServicesDTO.categoria.NuevaCategoriaRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    public Categoria(NuevaCategoriaRequest nuevaCategoria) {
        this.nombre = nuevaCategoria.nombre();
    }

    public void modificar(ModificarCategoriaRequest modificarCategoria) {
        this.nombre = modificarCategoria.nombre();
    }
}