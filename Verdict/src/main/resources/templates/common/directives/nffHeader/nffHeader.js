/**
 * Created by jiegc on 11/16/16.
 */

(function() {
    'use strict';

    var nffHeaderController = function($scope,$state,$window,LogoutService){
        $scope.logout = function(){
            LogoutService.logout();
        };
        $scope.go1 = function(){
            $state.go('home.ontInfo');
        };
        $scope.go2 = function(){
            $state.go('home.imageInfo');
        };
        $scope.go3 = function(){
            $state.go('home.offline');
        };
        $scope.go4 = function(){
            $state.go('home.online');
        };
        $scope.go5 = function(){
            $state.go('home.customCommand');
        };
    }

    /* @ngInject */
    function nffHeader($state) {
        var directive = {
            link: link,
            controller : 'nffHeaderController',
            //replace: true,
            scope: {
                nffHeaderTitle: '@'
            },
            restrict: 'EA',
            templateUrl: 'common/directives/nffHeader/nffHeader.html'

        };
        return directive;

        function link(scope, element, attrs) {

            /**
             * Section 1: Product Icon - Product Name
             */
            scope.header = {
                title: 'Verdict Manage Platform',
                alt: 'VV',
                route: 'home.ontInfo',
                logo: 'assets/images/verdict_LOGO.png'
            }


            scope.gotoHome = function () {
                if (scope.header.route) {
                    $state.go(scope.header.route)
                } else {
                    $location.path('/home/ontInfo');
                }
            };
        }


    }
    angular.module('directive.nffHeader',[])
           .directive('nffHeader', ['$state',nffHeader])
           .controller('nffHeaderController',['$scope','$state','$window','LogoutService',nffHeaderController]);
})();
