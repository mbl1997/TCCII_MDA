angular.module('mda').controller("funcionarioController", ['$mdDialog', 'funcionarioFactory', 'NgTableParams', '$scope', '$location', '$http', 'Session',
	function($mdDialog, funcionarioFactory, NgTableParams, $scope, $location, $http, Session) {
	var ctrl = this;

	ctrl.funcionarios = [];
	ctrl.funcionarios = [];
	ctrl.funcionario = {};
	ctrl.permissoes = [];
	ctrl.x = Session.usuario;
	
	//ctrl.funcionarioLogado();
	
	$scope.$watch(function () { return ctrl.funcionarios; }, function (newValue, oldValue) {
		ctrl.tableParams = new NgTableParams({ count: 10, sorting: { nomeCompleto: "asc" } }, { counts: [], dataset: ctrl.funcionarios });
	});
	
	$scope.$watch(function () { return ctrl.permissoes; }, function (newValue, oldValue) {
	});	
	
	if (funcionarioFactory.isEditandoFuncionario()) {
		ctrl.funcionario = funcionarioFactory.getFuncionario();		
	}
	
	
	ctrl.funcionarioLogado = function(funcionario) {
		homeFactory.funcionarioLogado().then(function successCallback(response){
		ctrl.funcionario = response.data;
		return ctrl.funcionario.nomeCompleto;
	}, function errorCallback(response) {
		console.log(response.data);
		console.log(response.status);
	});

	}
	
//	ctrl.funcionarioLogado = function() {
//		homeFactory.funcionarioLogado().then(function successCallback(response){
//		ctrl.funcionario.nomeCompleto = response.data;
//	}, function errorCallback(response) {
//		console.log(response.data);
//		console.log(response.status);
//	});

//	}

	// primeiro parametro, sucesso, segundo parametro erro.
	ctrl.listarFuncionarios = function() {
		funcionarioFactory.listarFuncionarios().then(function successCallback(response) {
			ctrl.funcionarios = response.data;
		}, function errorCallback(response) {
			console.log(response.data);
			console.log(response.status);
		});

	}
	
	var carregarPermissoes = function() {
		funcionarioFactory.listarPermissoes().then(
				successCallback = function(response) {	  
					ctrl.permissoes = response.data;					
				},
				errorCallback = function (error, status){					
					console.log(error, status); 
				}
		);
	}
	
	ctrl.excluirFuncionarios = function(funcionario) {
		var confirm = $mdDialog.confirm()
		.title('Atenção')
		.textContent('Todas as informações do funcionario, serão perdidas. Tem certeza que deseja continuar?')				
		.ok('Sim')
		.cancel('Não');
		
		funcionarioFactory.excluirFuncionarios(funcionario).then(function successCallback(response) {
			ctrl.listarFuncionarios();
			ctrl.funcionarios = response.data;
			console.log(response.data);
			console.log(response.status);
		}, function errorCallback(response) {
			console.log(response.data);
			console.log(response.status);
		});
		
	}
	
	ctrl.buscarCep = function(nCep) {
		ctrl.loading = true;
		
		var cep = nCep.replace(/[^0-9]/g,'');
		$http.get("https://viacep.com.br/ws/" + cep + "/json/").then(
				successCallback = function(response) {
					ctrl.funcionario.rua = response.data.logradouro;
					ctrl.funcionario.numero = "";
					ctrl.funcionario.bairro = response.data.bairro;
					ctrl.funcionario.cidade = response.data.localidade;
					ctrl.funcionario.uf = response.data.uf;
					ctrl.loading = false;					
				},
			  	errorCallback = function (error, status){
					ctrl.funcionario.logradouro = "";
					ctrl.funcionario.numero = "";
					ctrl.funcionario.bairro = "";
					ctrl.funcionario.localidade = "";
					ctrl.funcionario.uf = "";
					ctrl.loading = false;
			  	}		
		);						
	};
	
	ctrl.editarFuncionario = function(funcionario) {
		if (funcionario.dataNascimento) {
			var dataFormatada = new Date(funcionario.dataNascimento);			
			var dia = dataFormatada.getDate() < 10?"0"+dataFormatada.getDate():dataFormatada.getDate();
			var mes = (dataFormatada.getMonth()+1) < 10?"0"+(dataFormatada.getMonth()+1):(dataFormatada.getMonth()+1);
			var ano = dataFormatada.getFullYear();			
			funcionario.dataNascimento = dia + "/" + mes + "/" + ano;
		}
		funcionarioFactory.setFuncionario(funcionario);
		funcionarioFactory.setEditandoFuncionario(true);
		console.log(funcionario);
		$location.path("/editarFuncionario");
	};
	
	
	ctrl.salvarFuncionarios = function(funcionario) {
		funcionarioFactory.salvarFuncionarios(funcionario).then(function successCallback(response) {
			$scope.frmFuncionario.$setPristine();
			alert('Funcionario cadastrado com sucesso!');
			ctrl.funcionario = {};	
			ctrl.funcionarios = response.data;
		}, function errorCallback(response) {
			if(response.data.exception == 'org.springframework.dao.DataIntegrityViolationException'){
				alert('login de usuario já existe cadastrado no sistema')
			}else{
				alert('CPF Inválido');
				console.log(response.data);
				console.log(response.status);
			}
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
	carregarPermissoes();
	ctrl.listarFuncionarios();
}]);