angular.module('mda').service('modalAgendamentoService', ['$uibModal', 'agendamentoFactory', function($uibModal, agendamentoFactory) {
	/**
	 * Abre janela modal de erro
	 */	
	this.openConfirmModal = function (size) {	 	
		var modalInstance = $uibModal.open({
			animation: true,
		    ariaLabelledBy: 'modal-title',
		    ariaDescribedBy: 'modal-body',
		    templateUrl: 'pages/confirmacao_agendamento_modal.html',
		    controller: 'modalAgendamentoController',
		    controllerAs: 'ctrl',      
		    size: size
		});    
	};
	
	/**
	 * Abre janela modal do agendamento
	 */	
	this.openEventModal = function (size) {	 	
		var modalInstance = $uibModal.open({
			animation: true,
		    ariaLabelledBy: 'modal-title',
		    ariaDescribedBy: 'modal-body',
		    templateUrl: 'pages/agendamento_modal.html',
		    controller: 'modalAgendamentoController',
		    controllerAs: 'ctrl',      
		    size: size
		});
	    
		modalInstance.result.then(function (selectedItem) {}, function () {			
			agendamentoFactory.setAgendamento({});					
		});
	};
}]);