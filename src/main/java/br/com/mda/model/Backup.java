package br.com.mda.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Backup {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	private Calendar inicio;
	
	private Calendar fim;


	// Apenas para JPA
	protected Backup() {
		
	}
	
	public Backup(Calendar inicio, Calendar fim) {
		this.id = null;
		this.inicio = inicio;
		this.fim = fim;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getInicio() {
		return inicio;
	}

	public void setInicio(Calendar inicio) {
		this.inicio = inicio;
	}

	public Calendar getFim() {
		return fim;
	}

	public void setFim(Calendar fim) {
		this.fim = fim;
	}
	
	

}
