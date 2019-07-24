angular.module('mda').controller("homeController", ['homeFactory', 
	function($location) {
	var ctrl = this;
	
	$ctrl.$location = $location;
	
	ctrl.funcionario = {};
	
	$scope.$watch(function () { return ctrl.funcionario; }, function (newValue, oldValue) {
	});
	
	ctrl.funcionarioLogado = function(funcionario) {
		homeFactory.funcionarioLogado().then(function successCallback(response){
		ctrl.funcionario = response.data;
		return ctrl.funcionario.nomeCompleto;
	}, function errorCallback(response) {
		console.log(response.data);
		console.log(response.status);
	});

	}
}]);

