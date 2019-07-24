angular.module('mda', ['ngRoute', 'ngMaterial', 'ngTable']).constant("consts", {
	BASE_URL: "http://localhost:8443",
		})

.config(['$routeProvider', '$httpProvider', '$mdDateLocaleProvider' ,function($routeProvider, $httpProvider, $mdDateLocaleProvider) {		
		$routeProvider.
			when('/home', { 
				templateUrl: "pages/dashboard.html",
				controller: "dashboardController",
				controllerAs: "ctrl"
			}).when('/cliente', { 
				templateUrl: "pages/cadastroCliente.html",
				controller: "clienteController",
				controllerAs: "ctrl"
			}).when('/consulta', { 
				templateUrl: "pages/consulta.html",
				controller: "consultaController",
				controllerAs: "ctrl"
			}).when('/editarCliente', { 
				templateUrl: "pages/cadastroCliente.html",
				controller: "clienteController",
				controllerAs: "ctrl"
			}).when('/editarFuncionario', { 
				templateUrl: "pages/cadastroFuncionario.html",
				controller: "funcionarioController",
				controllerAs: "ctrl"
			}).when('/financeiro', { 
				templateUrl: "pages/financeiro.html",
				controller: "financeiroController",
				controllerAs: "ctrl"
			}).when('/agenda', { 
				templateUrl: "pages/agenda.html",
				controller: "agendaController",
				controllerAs: "ctrl"
			}).when('/consultarCliente', { 
				templateUrl: "pages/consultarCliente.html",
				controller: "clienteController",
				controllerAs: "ctrl"
			}).when('/cadastrarFuncionario', { 
				templateUrl: "pages/cadastroFuncionario.html",
				controller: "funcionarioController",
				controllerAs: "ctrl"			
			}).when('/consultarFuncionario', { 
				templateUrl: "pages/consultarFuncionario.html",
				controller: "funcionarioController",
				controllerAs: "ctrl"			
			}).when('/despesas', { 
				templateUrl: "pages/despesas.html",
				controller: "financeiroController",
				controllerAs: "ctrl"			
			}).when('/receitas', { 
				templateUrl: "pages/receitas.html",
				controller: "financeiroController",
				controllerAs: "ctrl"			
			}).when('/evolucao', { 
				templateUrl: "pages/evolucao.html",
				controller: "evolucaoController",
				controllerAs: "ctrl"			
			}).when('/preAgenda', { 
				templateUrl: "pages/selecaoFuncionario.html",
				controller: "agendaController",
				controllerAs: "ctrl"			
			}).when('/index', { 
				templateUrl: "index.html",
				controller: "loginController",
				controllerAs: "ctrl"			
			}).otherwise({redirectTo: '/'});
		
		$mdDateLocaleProvider.months = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
	    $mdDateLocaleProvider.shortMonths = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
	    $mdDateLocaleProvider.days = ['Domingo', 'Segunda', 'terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'];
	    $mdDateLocaleProvider.shortDays = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'];
		
		$mdDateLocaleProvider.formatDate = function(date) {
			console.log("aqui");
			return moment(date).format('DD/MM/YYYY');
		};
		
		$mdDateLocaleProvider.parseDate = function(dateString) {
			var m = moment(dateString, 'DD/MM/YYYY', true);
			return m.isValid() ? m.toDate() : new Date(NaN);
		};
		
		
		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
		
	}]).run(['$rootScope', function($rootScope){
		// start watching when the app runs. also starts the Keepalive service by default.

	}]);