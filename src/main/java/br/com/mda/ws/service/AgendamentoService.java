package br.com.mda.ws.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mda.model.Agendamento;
import br.com.mda.model.Cliente;
import br.com.mda.model.Funcionario;
import br.com.mda.ws.repository.AgendamentoRepository;

@Service
public class AgendamentoService {
	
	@Autowired
	AgendamentoRepository agendamentoRepository;
	
	//Serve para inserir e alterar
		public Agendamento salvar(Agendamento agendamento){
			agendamentoRepository.save(agendamento);
			return agendamento;
		}
	
	public List<Agendamento> listarPorPeriodoePorProfissional(Calendar dataInicial,Calendar dataFinal,Funcionario funcionario){
		List<Agendamento> agendamentos = agendamentoRepository.listarPorPeriodoePorProfissional(dataInicial, dataFinal, funcionario);
		return agendamentos;
	}
	
	public List<Agendamento> listarPorPeriodoePorPaciente(Calendar dataInicial,Calendar dataFinal,Cliente cliente){
		List<Agendamento> agendamentos = agendamentoRepository.listarPorPeriodoePorPaciente(dataInicial, dataFinal, cliente);
		return agendamentos;
	}
	
	public Agendamento findById(Agendamento agendamento){
		agendamento = agendamentoRepository.findOne(agendamento.getId());
		return agendamento;
	}
	
	public List<Agendamento> listarAllPorPeriodo(Calendar dataInicial,Calendar dataFinal){
		List<Agendamento> agendamentos = agendamentoRepository.listarAllPorPeriodo(dataInicial, dataFinal);
		return agendamentos;
	}
	
	public List<Agendamento> listarEventosPrincipaisPorPeriodo(Calendar dataInicial,Funcionario funcionario){
		List<Agendamento> agendamentos = agendamentoRepository.listarEventosPrincipaisPorPeriodo(dataInicial, funcionario);
		return agendamentos;
	}
	
	public List<Agendamento> salvarListaAgendamentos(List<Agendamento> agendamentos){
		agendamentos = agendamentoRepository.save(agendamentos);
		return agendamentos;
	}
	public List<Agendamento> listarPorFuncionario(Funcionario funcionario){
		List<Agendamento> agendamentos = agendamentoRepository.listarPorFuncionario(funcionario);
		return agendamentos;
	}
	
	public List<String> listarIdGCalendarPorPeriodo(Calendar dataInicial, Calendar dataFinal){
		List<String> agendamentos = agendamentoRepository.listarIdGCalendarPorPeriodo(dataInicial, dataFinal);
		return agendamentos;
	}
	
	public Agendamento localizarAgendamentoRepetitivo(Calendar start, String idRecurring){	
		Agendamento agendamento = agendamentoRepository.localizarAgendamentoRepetitivo(start, idRecurring);
		return agendamento;
	}
	
	public Agendamento localizarAgendamentoPrincipalRepetitivo(String idRecurring){	
		Agendamento agendamento = agendamentoRepository.localizarAgendamentoPrincipalRepetitivo(idRecurring);
		return agendamento;
	}
	
	public List<Agendamento> listarConsultasNaoFinalizadasPorPeriodo(Calendar dataInicial, Calendar dataFinal){	
		List<Agendamento> agendamentos = agendamentoRepository.listarConsultasNaoFinalizadasPorPeriodo(dataInicial, dataFinal);
		return agendamentos;
	}
	
	public List<Agendamento> listarConsultasPorPeriodo(Calendar dataInicial, Calendar dataFinal){	
		List<Agendamento> agendamentos = agendamentoRepository.listarConsultasPorPeriodo(dataInicial, dataFinal);
		return agendamentos;
	}
	
	public List<Agendamento> listarReceitasPorPeriodoDash(Calendar inicio, Calendar fim){	
		List<Agendamento> agendamentos = agendamentoRepository.listarReceitasPorPeriodoDash(inicio.getTime(), fim.getTime());
		return agendamentos;
	}
	
	public List<Agendamento> listarReceitasPorFuncionario(Calendar dataInicial, Calendar dataFinal, Funcionario funcionario){	
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosDoMesPorFuncionario(dataInicial, dataFinal, funcionario);
		return agendamentos;
	}
	
	public Agendamento save(Agendamento agendamento){	
		agendamento = agendamentoRepository.save(agendamento);
		return agendamento;
	}
	public Agendamento findFirstByIdRecurringAndAtivo(String idRecurring, boolean ativo){	
		Agendamento agendamento = agendamentoRepository.findFirstByIdRecurringAndAtivo(idRecurring, ativo);
		return agendamento;
	}
	
