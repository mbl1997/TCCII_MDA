angular.module('mda').factory('dashboardFactory',['$http', function($http) {
	var _funcionario;
	
	var ctrl = this;
	
	
	var _listarValores = function() {
		return $http.get('https://localhost:8443/listarValoresPorFuncionario');
	};
	
	var _listarNovosUsuarios = function() {
		return $http.get('https://localhost:8443/listarNovosUsuarios');
	};
	
	var _listarAgendamentosDoMesPorFuncionario = function() {
		return $http.get('https://localhost:8443/listarAgendamentosDoMesPorFuncionario');
	};
	
	return {
		getFuncionario: function() { return _funcionario; },
		setFuncionario: function(funcionario) { _funcionario = funcionario; },
		listarValores: _listarValores,
		listarNovosUsuarios: _listarNovosUsuarios,
		listarAgendamentosDoMesPorFuncionario: _listarAgendamentosDoMesPorFuncionario
	}
}]);