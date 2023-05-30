package com.alura.modelo;


import java.util.Date;

import com.alura.ServicesDTO.Respuesta.ModificarRespuestaRequest;
import com.alura.ServicesDTO.Respuesta.NuevaRespuestaRequest;


import jakarta.persistence.Entity;
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
@Table(name = "respuestas")
@Data
@NoArgsConstructor
public class Respuesta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Usuario usuario;

	private String mensaje;

	@Temporal(TemporalType.TIME)
	private Date fechaDeCreacion;

	private Boolean mejorRespuesta;

	@ManyToOne
	private Topico topico;




	public Respuesta(NuevaRespuestaRequest nuevaRespuesta, Usuario usuario, Topico topico) {
		this.usuario = usuario;
		this.mensaje = nuevaRespuesta.mensaje();
		this.fechaDeCreacion = new Date();
		this.mejorRespuesta = false;
		this.topico = topico;
	}

		public void modificar(ModificarRespuestaRequest modificarRespuesta) {
		this.mensaje = modificarRespuesta.mensaje();
	}

	public void marcarComoSolucion() {
		this.mejorRespuesta = true;
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
		Respuesta other = (Respuesta) obj;
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}


}
