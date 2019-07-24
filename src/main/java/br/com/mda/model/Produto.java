package br.com.mda.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Produto {

	@Id
	private Long idProduto;
	
	private int quantidade;

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	
}
