package com.alura.services;

import com.alura.Repository.RespuestaRepository;
import com.alura.Repository.TopicoRepository;
import com.alura.Repository.UsuarioRepository;
import com.alura.ServicesDTO.Respuesta.ModificarRespuestaRequest;
import com.alura.ServicesDTO.Respuesta.NuevaRespuestaRequest;
import com.alura.ServicesDTO.Respuesta.RespuestaResponse;
import com.alura.ServicesDTO.Respuesta.SolucionRespuestaRequest;
import com.alura.exceptions.*;
import com.alura.modelo.Estatus;
import com.alura.modelo.Respuesta;
import com.alura.modelo.Topico;
import com.alura.modelo.Usuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RespuestaService {
    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RequestService requestService;

    @Transactional
    public Respuesta nueva(Long idTopico, HttpServletRequest request, NuevaRespuestaRequest nuevaRespuesta)
            throws NoExisteException, DuplicadoException, TopicoResueltoException {
        Optional<Topico> topico = topicoRepository.findById(idTopico);
        if (!topico.isPresent()) {
            throw new NoExisteException("idTopico");
        }
        if (topico.get().getEstatus().equals(Estatus.RESUELTO)) {
            throw new TopicoResueltoException(idTopico);
        }
        Optional<Usuario> usuario = usuarioRepository.buscarPorEmail(requestService.obtenerEmail(request));
        if (!usuario.isPresent()) {
            throw new NoExisteException("idUsuario");
        }
        if (respuestaRepository.existsByMensaje(nuevaRespuesta.mensaje())) {
            throw new DuplicadoException("mensaje");
        }
        topico.get().setEstatus(Estatus.SIN_SOLUCION);
        return respuestaRepository.save(new Respuesta(nuevaRespuesta, usuario.get(), topico.get()));
    }

    @Transactional
    public void eliminar(Long idRespuesta, HttpServletRequest request) throws NoExisteException, NoEsCreadorException {
        Optional<Respuesta> respuesta = respuestaRepository.findById(idRespuesta);
        if (!respuesta.isPresent()) {
            throw new NoExisteException("id");
        }
        if (!requestService.esPropietario(request, respuesta.get().getUsuario())){
            throw new NoEsCreadorException();
        }
        respuestaRepository.deleteById(idRespuesta);
    }

    @Transactional
    public Respuesta modificar(Long idRespuesta, ModificarRespuestaRequest modificarRespuesta, HttpServletRequest request)
            throws NoExisteException, DuplicadoException, NoEsCreadorException {
        Optional<Respuesta> respuesta = respuestaRepository.findById(idRespuesta);
        if (!respuesta.isPresent()) {
            throw new NoExisteException("id");
        }
        if (!requestService.esPropietario(request, respuesta.get().getUsuario())){
            throw new NoEsCreadorException();
        }
        if (respuestaRepository.existsByMensaje(modificarRespuesta.mensaje())) {
            throw new DuplicadoException("mensaje");
        }
        Respuesta modificacion = respuesta.get();
        modificacion.modificar(modificarRespuesta);
        return modificacion;
    }

    public Page<RespuestaResponse> listado(Pageable paginacion) {
        return respuestaRepository.findAll(paginacion).map(RespuestaResponse::new);
    }

    public Page<RespuestaResponse> listadoPorTopico(Long idTopico,  Pageable paginacion) {
        return respuestaRepository.findAllByTopicoId(idTopico, paginacion).map(RespuestaResponse::new);
    }

    public Page<RespuestaResponse> listadoPorUsuario(Long idUsuario,  Pageable paginacion) {
        return respuestaRepository.findAllByUsuarioId(idUsuario, paginacion).map(RespuestaResponse::new);
    }

    public Respuesta ver(Long idRespuesta) {
        Optional<Respuesta> respuesta = respuestaRepository.findById(idRespuesta);
        if (!respuesta.isPresent()) {
            throw new EntityNotFoundException("Error el ID de la respuesta no existe");
        }
        return respuesta.get();
    }

    @Transactional
    public Respuesta marcarComoSolucion(SolucionRespuestaRequest solucionRequest,
                                        HttpServletRequest request) throws NoExisteException, NoEsCreadorException, RespuestaNoCorrespondeException, TopicoResueltoException {
        Optional<Topico> topico = topicoRepository.findById(solucionRequest.idTopico());
        if (!topico.isPresent()){
            throw new NoExisteException("idTopico");
        }
        if (topico.get().getEstatus().equals(Estatus.RESUELTO)){
            throw new TopicoResueltoException(solucionRequest.idTopico());
        }
        if (!requestService.esPropietario(request, topico.get().getUsuario())){
            throw new NoEsCreadorException();
        }
        Optional<Respuesta> respuesta = respuestaRepository.findById(solucionRequest.idRespuesta());
        if (!respuesta.isPresent()){
            throw new NoExisteException("idRespuesta");
        }
        if (!respuesta.get().getTopico().equals(topico.get())){
            throw new RespuestaNoCorrespondeException(solucionRequest.idRespuesta(), solucionRequest.idTopico());
        }
        topico.get().marcarComoResuelto();
        respuesta.get().marcarComoSolucion();
        return respuesta.get();
    }
}
