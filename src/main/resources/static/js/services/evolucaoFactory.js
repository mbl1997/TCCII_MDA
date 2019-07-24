angular.module('mda').factory('evolucaoFactory',['$http', function($http) {
	
	var _evolucao;
	var _editandoevolucao;
	
	var _listarevolucao = function() {
		return $http.get(
			'https://localhost:8443/listarevolucoes'
		);
	};
	
	var _salvarevolucao = function(evolucao) {
		return $http.post(
			'https://localhost:8443/salvarevolucoes',
			evolucao
		);
	};
	
	var _salvarArquivo = function(arquivo) {
		return $http.post(
			'https://localhost:8443/api/upload/multi/model',
			arquivo
		);
	};
	
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
	
	var _imprimirRelatorioevolucao = function(evolucao) {
		return $http.post(
			'https://localhost:8443/imprimirRelatorioevolucao',
			evolucao, {responseType:'arraybuffer'}
		);
	};
	
	var _excluirevolucao = function(evolucao) {
		return $http.post(
			'https://localhost:8443/excluirevolucoes/',
			evolucao
		);
	};
	
	return {
		getevolucao: function() { return _evolucao; },
		setevolucao: function(evolucao) { _evolucao = evolucao; },
		listarevolucao: _listarevolucao,
		isEditandoevolucao: function() { return _editandoevolucao; },
		setEditandoevolucao: function(editandoevolucao) { _editandoevolucao = editandoevolucao; },
		salvarevolucao: _salvarevolucao,
		salvarArquivo: _salvarArquivo,
		imprimirRelatorioevolucao: _imprimirRelatorioevolucao,
		listarFuncionarios: _listarFuncionarios,
		listarClientes: _listarClientes,
		excluirevolucao: _excluirevolucao
	}
}]);