angular.module('mda').filter('monetarioFormat', function() {
    return function(valor) {
    	if (valor && valor.toFixed) {    
    		var novoValor = valor.toFixed(2).toString();
    		if (novoValor.split(".").length > 1 || novoValor.split(",").length > 1) {    			   
    			novoValor = 'R$ ' + novoValor.replace('.',',');
    		} else {    			   
    			novoValor = 'R$ ' + novoValor + ",00";
    		}
    		return novoValor;
    	} else {
    		return "R$ 0,00";
    	} 	
    };
});