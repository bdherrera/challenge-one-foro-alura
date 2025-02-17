package com.alura.controller;

import com.alura.ServicesDTO.categoria.CategoriaResponse;
import com.alura.ServicesDTO.categoria.ModificarCategoriaRequest;
import com.alura.ServicesDTO.categoria.NuevaCategoriaRequest;
import com.alura.exceptions.DuplicadoException;
import com.alura.exceptions.NoExisteException;
import com.alura.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "4. Categorias", description = "Authenticated Access")
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/categorias")
@PreAuthorize("isAuthenticated()")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Nueva categoria (Admin)", description = "Admin puede crear una nueva categoria, esta sera utilizada para los cursos")
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriaResponse> nuevaCategoria(
            @RequestBody @Valid NuevaCategoriaRequest nuevaCategoria) throws DuplicadoException {
        return ResponseEntity.ok(new CategoriaResponse(categoriaService.nueva(nuevaCategoria)));
    }

    @Operation(summary = "Eliminar categoria (Admin)", description = "Admin puede eliminar la categoria")
    @DeleteMapping("/{idCategoria}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> eliminarCategoria(@PathVariable Long idCategoria) throws NoExisteException {
        categoriaService.eliminar(idCategoria);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modificar categoria (Admin)", description = "Admin puede modificar la categoria")
    @PutMapping("/{idCategoria}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoriaResponse> modificarCategoria(@PathVariable Long idCategoria,
                                                                @RequestBody @Valid ModificarCategoriaRequest modificarCategoria)
            throws NoExisteException, DuplicadoException {
        return ResponseEntity.ok(new CategoriaResponse(categoriaService.modificar(idCategoria, modificarCategoria)));
    }

    @Operation(summary = "Detalles de categoria", description = "Cualquier usuario puede ver los detalles de una categoria especifica")
    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaResponse> verCategoria(@ParameterObject @PathVariable Long idCategoria) throws NoExisteException {
        return ResponseEntity.ok(new CategoriaResponse(categoriaService.ver(idCategoria)));
    }

    @Operation(summary = "Lista de categorias", description = "Cualquier usuario obtiene la lista de todas las categorias")
    @GetMapping("/icategoria/{idCategoria}")
    public ResponseEntity<Page<CategoriaResponse>> listadoCategorias(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(categoriaService.listado(paginacion));
    }
}
