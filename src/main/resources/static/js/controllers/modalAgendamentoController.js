var lazyModules = ['ui.calendar', 'ui.bootstrap']; 
angular.forEach(lazyModules, function(dependency) {
	angular.module('mda').requires.push(dependency);
});

angular.module('mda').controller('modalAgendamentoController', ['$scope', '$http',
	'$location', '$mdDialog', 'agendamentoFactory', 'modalAgendamentoFactory', 'Session', '$uibModal' , '$uibModalInstance', 'modalAgendamentoService', 'consultaFactory', 'funcionarioFactory', 'utilService',
	function ($scope, $http, $location, $mdDialog,	agendamentoFactory, modalAgendamentoFactory, Session, $uibModal, $uibModalInstance, modalAgendamentoService, consultaFactory, funcionarioFactory, utilService) {
	
	var ctrl = this;		
	ctrl.x = Session.usuario;

	ctrl.agendamentoCarregado = agendamentoFactory.getAgendamentoCarregado();
	ctrl.agendamento = agendamentoFactory.getAgendamento();
	
	
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
	
	ctrl.editable = function (start) {	 	
		if(start < new Date()){
			return true;
		}else{
			return false;
		}
	};
	
	ctrl.editableNaoCompareceu = function (start) {	 	
		if(start > new Date()){
			return true;
		}else{
			return false;
		}
	};
	
	agendamentoFactory.listarClientes().then(
			sucessCallback = function(response){
				ctrl.comboClientes = response.data;
			},
			errorCallback = function (error){
				
			});
	
	ctrl.iniciarConsulta = function(agendamento) {		
		consultaFactory.setAgendamento(agendamento);
		if (agendamento.consulta) {						
			$location.path('/consulta');
		} else {
			if (agendamento.cliente) {								
				consultaFactory.setConsulta({});
				consultaFactory.setId(null);				
				consultaFactory.setProntuario(null);
				consultaFactory.setValor(0);
				consultaFactory.setInicio(new Date());
				consultaFactory.setFim(null);
				$location.path('/consulta');
			} else {
				$mdDialog.show(
					$mdDialog.alert()
					.multiple(true)
						.clickOutsideToClose(true)
						.title('Algo saiu errado ...')
						.textContent("Não foi possível localizar o paciente da consulta!")
						.ariaLabel('Alerta')
						.ok('Ok')						
				);							
			}
		}
		$uibModalInstance.close();			
	};
	
	funcionarioFactory.listarFuncionarios().then(
			sucessCallback = function(response){
				ctrl.comboFuncionarios = response.data;
			},
			errorCallback = function (error){
				
			});
	
	 /**
	  * Atualiza o campo descricao do agendamento
	  */
	 var updateTitle = function (agendamento) {		
		 return agendamento.descricao ? agendamento.cliente.nomeCompleto + " (" +
				 agendamento.descricao + ")" : agendamento.cliente.nomeCompleto;			  
	 };
	 
		ctrl.cancel = function () {				
			$uibModalInstance.dismiss('cancel');
		};	
	 
	 
	 /**
		 * Confirma com o usuário a remoção do evento
		 */
		ctrl.confirmarRemocaoEvento = function (agendamento) {		
			modalAgendamentoService.openConfirmModal();
			
			$uibModalInstance.close();
							
		};	
		
		ctrl.removerAgendamento = function(agendamento){
			
			agendamentoFactory.removerAgendamento(agendamento).then(
					successCallback = function(response) {					
										angular.element('.calendar').fullCalendar('removeEvents',agendamento.id);				
					},errorCallback = function (error, status){
										//utilService.hideWait();
										utilService.tratarExcecao(error);			  						
									}
							);
						angular.element('.calendar').fullCalendar('removeEvents',agendamento.id);				
			$uibModalInstance.close();
							
		$mdDialog.show(
				$mdDialog.alert()
					.clickOutsideToClose(true)
					.multiple(true)
					.title('Removido com Sucesso')
					.textContent("Agendamento Removido com Sucesso!")
					.ariaLabel('Alerta')
					.ok('Ok')						
			);
		
		};
		
	 
	ctrl.salvar = function (agendamento) {
		$uibModalInstance.close();
		if(!agendamento.valor){
		utilService.setMessage("Salvando agendamento ...");
		utilService.showWait();	
		$scope.agendamentoForm.$setPristine();
		}
		agendamentoFactory.salvarAgendamento(agendamento).then(
					successCallback = function(response) {	
						var event = angular.element('.calendar').fullCalendar('clientEvents',agendamento.id);																								
						if (event.length > 0) {
							agendamento.title = updateTitle(agendamento);
							atualizarViewFC();
							if(agendamento.valor){
							utilService.hideWait();
							$mdDialog.show(
										$mdDialog.alert()
											.multiple(true)
											.clickOutsideToClose(false)
											.title('Sucesso!')
											.textContent("Valor salvo com sucesso")
											.ariaLabel('Alerta')
											.ok('Ok')						
									);
							}else{
								utilService.hideWait();
								$mdDialog.show(
										$mdDialog.alert()
											.multiple(true)
											.clickOutsideToClose(false)
											.title('Sucesso!')
											.textContent("Agendamento salvo com sucesso")
											.ariaLabel('Alerta')
											.ok('Ok')						
									);
							}
						} function errorCallback(response) {		
							utilService.hideWait();
							$mdDialog.show(
								$mdDialog.alert()
								.multiple(true)
									.clickOutsideToClose(true)
									.title('Algo saiu errado ...')
									.textContent("Não foi possível encontrar o agendamento com o id informado!")
									.ariaLabel('Alerta')
									.ok('Ok')						
							);
							utilService.hideWait();
						}
						
	});			
	
	}
	
	 var atualizarViewFC = function() {	
				 		 
		 angular.element('.calendar').fullCalendar('removeEvents');
		 // Atualiza a view para o caso de haver algum evento semanal
		 view = angular.element('.calendar').fullCalendar('getView');
		 agendamentoFactory.listarAgendamentos(view.start, view.end).then(
				 successCallback = function (response) {					 					 
					 angular.element('.calendar').fullCalendar('renderEvents',response.data);
					 utilService.hideWait();
				 },
				 errorCallback = function (error) {	
					 utilService.hideWait();
					 utilService.tratarExcecao(error);
				 }
		 );		
		 utilService.hideWait();
	 };
	 
	 ctrl.imprimirImpostoRenda = function(agendamento) {
			utilService.setMessage("Gerando Declaração ...");
			utilService.showWait();
			agendamentoFactory.imprimirImpostoRenda(agendamento).then(
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
		
		ctrl.imprimirAtestado = function(agendamento) {
			utilService.setMessage("Gerando Atestado ...");
			utilService.showWait();
			agendamentoFactory.imprimirRelatorioAtestado(agendamento).then(
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
		
		ctrl.imprimirReciboCliente = function(agendamento) {
			utilService.setMessage("Gerando Atestado ...");
			utilService.showWait();
			agendamentoFactory.imprimirReciboCliente(agendamento).then(
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
		
}]);
