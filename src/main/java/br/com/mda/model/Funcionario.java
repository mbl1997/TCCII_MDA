package br.com.mda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Funcionario extends Usuario {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private String nRegistro;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getnRegistro() {
		return nRegistro;
	}

	public void setnRegistro(String nRegistro) {
		this.nRegistro = nRegistro;
	}

}
