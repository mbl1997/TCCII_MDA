angular.module('mda').filter('booleanFormat', function() {
    return function(valor) {
    	return valor?"Sim":"Não";   	
    };
});