(function(){
    'use strict';

    var nffUpgtade = function(){
        var filter= function(input) {
	      if (input) {
	      	 return input.split("(")[0];

	      } 
	    }; 
        return filter;
    };
    angular.module('filter.nffUpgtadeService',[]).filter('nffUpgtade',nffUpgtade);
})();