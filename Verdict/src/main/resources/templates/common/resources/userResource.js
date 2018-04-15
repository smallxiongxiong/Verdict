
(function(){
    'use strict';

    var UserResource = function ($resource) {

        var resource =  $resource('/user/login',
            {
            },
            {
                login: {
                    method : 'post',
                    isArray: false
                }
            }
        );
        return resource;
    };


    var userResource = angular.module('resources.userResource', []);
    userResource.factory('UserResource', ['$resource',UserResource]);

})();
