package br.com.mda.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long idUsuario;
	
	@Column(unique = true)
	private String nomeCompleto;
	
	private Calendar dataNascimento;
	
	private String cpf_cnpj;
	
	private String email;
	
	private String telefonePrincipal;
	
	@Column(name="logradouro")
	private String rua;
	
	private int numero;
	
	private String complemento;
	
	private String bairro;
	
	private String cidade;
	
	private String uf;
	
	private Boolean ativo;
	
	private String cep;
	
	private char sexo;
	
	private Calendar dataInclusao;
	
	@Column(name="TipoUsuario")
	private String tipoUsuario;
	
	@Column(unique=true)
	private String login;
	
	private byte[] senha;
	
	private byte[] chave;
	
	public Usuario(){
		super();
	}

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}


	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf_cnpj() {
		return cpf_cnpj;
	}

	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefonePrincipal() {
		return telefonePrincipal;
	}

	public void setTelefonePrincipal(String telefonePrincipal) {
		this.telefonePrincipal = telefonePrincipal;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public Calendar getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Calendar dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	@JsonIgnore
	public byte[] getSenha() {
		return senha;
	}

	public void setSenha(byte[] senha) {
		this.senha = senha;
	}
	@JsonIgnore
	public byte[] getChave() {
		return chave;
	}

	public void setChave(byte[] chave) {
		this.chave = chave;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}