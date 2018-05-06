(function(){
    'use strict';

    var EsResource  = function($resource){

        var resource;
        resource = $resource('/es/query',
            {
            },
            {
            	post: {
//                    method : 'post',
//                    params : {
//                    	query:'@query'
//                    	//query:'{ "match_all": {} }'
//                    },
//                    headers: {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'}
            		method : 'post',
                    isArray: false,
                    params : {
                        conditions:'@conditions'
                    }
                }
            }
        );
        return resource;
    };

    var WenshujiansuoGuiResource = function ($resource) {

        var resource =  $resource('templates/assets/data/wenshujiansuo.json',
            {
                query: {isArray: false}
            }
        );
        return resource;
    };
    
    angular.module('resources.esResource', [])
        .factory('esResource',  ['$resource',EsResource])
        .factory('guiResource', ['$resource',WenshujiansuoGuiResource]);

})();