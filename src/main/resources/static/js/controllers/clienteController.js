angular.module('mda').controller("clienteController", ['clienteFactory', 'agendamentoFactory', 'consultaFactory', '$http', '$mdDialog', 'NgTableParams', '$scope' , '$location', 'Session', 
	function(clienteFactory, agendamentoFactory, consultaFactory, $http, $mdDialog, NgTableParams, $scope, $location, Session) {
	var ctrl = this;

	/*
	$scope.clientes = [];
	$scope.cliente={};
	*/
	ctrl.clientes = [];
	ctrl.cliente = {};
	ctrl.x = Session.usuario;
	
	$scope.$watch(function () { return ctrl.clientes; }, function (newValue, oldValue) {
		ctrl.tableParams = new NgTableParams({ count: 10, sorting: { nomeCompleto: "asc" } }, { counts: [], dataset: ctrl.clientes });
	});
	
	if (clienteFactory.isEditandoCliente()) {
		ctrl.cliente = clienteFactory.getCliente();	
		console.log(ctrl.cliente);
	}
	
	ctrl.buscarCep = function(nCep) {
		ctrl.loading = true;
		
		var cep = nCep.replace(/[^0-9]/g,'');
		$http.get("https://viacep.com.br/ws/" + cep + "/json/").then(
				successCallback = function(response) {
					ctrl.cliente.rua = response.data.logradouro;
					ctrl.cliente.numero = "";
					ctrl.cliente.bairro = response.data.bairro;
					ctrl.cliente.cidade = response.data.localidade;
					ctrl.cliente.uf = response.data.uf;
					ctrl.loading = false;					
				},
			  	errorCallback = function (error, status){
					ctrl.cliente.logradouro = "";
					ctrl.cliente.numero = "";
					ctrl.cliente.bairro = "";
					ctrl.cliente.localidade = "";
					ctrl.cliente.uf = "";
					ctrl.loading = false;
			  	}		
		);						
	};

	ctrl.editarCliente = function(cliente) {
		console.log(cliente.dataNascimento);
		clienteFactory.setCliente(cliente);
		clienteFactory.setEditandoCliente(true);
		$location.path("/editarCliente");
	};
	
	// primeiro parametro, sucesso, segundo parametro erro.
	ctrl.listarClientes = function() {
		clienteFactory.listarClientes().then(function successCallback(response) {
			ctrl.clientes = response.data;
			console.log(response.data);
		}, function errorCallback(response) {
			console.log(response.data);
			console.log(response.status);
		});

	}
	
	ctrl.excluirClientes = function(cliente) {
		console.log(cliente);
		var confirm = $mdDialog.confirm()
		.multiple(true)
		.title('Atenção')
		.textContent('Todas as informações do paciente, serão perdidas. Tem certeza que deseja continuar?')				
		.ok('Sim')
		.cancel('Não');
		
		clienteFactory.excluirClientes(cliente).then(function successCallback(response) {
			ctrl.listarClientes();
			ctrl.clientes = response.data;
			console.log(response.data);
			console.log(response.status);
		}, function errorCallback(response) {
			console.log(response.data);
			console.log(response.status);
		});
		
	}		
	
	
	ctrl.salvarClientes = function(cliente) {
		clienteFactory.salvarClientes(cliente).then(function successCallback(response) {	
			alert('Cliente cadastrado com sucesso!');
			ctrl.cliente = {};	
			ctrl.clientes = response.data;
			$scope.frmCliente.$setPristine();
		}, function errorCallback(response) {
			alert("CPF Inválido");
			console.log(response.data);
			console.log(response.status);
		});
		
	}
	
	ctrl.funcionarioLogado = function(x) {
		$http({
			  method: 'GET',
			  url: 'https://localhost:8443/userLogado/'
			}).then(function successCallback(response) {
			    Session.create(response.data);
			    ctrl.x = response.data;
			  }, function errorCallback(response) {
				 console.log(response.data);
				 console.log(response.status);
			  });
		
	}
	ctrl.funcionarioLogado();
	ctrl.listarClientes();
}]);