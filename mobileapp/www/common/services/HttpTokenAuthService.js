(function(){
    'use strict';

    angular
        .module('services.httpTokenAuthService',[])
        .service('httpTokenAuthService', ['$window',httpTokenAuthService]);

    function httpTokenAuthService($window){

        var auth = {
            saveToken: saveToken,
            getToken: getToken,
            deleteToken: deleteToken
        };

        return auth;

        function saveToken(token){
            $window.localStorage.setItem('node-js-store-auth-token', token);
        }

        function getToken(){
            return $window.localStorage.getItem('node-js-store-auth-token');
        }

        function deleteToken(){
            $window.localStorage.removeItem('node-js-store-auth-token');
        }

    }
})();