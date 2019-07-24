package br.com.mda.pojo;

import br.com.mda.model.Funcionario;

public class CredenciaisFuncionario {
	
	private Funcionario funcionario;
	private String senha;

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
