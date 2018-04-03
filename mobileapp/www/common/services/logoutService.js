/**
 * Created by jiegc on 1/23/17.
 */

(function(){
    'use strict';
    var LogoutService= function($window,storageService,telnetService) {
        return {
            logout: function() {
                redirectToLoginPage();
                storageService.logout();
                telnetService.telnetOff();
            }
        }

        function redirectToLoginPage() {
            redirect('index.html', 'index.html');
        }

        function redirect(currentPage, newPage) {
            newPage = newPage || '/';
            var path = $window.location.pathname;
            var i = path.indexOf(currentPage);
            $window.location.href = path.substring(0, i) + newPage;
        }
    }

    angular.module('services.logoutService',[])
        .factory('LogoutService',['$window','storageService','telnetService',LogoutService]);

})();