package br.com.mda.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Consulta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private BigDecimal valor;
	private boolean recibo;
	private Calendar inicio;
	private Calendar fim;		
	private String descricao;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * @return the valor
	 */
	public BigDecimal getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;	
	}

	/**
	 * @return the recibo
	 */
	public boolean isRecibo() {
		return recibo;
	}

	/**
	 * @param recibo the recibo to set
	 */
	public void setRecibo(boolean recibo) {
		this.recibo = recibo;
	}

	/**
	 * @return the inicio
	 */
	public Calendar getInicio() {
		return inicio;
	}
	
	/**
	 * @param inicio the inicio to set
	 */
	public void setInicio(Calendar inicio) {
		this.inicio = inicio;
	}
	
	/**
	 * @return the fim
	 */
	public Calendar getFim() {
		return fim;
	}
	
	/**
	 * @param fim the fim to set
	 */
	public void setFim(Calendar fim) {
		this.fim = fim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}		
	
	
}
