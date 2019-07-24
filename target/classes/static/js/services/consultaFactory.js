angular.module('mda').factory('consultaFactory',['$http', function($http) {
	
	var _agendamento;
	var _lstAgendamentosComConsulta;
	var _conteudoProntuarioMudou;

	var _salvarConsulta = function(agendamento) {
		return $http.post('https://localhost:8443/salvarConsulta', angular.copy(agendamento));
	};	
	
	
	return {
		getAgendamento: function() { return _agendamento; },
		setAgendamento: function(agendamento) { _agendamento = agendamento; },
		getInicioAgendamento: function() { return _agendamento.start; },
		setInicioAgendamento: function(inicio) { _agendamento.start = inicio; },
		getFimAgendamento: function() { return _agendamento.end; },
		setFimAgendamento: function(fim) { _agendamento.end = fim; },
		getConsulta: function() { return _agendamento.consulta; },
		setConsulta: function(consulta) { _agendamento.consulta = consulta; },		
		getId: function() { return _agendamento.consulta.id; },
		setId: function(id) { _agendamento.consulta.id = id; },
		getCliente: function() { return _agendamento.cliente; },
		setCliente: function(paciente) { _agendamento.cliente = cliente; },
		getProntuario: function() { return _agendamento.consulta.prontuario; },
		setProntuario: function(prontuario) { _agendamento.consulta.prontuario = prontuario; },
		getValor: function() { return _agendamento.consulta.valor; },
		setValor: function(valor) { _agendamento.consulta.valor = valor; },
		getInicio: function() { return _agendamento.consulta.inicio; },
		setInicio: function(inicio) { _agendamento.consulta.inicio = inicio; },
		getFim: function() { return _agendamento.consulta.fim; },
		setFim: function(fim) { _agendamento.consulta.fim = fim; },
		getLstAgendamentosComConsulta: function() { return _lstAgendamentosComConsulta; },
		setLstAgendamentosComConsulta: function(lstAgendamentosComConsulta) { _lstAgendamentosComConsulta = lstAgendamentosComConsulta; },
		getConteudoProntuarioMudou: function() { return _conteudoProntuarioMudou; },
		setConteudoProntuarioMudou: function(conteudoProntuarioMudou) { _conteudoProntuarioMudou = conteudoProntuarioMudou; },
		salvarConsulta: _salvarConsulta,
	};
}]);