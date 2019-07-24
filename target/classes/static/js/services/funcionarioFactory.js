angular.module('mda').factory('funcionarioFactory',['$http', function($http) {
	var _funcionario;
	var _editandoFuncionario;
	
	var ctrl = this;
	
	var _listarFuncionarios = function() {
		return $http.get(
			'https://localhost:8443/listarFuncionarios'
		);
	};
	
	var _listarAniversariantesDoMes = function() {
		return $http.get('https://localhost:8443/listarAniversariantesDoMes'
		);
	};
	
	var _listarFuncionarios = function() {
		return $http.get(
			'https://localhost:8443/listarFuncionarios'
		);
	};
	
	var _listarPermissoes = function() {
		return $http.get(
		'https://localhost:8443/listarPermissoes');
	};
	
	
	var _excluirFuncionarios = function(funcionario) {
		return $http.post(
			'https://localhost:8443/deletarFuncionario/',
			funcionario
		);
	};
	
	var _salvarFuncionarios = function(funcionario) {
		var novoFuncionario = angular.copy(funcionario);
		
		if (novoFuncionario.cpf) {
			novoFuncionario.cpf = novoFuncionario.cpf.replace(/[^0-9]/g,'');
		}
		
		novoFuncionario.ativo = true;
		
		CredenciaisFuncionario = {
				funcionario: novoFuncionario,
				senha: novoFuncionario.senha
		}
		
		
		return $http.post(
			'https://localhost:8443/salvarFuncionarios',
			CredenciaisFuncionario
		);
	};
	
		
		
	return {
		getFuncionario: function() { return _funcionario; },
		setFuncionario: function(funcionario) { _funcionario = funcionario; },
		isEditandoFuncionario: function() { return _editandoFuncionario; },
		setEditandoFuncionario: function(editandoFuncionario) { _editandoFuncionario = editandoFuncionario; },
		listarFuncionarios: _listarFuncionarios,
		listarFuncionarios: _listarFuncionarios,
		listarPermissoes: _listarPermissoes,
		salvarFuncionarios: _salvarFuncionarios,
		excluirFuncionarios: _excluirFuncionarios,
		listarAniversariantesDoMes: _listarAniversariantesDoMes
	}
}]);