package com.alura.services;

import com.alura.Repository.CategoriaRepository;
import com.alura.ServicesDTO.categoria.CategoriaResponse;
import com.alura.ServicesDTO.categoria.ModificarCategoriaRequest;
import com.alura.ServicesDTO.categoria.NuevaCategoriaRequest;
import com.alura.exceptions.DuplicadoException;
import com.alura.exceptions.NoExisteException;
import com.alura.modelo.Categoria;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria nueva(NuevaCategoriaRequest nuevaCategoria) throws DuplicadoException {
        if (categoriaRepository.existsByNombre(nuevaCategoria.nombre())){
            throw new DuplicadoException("nombre");
        }
        return categoriaRepository.save(new Categoria(nuevaCategoria));
    }

    @Transactional
    public void eliminar(Long idCategoria) throws NoExisteException {
        if (!categoriaRepository.existsById(idCategoria)) {
            throw new NoExisteException("idCategoria");
        }
        categoriaRepository.deleteById(idCategoria);
    }

    @Transactional
    public Categoria modificar(Long idCategoria, ModificarCategoriaRequest modificarCategoria) throws NoExisteException, DuplicadoException {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        if (!categoria.isPresent()) {
            throw new NoExisteException("idCategoria");
        }
        if (categoriaRepository.existsByNombre(modificarCategoria.nombre())){
            throw new DuplicadoException("idCategoria");
        }
        Categoria modificacion = categoria.get();
        modificacion.modificar(modificarCategoria);
        return modificacion;
    }

    public Categoria ver(Long idCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
        if (!categoria.isPresent()) {
            throw new EntityNotFoundException("Error el ID de la categoria no existe");
        }
        return categoria.get();
    }

    public Page<CategoriaResponse> listado(Pageable paginacion) {
        return categoriaRepository.findAll(paginacion).map(CategoriaResponse::new);
    }
}
