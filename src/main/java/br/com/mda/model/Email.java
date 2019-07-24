package br.com.mda.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@XmlRootElement(name="Email")
@Component
public class Email {

	private String nome;
	private String email;
	private String texto;
	private String to;
	private String cc;
	private String from;
	private String pass;
	private String emailFormatado;
	private String url;

	/**
	 * 
	 */
	public Email() {
	}
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmailFormatado() {
		return emailFormatado;
	}

	public void setEmailFormatado(String emailFormatado) {
		this.emailFormatado = emailFormatado;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Email [nome=" + nome + ", email=" + email + ", texto=" + texto + ", to=" + to + ", from=" + from
				+ ", pass=" + pass + ", emailFormatado=" + emailFormatado + ", url=" + url + "]";
	}



	public String getCc() {
		return cc;
	}



	public void setCc(String cc) {
		this.cc = cc;
	}
	
	

}
