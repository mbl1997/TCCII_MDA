// Modulos desta controller
 var lazyModules = ['ui.calendar', 'ui.bootstrap','ui.select','ngSanitize']; 
angular.forEach(lazyModules, function(dependency) {
	angular.module('mda').requires.push(dependency);
});

angular.module('mda').controller('agendaController', ['$scope', '$mdDialog', 'agendamentoFactory', '$uibModal', 'funcionarioFactory', 
                                                           function ($scope, $mdDialog, agendamentoFactory, $uibModal, funcionarioFactory) {
	
  var ctrl = this;
  ctrl.selClientes = agendamentoFactory.listarClientes();
  ctrl.selFuncionarios = agendamentoFactory.listarFuncionarios();
  ctrl.funcionario = {};
    
 
    
  var select = function(start, end) {	  
	  console.log("select");
	  limparDadosAgendamento();
	  
	  agendamentoFactory.setAgendamentoCarregado(null);	  

	  // Verifica se existe um horario pre definido
	  if (!start.hasTime()) {		
		  var time = moment();
		  start = moment(start).hour(time.hour()).minute(time.minute()).second(0).millisecond(0);
		  end = moment(start); // a consulta deve terminar no mesmo dia
		  end.add(30, 'm');
	  }
		
	  var dataInicialAgendamento = start.local();
	  var dataFinalAgendamento = end.local();
		
	  agendamentoFactory.setStart(new Date(dataInicialAgendamento));
	  agendamentoFactory.setEnd(new Date(dataFinalAgendamento));
	  agendamentoFactory.setFormatedStart(start.format('HH:mm'));		
	  agendamentoFactory.setEditable(true);
	  
	  ctrl.openEventModal();
  };
  
  
  $scope.$watch(function () { return agendamentoFactory.getAgendamento(); }, function (newValue, oldValue) {
   	  ctrl.agendamento = newValue;
  });
  
  
//  /**
//	 * Abre janela modal de erro
//	 */	
//	this.openConfirmModal = function (size) {	 	
//		var modalInstance = $uibModal.open({
//			animation: true,
//		    ariaLabelledBy: 'modal-title',
//		    ariaDescribedBy: 'modal-body',
//		    templateUrl: 'pages/confirmacao_agendamento_modal.html',
//		    controller: 'ModalAgendamentoCtrl',
//		    controllerAs: 'ctrl',      
//		    size: size
//		});    
//	};
	
	
  const modalTemplate = `
     <!-- Main content -->
    <section class="content">
      <div class="row">
        <!-- left column -->
        <div class="col-md-6">
          <!-- general form elements -->
          <div class="box box-primary">
            <div class="box-header with-border">
              <h3 class="box-title">Quick Example</h3>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <form role="form">
              <div class="box-body">
                <div class="form-group">
                  <label for="exampleInputEmail1">Email address</label>
                  <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
                </div>
                <div class="form-group">
                  <label for="exampleInputPassword1">Password</label>
                  <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                </div>
                <div class="form-group">
                  <label for="exampleInputFile">File input</label>
                  <input type="file" id="exampleInputFile">

                  <p class="help-block">Example block-level help text here.</p>
                </div>
                <div class="checkbox">
                  <label>
                    <input type="checkbox"> Check me out
                  </label>
                </div>
              </div>
              <!-- /.box-body -->

              <div class="box-footer">
                <button type="submit" class="btn btn-primary">Submit</button>
              </div>
            </form>
          </div>
          <!-- /.box -->
  `;
  
	this.openEventModal = function (size) {	 	
		var modalInstance = $uibModal.open({
			animation: true,
		    ariaLabelledBy: 'modal-title',
		    ariaDescribedBy: 'modal-body',
		    templateUrl: 'pages/modal_agendamento.html',
		  //template: modalTemplate,
		    controller: 'modalAgendamentoController',
		    controllerAs: 'ctrl',
		    size: size
		});
		
		modalInstance.result.then(function (selectedItem) {}, function () {			
			agendamentoFactory.setAgendamento({});		
		});
	};
	
	
	
//	  limparDadosAgendamento();	  
//	  agendamentoFactory.setAgendamentoCarregado(null);	  
//
//	  // Verifica se existe um horario pre definido
//	  if (!start.hasTime()) {		
//		  var time = moment();
//		  start = moment(start).hour(time.hour()).minute(time.minute()).second(0).millisecond(0);
//		  end = moment(start); // a consulta deve terminar no mesmo dia
//		  end.add(1, 'm');
//	  }
//		
//	  var dataInicialAgendamento = start.local();
//	  var dataFinalAgendamento = end.local();
//		
//	  agendamentoFactory.setStart(new Date(dataInicialAgendamento));
//	  agendamentoFactory.setEnd(new Date(dataFinalAgendamento));
//	  agendamentoFactory.setFormatedStart(start.format('HH:mm'));		
//	  agendamentoFactory.setEditable(true);
//	  
//	  modalAgendamentoService.openEventModal();
//  };
	
	ctrl.chamaAgenda = function(funcionario) {	  
		window.location.href = "#/agenda";								
	  };
	
	
	
	
  var eventClick = function(event, jsEvent, view) {	  
	  console.log("click");
	  event.formatedStart = event.start.format('HH:mm');							
	  agendamentoFactory.setAgendamento(angular.copy(event));
	  agendamentoFactory.setAgendamentoCarregado(angular.copy(event));	  	  
	 
	  ctrl.openEventModal();											
  };
  
  
  funcionarioFactory.listarFuncionarios().then(
			sucessCallback = function(response){
				ctrl.comboFuncionarios = response.data;
			},
			errorCallback = function (error){
				
			});
  
  
	
  var eventRender = function( event, element, view ) { 	  
	  console.log("eventRender");
  }
  
  var limparDadosAgendamento = function() {
	  agendamentoFactory.setAgendamento({});
  };
  
  var eventDrop = function(event, delta, revertFunc, jsEvent, ui, view) {	
	  console.log("EvendDrop");
	  agendamentoFactory.salvarAgendamento(event).then(
				successCallback = function(response) {					
									angular.element('.calendar').fullCalendar('removeEvents',event.id);
									atualizarViewFC();
				},errorCallback = function (error, status){
									//utilService.hideWait();
									utilService.tratarExcecao(error);			  						
								}
						);
	  
  };
	
  var viewRender = function (view, element) {	  
	  console.log("viewRender");
	  listarAgendamento(view.start, view.end);	 
  };    
  
  
  /**
   * Popula o calendario com os agendamentos do BD e persiste novos agendamentos
   * na view atual, caso necessário
   */ 
  var listarAgendamento = function(dataInicial, dataFinal) {	  
	  $mdDialog.show({			
		    template: 
		    	'<head>' +		
		    		'<meta charset="UTF-8" />'+		
				'</head>' +
				'<md-dialog>'+
					'<md-dialog-content>'+
						'<div class="md-dialog-content">'+
							'<p>Carregando agendamentos ...</p>'+
							'<div layout="row" layout-sm="column" layout-align="center center" aria-label="wait">'+
					    		'<md-progress-circular md-mode="indeterminate" ></md-progress-circular>'+
					    	'</div>'+
						'</div>'+
					'</md-dialog-content>'+	
				'</md-dialog>',
		    parent: angular.element(document.body),		    
		    clickOutsideToClose: false		    
		});
	  agendamentoFactory.listarAgendamentos(dataInicial, dataFinal).then(
			  successCallback = function (response) {	
				  console.log(response.data);
				  angular.element('.calendar').fullCalendar('removeEvents');
				  angular.element('.calendar').fullCalendar('renderEvents',response.data);
				  $mdDialog.hide();
			  },
			  errorCallback = function (error) {				  
				  $mdDialog.hide();				  
				  utilService.tratarExcecao(error);				  
			  }			  
	  );		  
  };      

  /* config object */
  ctrl.uiConfig = {
		  calendar: ({
			  header : {
				  left : 'prev,next today',
				  center : 'title',
				  right : 'month,agendaWeek,agendaDay'
			  },		
			  //height: 450,
			  timezone: "local",
			  allDaySlot: false,
			  locale : 'pt-br',
			  navLinks : true, // can click day/week names to navigate views
			  selectable : true,
			  slotDuration : "00:30:00",
			  slotLabelFormat : [ 'ddd D/M', 'HH:mm' ],
			  timeFormat : "HH:mm",
			  selectHelper : true,		
			  editable : true,
			  eventLimit : true, // allow "more" link when too many events
			  select : select,
			  eventClick : eventClick,		
			  eventDrop : eventDrop,
			  eventRender: eventRender,
			  viewRender: viewRender			
		  })
  };    
  
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
  
  /**
   * Limpa os dados pertinentes a um agendamento
   */
  var limparDadosAgendamento = function() {
	//  agendamentoFactory.setAgendamento({});
  };
 
}]);