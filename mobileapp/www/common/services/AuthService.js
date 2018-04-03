(function(){
    'use strict';

    angular
        .module('services.authService',[])
        .service('authService', ['httpTokenAuthService', 'usersService', 'cartService', '$http', '$window','$cookies', authService]);

    function authService(httpTokenAuthService, usersService, cartService, $http, $window, $cookies){

        var auth = {
            isLoggedIn: isLoggedIn,
            register: register,
            logIn: logIn,
            logOut: logOut
        };

        return auth;

        function isLoggedIn(){
            var token = httpTokenAuthService.getToken();

            if(token){
                var payload = JSON.parse($window.atob(token.split('.')[1]));
                return payload.exp > Date.now() / 1000;
            } else {
                return false;
            }
        }
        
        function register(user){
            return $http.post('http://47.96.17.143/register', user).then(function (data) {
                httpTokenAuthService.saveToken(data.data.token);
            });
        }

        function logIn(user){
            return $http.post('http://47.96.17.143/login', user).then(function(data){
                httpTokenAuthService.saveToken(data.data.token);
                usersService.getUserData().then(function(response){
                    var userID = response.data._id;
                    $cookies.put('user.id', userID);
                    cartService.getCart();
                });

            });
        }

        function logOut(){
            httpTokenAuthService.deleteToken();
            $cookies.remove('user.id');
            cartService.getCart();
        }

    }
})();