package com.alura.modelo;

import java.util.Date;


import com.alura.ServicesDTO.Topico.ModificarTopicoRequest;
import com.alura.ServicesDTO.Topico.NuevoTopicoRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topicos")
@Data
@NoArgsConstructor
public class Topico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Usuario usuario;

	private String titulo;
	private String mensaje;

	@Temporal(TemporalType.TIME)
	private Date fechaDeCreacion;

	@Enumerated(EnumType.STRING)
	private Estatus estatus;

	@ManyToOne
	private Curso curso;

	@Enumerated(EnumType.STRING)
	private Tag tag;

	public Topico(NuevoTopicoRequest nuevoTopico, Usuario usuario, Curso curso) {
		this.usuario = usuario;
		this.titulo = nuevoTopico.titulo();
		this.mensaje = nuevoTopico.mensaje();
		this.fechaDeCreacion = new Date();
		this.estatus = Estatus.SIN_RESPUESTAS;
		this.curso = curso;
		this.tag = nuevoTopico.tag();
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
		Topico other = (Topico) obj;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	//modificar

	public void modificar(ModificarTopicoRequest modificarTopico, Curso curso) {
		this.titulo = modificarTopico.titulo();
		this.mensaje = modificarTopico.mensaje();
		this.tag = modificarTopico.tag();
		this.curso = curso;
	}

	//marcar resultado
	public void marcarComoResuelto() {
		this.estatus = Estatus.RESUELTO;
	}
}
