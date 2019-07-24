angular.module('mda').service('utilService', ['$rootScope', '$mdDialog', function($rootScope, 
		$mdDialog) {
	var _message;
	
	/**
	 * Trata excessoes que possam ocorrer
	*/
	var _tratarExcecao = function(error) {	
		try {
			// captura de excecao enviada pela Controller (codigo java)
			msg = error.data.message;
		} catch(erro) {
			// Erro nivel Javascript
			msg = error;
		}
		
		return $mdDialog.show(
			$mdDialog.alert()
				.clickOutsideToClose(true)
				.title('Algo saiu errado contate o Administrador...')
				.textContent(msg)
				.ariaLabel('Alerta')
				.ok('Ok')						
		);	
	};  
	
	var _showWait = function(size) {		
		$mdDialog.show({
			controller: 'WaitController',			
		    templateUrl: 'pages/waiting.html',
		    parent: angular.element(document.body),		    
		    clickOutsideToClose: false		    
		});
	};
	
	var _hideWait = function() {
		$rootScope.$emit("hide_wait");			
	};
	
	return {
		getMessage: function() { return _message; },
		setMessage: function(message) { _message = message; },	
		tratarExcecao: _tratarExcecao,
		hideWait: _hideWait,
	    showWait: _showWait
	}
}]);