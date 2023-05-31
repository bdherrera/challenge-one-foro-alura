package com.alura.services;

import com.alura.Repository.CursoRepository;
import com.alura.Repository.TopicoRepository;
import com.alura.Repository.UsuarioRepository;
import com.alura.ServicesDTO.Topico.ModificarTopicoRequest;
import com.alura.ServicesDTO.Topico.NuevoTopicoRequest;
import com.alura.ServicesDTO.Topico.TopicoResponse;
import com.alura.exceptions.DuplicadoException;
import com.alura.exceptions.NoEsCreadorException;
import com.alura.exceptions.NoExisteException;
import com.alura.modelo.Curso;
import com.alura.modelo.Estatus;
import com.alura.modelo.Topico;
import com.alura.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private RequestService requestService;

    @Transactional
    public Topico nuevo(HttpServletRequest request, NuevoTopicoRequest nuevoTopico)
            throws NoExisteException, DuplicadoException {
        if (!cursoRepository.existsById(nuevoTopico.idCurso())) {
            throw new NoExisteException("idCurso");
        }
        String email = requestService.obtenerEmail(request);
        Optional<Usuario> usuario = usuarioRepository.buscarPorEmail(email);
        if (!usuario.isPresent()) {
            throw new NoExisteException("idUsuario");
        }
        if (topicoRepository.existsByTitulo(nuevoTopico.titulo())) {
            throw new DuplicadoException("titulo");
        }
        if (topicoRepository.existsByMensaje(nuevoTopico.mensaje())) {
            throw new DuplicadoException("mensaje");
        }
        return topicoRepository
                .save(new Topico(nuevoTopico, usuario.get(), cursoRepository.getReferenceById(nuevoTopico.idCurso())));
    }

    @Transactional
    public void eliminar(HttpServletRequest request, Long idTopico) throws NoExisteException, NoEsCreadorException {
        Optional<Topico> topico = topicoRepository.findById(idTopico);
        if (!topico.isPresent()) {
            throw new NoExisteException("idTopico");
        }
        if (!requestService.esPropietario(request, topico.get().getUsuario())){
            throw new NoEsCreadorException();
        }
        topicoRepository.deleteById(idTopico);
    }

    @Transactional
    public Topico modificar(HttpServletRequest request, Long idTopico, ModificarTopicoRequest modificarTopico) throws NoExisteException, DuplicadoException, NoEsCreadorException {
        Optional<Topico> topico = topicoRepository.findById(idTopico);
        if (!topico.isPresent()) {
            throw new NoExisteException("idTopico");
        }
        if (!requestService.esPropietario(request, topico.get().getUsuario())){
            throw new NoEsCreadorException();
        }
        if (topicoRepository.existsByTitulo(modificarTopico.titulo())) {
            throw new DuplicadoException("titulo");
        }
        if (topicoRepository.existsByMensaje(modificarTopico.mensaje())) {
            throw new DuplicadoException("mensaje");
        }
        Optional<Curso> curso = cursoRepository.findById(modificarTopico.idCurso());
        if (!curso.isPresent()) {
            throw new NoExisteException("idCurso");
        }
        Topico modificacion = topico.get();
        modificacion.modificar(modificarTopico, curso.get());
        return modificacion;
    }

    public Page<TopicoResponse> listado(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(TopicoResponse::new);
    }

    public Page<TopicoResponse> verTopicosPorUsuario(Long idUsuario, Pageable paginacion) throws NoExisteException {
        if (!topicoRepository.existsById(idUsuario)){
            throw new NoExisteException("idUsuario");
        }
        return topicoRepository.findAllByUsuarioId(idUsuario, paginacion).map(TopicoResponse::new);
    }

    public Page<TopicoResponse> verTopicosPorCurso(Long idCurso, Pageable paginacion) throws NoExisteException {
        if (!topicoRepository.existsById(idCurso)){
            throw new NoExisteException("idCurso");
        }
        return topicoRepository.findAllByCursoId(idCurso, paginacion).map(TopicoResponse::new);
    }

    public Page<TopicoResponse> verTopicosPorEstatus(String estatus, Pageable paginacion) throws NoExisteException {
        if (!topicoRepository.existsByEstatus(Estatus.valueOf(estatus))){
            throw new NoExisteException("estatus");
        }
        return topicoRepository.findAllByEstatus(Estatus.valueOf(estatus), paginacion).map(TopicoResponse::new);
    }

    public Topico ver(Long id) throws NoExisteException {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (!topico.isPresent()) {
            throw new NoExisteException("id");
        }
        return topico.get();
    }
}
