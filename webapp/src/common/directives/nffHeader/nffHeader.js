/**
 * Created by jiegc on 11/16/16.
 */

(function() {
    'use strict';

    /**
     * @ngdoc directive
     * @name directive.nffHeader
     * @restrict EA
     * @scope
     * @restrict E
     *
     * @example
     * <nff-header></nff-header>
     */
    var nffHeaderController = function($scope,$state,$window,LogoutService){
        $scope.logout = function(){
            LogoutService.logout();
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
                title: 'ONU本地诊断系统',
                alt: 'Nokia',
                route: 'home.ontInfo',
                logo: 'assets/images/NOKIA_FONT_LOGO.png'
            }

            /**
             * Goto home page if production name of icon is clicked
             */
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
