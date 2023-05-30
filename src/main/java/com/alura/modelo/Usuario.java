package com.alura.modelo;
import java.util.Collection;
import java.util.List;

import com.alura.ServicesDTO.usuario.ActualizarUsuarioRequest;
import com.alura.ServicesDTO.usuario.ModificarRolUsuarioRequest;
import com.alura.ServicesDTO.usuario.RegistroUsuarioRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;
	private String password;

	private Boolean activo;

	@Enumerated(EnumType.STRING)
	private Rol rol;


	public Usuario(RegistroUsuarioRequest registroUsuario) {
		this.email = registroUsuario.email();
		this.password = new BCryptPasswordEncoder().encode(registroUsuario.password());
		this.activo = true;
		this.rol = Rol.USUARIO;
	}

	public void actualizar(ActualizarUsuarioRequest actualizar) {
		this.email = actualizar.email();
		this.password = new BCryptPasswordEncoder().encode(actualizar.password());
	}

	public void modificar(ModificarRolUsuarioRequest modificarRolUsuario) {
		this.rol = modificarRolUsuario.rol();
	}

	public void desactivar() {
		this.activo = false;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + this.rol.toString()));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.activo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



}
