package br.com.mda.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Consultorio implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idConsultorio;
	
	private String numeroSala;

	public long getIdConsultorio() {
		return idConsultorio;
	}

	public void setIdConsultorio(long idConsultorio) {
		this.idConsultorio = idConsultorio;
	}

	public String getNumeroSala() {
		return numeroSala;
	}

	public void setNumeroSala(String numeroSala) {
		this.numeroSala = numeroSala;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
