package br.com.mda.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Evolucao {
	
	@Id
	@GeneratedValue
	private long idEvolucao;
	
	private String diagnostico;
	
	@ManyToOne
	@JoinColumn(name="idCliente")	
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="idFuncionario")	
	private Funcionario funcionario;
	
	private Date data;
	
	private String dataFormatada;
	
	private Date hora;
	
	private String horaFormatada;
	
	private String relatorio;

	public long getIdEvolucao() {
		return idEvolucao;
	}

	public void setIdEvolucao(long idEvolucao) {
		this.idEvolucao = idEvolucao;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}


	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getHoraFormatada() {
		return horaFormatada;
	}

	public void setHoraFormatada(String horaFormatada) {
		this.horaFormatada = horaFormatada;
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}
	
}
