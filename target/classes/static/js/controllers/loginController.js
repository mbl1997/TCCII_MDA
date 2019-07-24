angular.module('mda').controller('loginController', ['$scope', '$rootScope', '$http', '$location', 
	'$mdDialog', 'loginFactory', 'funcionarioFactory' , 'Session', 'homeFactory', 'utilService', 	function($scope, $rootScope, 
			$http, $location, $mdDialog, loginFactory, funcionarioFactory, Session, homeFactory, utilService) {
	var ctrl = this;
	ctrl.funcionario = {};
	ctrl.x = {};
	
	$scope.$watch(function () { return ctrl.funcionario; }, function (newValue, oldValue) {
	});
		
	var authenticate = function(credentials, callback) {		
		var headers = credentials ? {authorization : "Basic "
			+ btoa(credentials.username + ":" + credentials.password)
		} : {};
		
		loginFactory.login(headers).then(function(response) {			
			if (response.data.name) {
				$rootScope.authenticated = true;	
				ctrl.funcionarioLogado(response.data.name);
				$location.path('/home');
			} else {											
				$rootScope.authenticated = false;
				credentials = {};	
				$scope.frmLogin.$setPristine();
			}			
			callback && callback();
		}, function() {
			$rootScope.authenticated = false;
			callback && callback();
		});
	}
	
	
	
	authenticate();	
	ctrl.credentials = {};
	ctrl.login = function() {		
		authenticate(ctrl.credentials, function() {
			if ($rootScope.authenticated) {				
				ctrl.error = false;
				
				loginFactory.realizarBackup().then(
						successCallback = function(response) {},
						errorCallback = function(error) {														
							utilService.tratarExcecao(error);
						}
				);
		    } else {		    	
		    	ctrl.error = true;
				credentials = {};	
				$scope.frmLogin.$setPristine();
				
		    	$mdDialog.show(
						$mdDialog.alert()
							.clickOutsideToClose(true)
							.title('Login Inválido')
							.textContent('Usuario e senha inválidos')
							.ariaLabel('Alerta')
							.ok('Ok')						
					);	
		    }
		  });			
	};
	
	ctrl.backup = function() {
	loginFactory.realizarBackup().then(
			successCallback = function(response) {},
			errorCallback = function(error) {														
				utilService.tratarExcecao(error);
			}
	);
	}
	
	ctrl.logout = function() {
		loginFactory.logout();
		$rootScope.authenticated = false;
		ctrl.credentials = {};
	    $location.path("/");
	};
	

	
	ctrl.funcionarioLogado = function(nomeFuncionario) {
		/*homeFactory.funcionarioLogado(nomeFuncionario).then(function successCallback(response){
		Session.create(response.data);
		ctrl.funcionario = response.data;
		return ctrl.funcionario;
	}, function errorCallback(response) {
		console.log(response.data);
		console.log(response.status);
	});*/
		$http({
			  method: 'GET',
			  url: 'https://localhost:8443/userLogado/', nomeFuncionario
			}).then(function successCallback(response) {
			    Session.create(response.data);
			    ctrl.x = response.data;
			  }, function errorCallback(response) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  });
		
	}
	
}]);