	public void delete(Agendamento agendamento){	
		agendamentoRepository.delete(agendamento);
	}
	
	public List<Agendamento> listarAgendamentosAposData(Calendar start,	Long grupo, Funcionario f){	
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosAposData(start, f);
		return agendamentos;
	}
	
	public List<Agendamento> listarAgendamentosRepetitivosParaNaoVincular(Date dataInicial) {
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosRepetitivosParaNaoVincular(dataInicial);
		return agendamentos;
	}
	
	public List<Agendamento> listarAgendamentosRepetidosAVincular(Funcionario funcionario) {	
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosRepetidosAVincular(funcionario);
		return agendamentos;
	}
	
	public List<Agendamento> listarAposHorario(Funcionario funcionario) {
		List<Agendamento> agendamentos = agendamentoRepository.listarAposHorario(funcionario);
		return agendamentos;
	}
	
	public List<Agendamento> listarAgendamentosDoDia(Funcionario funcionario) {
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosDoDiaProfissionais(funcionario);
		return agendamentos;
	}
	
	public List<Agendamento> listarAgendamentosDoDiaCliente(Cliente cliente) {
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosDoDiaClientes(cliente);
		return agendamentos;
	}
	
	public List<Agendamento> listarAgendamentosDoDiaGeral() {
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosDoDiaGeral();
		return agendamentos;
	}
	
	public List<Agendamento> listarAgendamentosComConsultaPeriodo(Calendar dataInicial, Calendar dataFinal, Cliente cliente, Funcionario funcionario) {
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosComConsultaPeriodo(dataInicial, dataFinal, funcionario);
		return agendamentos;
	}
	
	public List<Agendamento> listarAgendamentosDoMesPorFuncionario(Calendar dataInicial, Calendar dataFinal, Funcionario funcionario) {
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosDoMesPorFuncionario(dataInicial, dataFinal, funcionario);
		return agendamentos;
	}
	
	public List<Agendamento> listarAgendamentosDoMesPorCliente(Calendar dataInicial, Calendar dataFinal, Cliente cliente) {
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosDoMesPorCliente(dataInicial, dataFinal, cliente);
		return agendamentos;
	}
	
	public List<Agendamento> listarAgendamentosComConsulta(Cliente cliente, Funcionario funcionario) {
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosComConsulta(funcionario);
		return agendamentos;
	}
	
	public Agendamento listarEventoPrincipalPorGrupoEFuncionario(Long grupo, Funcionario funcionario) {
		Agendamento agendamento = agendamentoRepository.listarEventoPrincipalPorGrupoEfuncionario(funcionario);
		return agendamento;
	}
	
	public void deleteByIdGCalendar(String idGCalendar){	
		agendamentoRepository.deleteByIdGCalendar(idGCalendar);
	}
	
	public List<Agendamento> listarPorGrupoEFuncionario(Long grupo, Funcionario funcionario){	
		List<Agendamento> agendamentos = agendamentoRepository.listarPorGrupoEFuncionario(funcionario);
		return agendamentos;
	}

	public List<String> listarDatasAgendamentoPeriodoPorGrupoEFuncionario(Calendar dataInicial, Calendar dataFinal, long grupo, Funcionario funcionario){
		List<String> lstDiasSalvos = agendamentoRepository.listarDatasAgendamentoPeriodoPorGrupoEFuncionario(dataInicial, dataFinal, funcionario);
		return lstDiasSalvos;
	}
	
	public List<Agendamento> listarAgendamentosVinculados(Funcionario funcionario){
		List<Agendamento> lstDiasSalvos = agendamentoRepository.listarAgendamentosVinculados(funcionario);
		return lstDiasSalvos;
	}
	
	public List<Agendamento> listarAgendamentosRepetitivosParaNaoVincular(Funcionario funcionario, Calendar dataAtual){
		List<Agendamento> agendamentos = agendamentoRepository.listarAgendamentosRepetitivosParaNaoVincular(funcionario, dataAtual);
		return agendamentos;
	}
	
	public List<Agendamento> listarAgendamentosParaExcluirNoGoogleCalendarDuranteExportacao(Agendamento agendamento, Funcionario funcionario){
		List<Agendamento> lstDiasSalvos = agendamentoRepository.listarAgendamentosParaExcluirNoGoogleCalendarDuranteExportacao(agendamento, funcionario);
		return lstDiasSalvos;
	}
	
	public List<Agendamento> listarAgendamentosSimplesAVincular(Funcionario funcionario, Calendar date)	{
		List<Agendamento> lstDiasSalvos = agendamentoRepository.listarAgendamentosSimplesAVincular(funcionario, date);	
		return lstDiasSalvos;
	}
}
