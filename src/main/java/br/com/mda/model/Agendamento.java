package br.com.mda.model;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Agendamento {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	
	
	@ManyToOne
    @JoinColumn(name="idCliente")	
	private Cliente cliente;	
	
	@ManyToOne
    @JoinColumn(name="idFuncionario")	
	private Funcionario funcionario;
	
	@OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="idconsulta")
	private Consulta consulta;	
	
	@Column(name="idgcalendar") 
	private String idGCalendar;
	
	@Column(name="idrecurring")
	private String idRecurring;	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar start;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar end;	
	
	private String descricao;
	
	private String color;
	
	private BigDecimal valor;
	
	private boolean ativo;	
	
	private String title;
	
	private boolean naoCompareceu;
	
	private boolean fechado;
	
	// Apenas para JPA
	protected Agendamento() {
		super();
	}

	public Agendamento(Cliente cliente2, String idGCalendar2, String idRecurring2, Calendar start2, Calendar end2,
			String descricao2, String color2, boolean ativo2) {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public Calendar getStart() {
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	public Calendar getEnd() {
		return end;
	}

	public void setEnd(Calendar end) {
		this.end = end;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isNaoCompareceu() {
		return naoCompareceu;
	}

	public void setNaoCompareceu(boolean naoCompareceu) {
		this.naoCompareceu = naoCompareceu;
	}
	
	public String getTitle() {
		if (this.cliente == null) {
			return !this.descricao.isEmpty()?this.descricao:"";
		}
		return (this.descricao != null && !this.descricao.isEmpty()) ?
				this.cliente.getNomeCompleto() + " (" + this.descricao + ")" : 
				this.cliente.getNomeCompleto();
	}
	
	/**
	 * @param titulo the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdGCalendar() {
		return idGCalendar;
	}

	public void setIdGCalendar(String idGCalendar) {
		this.idGCalendar = idGCalendar;
	}

	public String getIdRecurring() {
		return idRecurring;
	}

	public void setIdRecurring(String idRecurring) {
		this.idRecurring = idRecurring;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public boolean isFechado() {
		return fechado;
	}

	public void setFechado(boolean fechado) {
		this.fechado = fechado;
	}
	
}
