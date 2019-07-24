angular.module('mda').controller('dashboardController', ['$scope', '$rootScope', '$http', '$location', 
	'$mdDialog', 'funcionarioFactory' , 'Session', 'homeFactory', 'agendamentoFactory', 'financeiroFactory', 'utilService', 'dashboardFactory',	function($scope, $rootScope, 
			$http, $location, $mdDialog, funcionarioFactory, Session, homeFactory, agendamentoFactory, financeiroFactory, utilService, dashboardFactory) {
	
	var ctrl = this;
	ctrl.funcionario = {};
	ctrl.valores = {};
	ctrl.consultasDoMesFuncionario = {};
	ctrl.x = Session.usuario;
	ctrl.lstAgendamentos = {};
	ctrl.lstAgendamentosDoDia = {};
	ctrl.lstAgendamentosDoDiaGeral = {};
	ctrl.lstNovosUsuarios = {};
	ctrl.listaReceitaDash = {};

	$scope.$watch(function () { return financeiroFactory.getTotalConsultasPeriodo(); }, function (newValue, oldValue) {		
		ctrl.totalConsultasPeriodo = newValue;
	});
	
	  var carregarContasDoMes = function() {		
		  var dataInicial = moment().startOf('month').local().format();
		  var dataFinal = moment().endOf('month').local().format();
		
		  financeiroFactory.listarDespesasPorPeriodo(dataInicial, dataFinal).then(
				successCallback = function(response) {
					ctrl.totalDespesasMesCorrente = response.data.totalDespesas;
				},
				errorCallback = function(error) {
					utilService.tratarExcecao(error);
				}
		  );
		
		  financeiroFactory.listarConsultasPorPeriodo(dataInicial, dataFinal).then(
				successCallback = function(response) {
					ctrl.totalConsultasMesCorrente = response.data.totalConsultas;
				},
				errorCallback = function(error) {					
					utilService.tratarExcecao(error);
				}
		  );
	  };
	  
	  
	  var listarValores = function() {
		  dashboardFactory.listarValores().then(
			      successCallback = function(response) {		    	  
			    	  ctrl.valores = response.data;	    	  
			  	  },
			  	  errorCallback = function (error, status){			  		
			  	  }
			  );
	  }
	  
	  var pesquisarReceitasPeriodo = function() {
			ctrl.searchDisabled = true;
			financeiroFactory.listarReceitasPorPeriodoDash().then(
					successCallback = function(response) {
						ctrl.listaReceitaDash = response.data;
					},
					errorCallback = function(error) {
						listaReceitaDash = 0;
						ctrl.searchDisabled = false;
						utilService.tratarExcecao(error);
					}
			);
		};
	  
	  var listarAgendamentosDoMesPorFuncionario = function() {
		  dashboardFactory.listarAgendamentosDoMesPorFuncionario().then(
			      successCallback = function(response) {		    	  
			    	  ctrl.consultasDoMesFuncionario = response.data;	    	  
			  	  },
			  	  errorCallback = function (error, status){			  		
			  	  }
			  );
	  }
	  
	  var carregarAniversariantesDoMes = function() {
		  funcionarioFactory.listarAniversariantesDoMes().then(
			      successCallback = function(response) {		    	  
			    	  ctrl.lstAniversariantes = response.data;	    	  
			  	  },
			  	  errorCallback = function (error, status){			  		
			  	  }
			  );
	  }
	  
	  var carregarAgendamentosDoDia = function() {
		  agendamentoFactory.listarAgendamentosDoDia().then(
			      successCallback = function(response) {		    	  
			    	  ctrl.lstAgendamentos = response.data;					    	  			    	 
			  	  },
			  	  errorCallback = function (error, status){		
			  		  utilService.tratarExcecao(error);
			  	  }
			  );
	  }
	  
	  var listarAgendamentosDoDiaGeral = function() {
		  agendamentoFactory.listarAgendamentosDoDiaGeral().then(
			      successCallback = function(response) {		    	  
			    	  ctrl.lstAgendamentosDoDiaGeral = response.data;					    	  			    	 
			  	  },
			  	  errorCallback = function (error, status){		
			  		  utilService.tratarExcecao(error);
			  	  }
			  );
	  }
	  
	  var listarAgendamentosDoDiaFuncionarios = function() {
		  agendamentoFactory.listarAgendamentosDoDia().then(
			      successCallback = function(response) {		    	  
			    	  ctrl.lstAgendamentosDoDia = response.data;					    	  			    	 
			  	  },
			  	  errorCallback = function (error, status){		
			  		  utilService.tratarExcecao(error);
			  	  }
			  );
	  }
	  
	  var carregarNovosUsuarios = function() {
		  dashboardFactory.listarNovosUsuarios().then(
			      successCallback = function(response) {		    	  
			    	  ctrl.lstNovosUsuarios = response.data;					    	  			    	 
			  	  },
			  	  errorCallback = function (error, status){		
			  		  utilService.tratarExcecao(error);
			  	  }
			  );
	  }
	  
	  var carregarAgendamentosDoMes = function() {
		  agendamentoFactory.listarAgendamentosDoMes().then(
			      successCallback = function(response) {		    	  
			    	  ctrl.lstAgendamentosDoMes = response.data;					    	  			    	 
			  	  },
			  	  errorCallback = function (error, status){		
			  		  utilService.tratarExcecao(error);
			  	  }
			  );
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
	listarAgendamentosDoDiaFuncionarios();
	listarAgendamentosDoDiaGeral();
	carregarNovosUsuarios();
	listarAgendamentosDoMesPorFuncionario();
	carregarAniversariantesDoMes();
	carregarAgendamentosDoDia();
	ctrl.funcionarioLogado();
}]);