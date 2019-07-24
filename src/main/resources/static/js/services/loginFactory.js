angular.module('mda').factory('loginFactory', ['$http', 'consts', function($http, consts) {		
	var _login = function(headers) {
		return $http.get('https://localhost:8443/user', {headers : headers});				
	};
	
	var _logout = function() {
		return $http.post('logout', {});
	};
	
	var _realizarBackup = function() {
		return $http.get('https://localhost:8443/realizarBackup');
	}
	
	return {		
		login: _login,
		logout: _logout,
		realizarBackup: _realizarBackup		
	};
}]);