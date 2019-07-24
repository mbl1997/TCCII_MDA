angular.module('mda').factory('modalAgendamentoFactory', ['$http',  function($http) {
	var _agendamento = {};
	var _agendamentoCarregado = {};	
	var _msgErro;
	var _msgConfirmacao;
	var _tipoConfirmacao;
	
	var _listarClientes = function() {
		return $http.get(
			'https://localhost:8443/listarClientes'
		);
	};
	
	var _listarFuncionarios = function() {
		return $http.get(
			'https://localhost:8443/listarFuncionarios'
		);
	};
	

	return {		
		getAgendamento: function() { return _agendamento; },
		setAgendamento: function(agendamento) { _agendamento = agendamento; },		
		getStart: function() { return _agendamento.start; },
		setStart: function(start) { _agendamento.start = start; },
		getEnd: function() { return _agendamento.end; },
		setEnd: function(end) { _agendamento.end = end; },
		getMsgErro: function() { return _msgErro; },
		setMsgErro: function(msgErro) { _msgErro = msgErro; },
		getMsgConfirmacao: function() { return _msgConfirmacao; },
		setMsgConfirmacao: function(msgConfirmacao) { _msgConfirmacao = msgConfirmacao; },
		getTipoConfirmacao: function() { return _tipoConfirmacao; },
		setTipoConfirmacao: function(tipoConfirmacao) { _tipoConfirmacao = tipoConfirmacao; },
		listarClientes: _listarClientes,
		listarFuncionarios: _listarFuncionarios,
		getListarClientes: function() { return _listarClientes; },
		setListarClientes: function(listarClientes) { _listarClientes = listarClientes; },
		getAgendamentoCarregado: function() { return _agendamentoCarregado; },
		setAgendamentoCarregado: function(agendamentoCarregado) { _agendamentoCarregado = agendamentoCarregado; },
		getEditable: function() { return _agendamento.editable; },
		setEditable: function(editable) { _agendamento.editable = editable; },
		getFormatedStart: function() { return _agendamento.formatedStart; },
		setFormatedStart: function(formatedStart) { _agendamento.formatedStart = formatedStart; },
	};
}]);