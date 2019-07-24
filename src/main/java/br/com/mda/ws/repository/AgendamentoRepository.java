package br.com.mda.ws.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.mda.model.Agendamento;
import br.com.mda.model.Cliente;
import br.com.mda.model.Funcionario;

public interface AgendamentoRepository extends JpaRepository <Agendamento, Long>{
	
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario fun "					
			+ "WHERE (DATE(a.start) BETWEEN DATE(?1) AND DATE(?2)) "
			+ "AND fun = ?3)")
	public List<Agendamento> listarPorPeriodoePorProfissional(Calendar dataInicial, Calendar dataFinal, Funcionario funcionario);
	
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.cliente fun "					
			+ "WHERE (DATE(a.start) BETWEEN DATE(?1) AND DATE(?2)) "
			+ "AND fun = ?3)")
	public List<Agendamento> listarPorPeriodoePorPaciente(Calendar dataInicial, Calendar dataFinal, Cliente cliente);
	
	@Query("SELECT a FROM Agendamento a "					
			+ "WHERE (DATE(a.start) BETWEEN DATE(?1) AND DATE(?2))")
	public List<Agendamento> listarAllPorPeriodo(Calendar dataInicial, Calendar dataFinal);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "					
			+ "WHERE f = ?1")
	public Agendamento listarEventoPrincipalPorGrupoEfuncionario(Funcionario funcionario);	
	@Query("SELECT a FROM Agendamento a "
			+ "WHERE a.funcionario = ?1 ")
	public List<Agendamento> listarPorFuncionario(Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "					
			+ "WHERE ((DATE(a.start) BETWEEN DATE(?1) AND DATE(?2)) "
			+ "OR (DATE(a.start) <= DATE(?1))) "
			+ "AND f = ?3")
	public List<Agendamento> listarPorPeriodo(Calendar dataInicial, Calendar dataFinal, Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "					
			+ "WHERE DATE(a.start) = DATE(NOW()) "
			+ "AND a.start >= NOW() "			
			+ "AND a.ativo = true "			
			+ "AND f = ?1 "
			+ "ORDER BY a.start ASC")
	public List<Agendamento> listarAposHorario(Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "
			+ "WHERE DATE(a.end) <= DATE(?1) AND f = ?2")
	public List<Agendamento> listarEventosPrincipaisPorPeriodo(Calendar dataFinal, Funcionario funcionario);

	@Query("SELECT a FROM Agendamento a WHERE"
			+ " DAY(start) = DAY(now()) "
			+ " AND MONTH(start) = MONTH(now()) "
			+ " AND YEAR(start) = YEAR(now())")
	public List<Agendamento> listarAgendamentosDoDiaGeral();
	
	@Query("SELECT a FROM Agendamento a WHERE"
			+ " a.funcionario = ?1 "
			+ " AND DAY(start) = DAY(now()) "
			+ " AND MONTH(start) = MONTH(now()) "
			+ " AND YEAR(start) = YEAR(now())")
	public List<Agendamento> listarAgendamentosDoDiaProfissionais(Funcionario funcionario);
	
	@Query("SELECT a FROM Agendamento a WHERE"
			+ " a.cliente = ?1 "
			+ " AND DAY(start) = DAY(now()) "
			+ " AND MONTH(start) = MONTH(now()) "
			+ " AND YEAR(start) = YEAR(now())")
	public List<Agendamento> listarAgendamentosDoDiaClientes(Cliente cliente);
	
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "
			+ "WHERE DATE(a.start) > DATE(?1) "
			+ "AND f = ?2 "			
			+ "AND f.ativo = true "
			+ "ORDER BY a.start ASC")
	public List<Agendamento> listarAgendamentosAposData(Calendar data, Funcionario funcionario);

	@Transactional
	public void deleteBycliente(Cliente cliente);
	@Query("SELECT idGCalendar FROM Agendamento "
			+ "WHERE DATE(start) BETWEEN DATE(?1) AND DATE(?2) "
			+ "AND idGCalendar IS NOT NULL "
			+ "AND ativo = true")
	public List<String> listarIdGCalendarPorPeriodo(Calendar start, Calendar end);
	@Transactional
	public void deleteByIdGCalendar(String idGCalendar);	
	public Agendamento findByIdGCalendarAndAtivo(String idGCalendar, boolean ativo);	
	public Agendamento findFirstByIdRecurringAndAtivo(String idRecurring, boolean ativo);
	@Query("SELECT a FROM Agendamento a "
			+ "WHERE a.funcionario = ?1 "
			+ "AND a.consulta IS NOT NULL "					
			+ "ORDER BY a.start ASC")
	public List<Agendamento> listarAgendamentosComConsulta(Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "
			+ "WHERE f = ?1 ")
	public List<Agendamento> listarPorGrupoEFuncionario(Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "
			+ "WHERE DATE(a.start) BETWEEN DATE(?1) AND DATE(?2) "
			+ "AND f = ?3 "
			+ "AND a.consulta IS NOT NULL "					
			+ "ORDER BY a.start ASC")
	public List<Agendamento> listarAgendamentosComConsultaPeriodo(Calendar dataInicial, Calendar dataFinal, Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "
			+ "WHERE DATE(a.start) BETWEEN DATE(?1) AND DATE(?2) "
			+ "AND f = ?3 "
			+ "ORDER BY a.start ASC")
	public List<Agendamento> listarAgendamentosDoMesPorFuncionario(Calendar dataInicial, Calendar dataFinal, Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.cliente c "
			+ "WHERE DATE(a.start) BETWEEN DATE(?1) AND DATE(?2) "
			+ "AND c = ?3 "
			+ "ORDER BY a.start ASC")
	public List<Agendamento> listarAgendamentosDoMesPorCliente(Calendar dataInicial, Calendar dataFinal, Cliente cliente);
	@Query("SELECT a FROM Agendamento a "			
			+ "WHERE (DATE(a.start) BETWEEN DATE(?1) AND DATE(?2)) "
			+ "AND a.funcionario = ?3 "
			+ "AND a.naoCompareceu = false "
			+ "AND a.consulta.valor > 0 ")
	public List<Agendamento> listarReceitasPorPeriodoProfissionais(Calendar dataInicial, Calendar dataFinal, Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "			
			+ "WHERE (DATE(a.start) BETWEEN DATE(?1) AND DATE(?2)) "
			+ "AND a.naoCompareceu = false ")
	public List<Agendamento> listarReceitasPorPeriodoDash(Date inicio,Date fim);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "
			+ "WHERE DATE(a.start) BETWEEN DATE(?1) AND DATE(?2) "
			+ "AND f = ?3 "
			+ "ORDER BY a.start ASC")
	public List<Agendamento> listarReceitasPorFuncionario(Calendar dataInicial, Calendar dataFinal, Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "			
			+ "WHERE (DATE(a.start) BETWEEN DATE(?1) AND DATE(?2)) "
			+ "AND a.naoCompareceu = false "
			+ "AND a.fechado = true ")
	public List<Agendamento> listarConsultasPorPeriodo(Calendar dataInicial, Calendar dataFinal);
	@Query("SELECT a FROM Agendamento a "			
			+ "INNER JOIN a.funcionario f "							
			+ "WHERE (DATE(a.start) BETWEEN DATE(?1) AND DATE(?2)) "
			+ "AND a.consulta IS NOT NULL "			
			+ "AND f = ?3")
	public List<Agendamento> listarConsultasNaoFinalizadasPorPeriodoeFuncionario(Calendar dataInicial, Calendar dataFinal, Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "
			+ "WHERE DATE(a.start) = DATE(?1) "
			+ "AND a.idRecurring = ?2 "
			+ "AND a.ativo = true"
			)
	public Agendamento localizarAgendamentoRepetitivo(Calendar start, String idRecurring);	
	@Query("SELECT a FROM Agendamento a "
			+ "WHERE a.idRecurring = ?1 "
			+ "AND a.ativo = true"
			)
	public Agendamento localizarAgendamentoPrincipalRepetitivo(String idRecurring);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "
			+ "WHERE a.idGCalendar IS NOT NULL "
			+ "OR a.idRecurring IS NOT NULL "
			+ "AND f = ?1")
	public List<Agendamento> listarAgendamentosVinculados(Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "
			+ "WHERE (DATE(a.start) >= DATE(?2)) "
			+ "AND a.ativo = true "
			+ "AND f = ?1")
	public List<Agendamento> listarAgendamentosSimplesAVincular(Funcionario funcionario, Calendar dataInicial);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "
			+ "WHERE a.ativo = true "
			+ "AND f = ?1")
	public List<Agendamento> listarAgendamentosRepetidosAVincular(Funcionario funcionario);
	@Query("SELECT DATE_FORMAT(a.start,'%Y-%m-%d') FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "			
			+ "WHERE DATE(a.start) BETWEEN DATE(?1) AND DATE(?2) "
			+ "AND f.ativo = true "
			+ "AND f = ?3")	
	public List<String> listarDatasAgendamentoPeriodoPorGrupoEFuncionario(Calendar dataInicial, Calendar dataFinal, Funcionario funcionario);
	@Query("SELECT a FROM Agendamento a "
			+ "WHERE (DATE(a.start) < DATE(?1))")
	public List<Agendamento> listarAgendamentosRepetitivosParaNaoVincular(Date dataInicial);
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "
			+ "WHERE f = ?1 "			
			+ "AND a.ativo = false")
	public List<Agendamento> listarAgendamentosParaExcluirNoGoogleCalendarDuranteExportacao(Agendamento agendamento, Funcionario funcionario);	
	@Query("SELECT a FROM Agendamento a "
			+ "INNER JOIN a.funcionario f "
			+ "WHERE (DATE(a.start) < DATE(?2)) "
			+ "AND a.ativo = true "
			+ "AND f = ?1")
	public List<Agendamento> listarAgendamentosRepetitivosParaNaoVincular(Funcionario funcioanrio, Calendar dataInicial);
	@Query("SELECT a FROM Agendamento a "
			+ "WHERE (DATE(a.start) BETWEEN DATE(?1) AND DATE(?2)) "
			+ "AND a.naoCompareceu = true ")			
	public List<Agendamento> listarConsultasNaoFinalizadasPorPeriodo(Calendar dataInicial, Calendar dataFinal);
}

