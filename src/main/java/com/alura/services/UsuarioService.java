package com.alura.services;

import com.alura.Repository.UsuarioRepository;
import com.alura.ServicesDTO.usuario.ActualizarUsuarioRequest;
import com.alura.ServicesDTO.usuario.ModificarRolUsuarioRequest;
import com.alura.ServicesDTO.usuario.RegistroUsuarioRequest;
import com.alura.ServicesDTO.usuario.UsuarioResponse;
import com.alura.exceptions.DuplicadoException;
import com.alura.exceptions.NoExisteException;
import com.alura.modelo.Rol;
import com.alura.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RequestService requestService;

    @Transactional
    public void registro(RegistroUsuarioRequest registroUsuario) throws DuplicadoException {
        if (usuarioRepository.existsByEmail(registroUsuario.email())) {
            throw new DuplicadoException("email");
        }
        Usuario usuario = new Usuario(registroUsuario);
        if (!usuarioRepository.hayUsuarios()) {
            usuario.setRol(Rol.ADMIN);
        }
        usuarioRepository.save(usuario);
    }

    public void desactivarCuenta(HttpServletRequest request) throws NoExisteException {
        Optional<Usuario> usuario = usuarioRepository.buscarPorEmail(requestService.obtenerEmail(request));
        if (!usuario.isPresent()) {
            throw new NoExisteException("id");
        }
        usuario.get().desactivar();
    }

    @Transactional
    public void desactivar(Long idUsuario) throws NoExisteException {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (!usuario.isPresent()) {
            throw new NoExisteException("idUsuario");
        }
        usuario.get().desactivar();
    }

    @Transactional
    public Usuario actualizarCuenta(HttpServletRequest request,ActualizarUsuarioRequest actualizarUsuario) throws NoExisteException, DuplicadoException {
        Optional<Usuario> usuario = usuarioRepository.buscarPorEmail(requestService.obtenerEmail(request));
        if (!usuario.isPresent()) {
            throw new NoExisteException("id");
        }
        if(usuarioRepository.existsByEmail(actualizarUsuario.email())){
            throw new DuplicadoException("email");
        }
        usuario.get().actualizar(actualizarUsuario);
        return usuario.get();
    }

    @Transactional
    public Usuario modificar(Long idUsuario, ModificarRolUsuarioRequest modificarUsuario) throws NoExisteException, DuplicadoException {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (!usuario.isPresent()) {
            throw new NoExisteException("id");
        }
        Usuario modificacion = usuario.get();
        modificacion.modificar(modificarUsuario);
        return modificacion;
    }

    public Usuario detallesUsuario(Long id) throws NoExisteException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (!usuario.isPresent() || !usuario.get().getActivo()) {
            throw new NoExisteException("id");
        }
        return usuario.get();
    }

    public Page<UsuarioResponse> listado(Pageable paginacion) {
        return usuarioRepository.findAllByActivo(true, paginacion).map(UsuarioResponse::new);
    }
}
