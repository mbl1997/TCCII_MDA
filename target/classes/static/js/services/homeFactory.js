angular.module('mda').factory('homeFactory',['$http', function($http) {
	var ctrl = this;
	var homeFactory = {};
	ctrl.funcionario = {};
	
	
	homeFactory.funcionarioLogado = function(funcionario) {
		return $http.get(
			'https://localhost:8443/userLogado',
			funcionario
				
		);
	};
	
	return homeFactory;
	
}]);