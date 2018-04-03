(function(){
    'use strict';

    var OntVersionsResource  = function($q,$timeout,$resource){

        var resource;
        resource = $resource('versions',
            {
            },
            {
                query: {isArray: false}
            }
        );
        return resource;
    };


    var versionsResource = angular.module('resources.ontVersionsResource', []);

    versionsResource
        .factory('ontVersionsResource',  ['$q','$timeout','$resource',OntVersionsResource]);

})();
