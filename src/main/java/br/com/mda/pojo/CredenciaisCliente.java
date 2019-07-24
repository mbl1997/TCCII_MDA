package br.com.mda.pojo;

import br.com.mda.model.Cliente;

public class CredenciaisCliente {
	
	private Cliente cliente;
	
	private String senha;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}


}
