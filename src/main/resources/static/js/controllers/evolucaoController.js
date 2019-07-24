var lazyModules = ['ui.calendar', 'ui.bootstrap']; 
angular.forEach(lazyModules, function(dependency) {
	angular.module('mda').requires.push(dependency);
});

angular.module('mda').controller("evolucaoController", ['evolucaoFactory','funcionarioFactory', 'agendamentoFactory', 'consultaFactory', '$http', '$mdDialog', 'NgTableParams', '$scope' , '$location', '$uibModal', 'utilService', 'Session',
	function(evolucaoFactory, funcionarioFactory, agendamentoFactory, consultaFactory, $http, $mdDialog, NgTableParams, $scope, $location, $uibModal, utilService, Session) {
	var ctrl = this;
	ctrl.selClientes = agendamentoFactory.listarClientes();
	ctrl.selFuncionarios = agendamentoFactory.listarFuncionarios();
	ctrl.arquivo = {};
	ctrl.x = Session.usuario;
	ctrl.evolucoes = [];
	ctrl.evolucao = {};
	
	$scope.$watch(function () { return ctrl.evolucoes; }, function (newValue, oldValue) {
		ctrl.tableParams = new NgTableParams({ count: 10, sorting: { nomeCompleto: "asc" } }, { counts: [], dataset: ctrl.evolucoes });
	});
	
	// primeiro parametro, sucesso, segundo parametro erro.
	ctrl.listarevolucao = function() {
		evolucaoFactory.listarevolucao().then(function successCallback(response) {
			ctrl.evolucoes = response.data;
			console.log(response.data);
		}, function errorCallback(response) {
			console.log(response.data);
			console.log(response.status);
		});

	}
	
	/**
	 * Abre janela modal do evolução
	 */	
	this.openEventModal = function (evolucao) {	 	
		var modalInstance = $uibModal.open({
			animation: true,
		    ariaLabelledBy: 'modal-title',
		    ariaDescribedBy: 'modal-body',
		    templateUrl: 'pages/modal_evolucao.html',
		    controller: 'evolucaoController',
		    controllerAs: 'ctrl',
		});
		
		modalInstance.result.then(function (selectedItem) {}, function () {			
			agendamentoFactory.setAgendamento({});		
		});
	};
	
	agendamentoFactory.listarClientes().then(
			sucessCallback = function(response){
				ctrl.comboClientes = response.data;
			},
			errorCallback = function (error){
				
	});
	
	funcionarioFactory.listarFuncionarios().then(
			sucessCallback = function(response){
				ctrl.comboFuncionarios = response.data;
			},
			errorCallback = function (error){
				
	});
	
	
	ctrl.cancel = function () {				
		$uibModalInstance.dismiss('cancel');
	}
		
	ctrl.imprimirRelatorioEvolucao = function(evolucao) {
		utilService.setMessage("Gerando relatório ...");
		utilService.showWait();
		evolucaoFactory.imprimirRelatorioevolucao(evolucao).then(
				successCallback = function(response) {
					utilService.hideWait();
					var file = new Blob([response.data], {
				    	type: 'application/pdf'
				    });
				    var fileURL = URL.createObjectURL(file);				    
					window.open(fileURL);							
				},
				errorCallback = function(error) {
					utilService.hideWait();
					utilService.tratarExcecao(error);
				}
		);
	};
	
	
	ctrl.excluirEvolucao = function(evolucao) {
		console.log(evolucao);
		var confirm = $mdDialog.confirm()
		.multiple(true)
		.title('Atenção')
		.textContent('Todas as informações do paciente, serão perdidas. Tem certeza que deseja continuar?')				
		.ok('Sim')
		.cancel('Não');
		
		evolucaoFactory.excluirevolucao(evolucao).then(function successCallback(response) {
			ctrl.listarevolucao();
			ctrl.evolucao = response.data;
		}, function errorCallback(response) {
			console.log(response.data);
			console.log(response.status);
		});
		
	}
	
	ctrl.salvar = function(evolucao) {
		evolucaoFactory.salvarevolucao(evolucao).then(function successCallback(response) {	
			alert('evolucao cadastrado com sucesso!');
//			$mdDialog.alert(
//					$mdDialog.alert()
//						.multiple(true)
//						.clickOutsideToClose(true)
//						.title('Cadastro de evolucaos')
//						.textContent('evolucao cadastrado com sucesso!')
//						.ariaLabel('Alerta')
//						.ok('Ok')						
//				);	
			ctrl.listarevolucao();
			ctrl.evolucao = {};	
			ctrl.evolucao = response.data;
			$scope.frmEvolucao.$setPristine();
			$mdDialog.hide();	
		}, function errorCallback(response) {
			console.log(response.data);
			console.log(response.status);
		});
		
	}
	
	ctrl.salvarArquivo = function(arquivo) {
		evolucaoFactory.salvarArquivo(arquivo).then(function successCallback(response) {	
			alert('arquivo cadastrado com sucesso!');
			ctrl.arquivo = {};	
			ctrl.arquivo = response.data;
		}, function errorCallback(response) {
			console.log(response.data);
			console.log(response.status);
		});
		
	}
	ctrl.funcionarioLogado = function(x) {
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
			  url: 'https://localhost:8443/userLogado/'
			}).then(function successCallback(response) {
			    Session.create(response.data);
			    ctrl.x = response.data;
			  }, function errorCallback(response) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
			  });
		
	}
	ctrl.funcionarioLogado();
	ctrl.listarevolucao();
	
}]);