
(function () {
    'use strict';
    function routeConfig($stateProvider) {
        $stateProvider.state('home.details', {
            params:{'data':null},
            views: {
                'ontView': {
                    controller: 'infoDetailsCtrl',
                    templateUrl: 'modules/details/details.html'
                }
            }
        });
    };


    function infoDetailsCtrl($scope,$stateParams,$ionicHistory,$state) {
        $scope.articleDetails = $stateParams.data;
        $scope.goBcak = function(){
            //$ionicHistory.goBack();
            $state.go('home.ontInfo');
        }
    }



    angular.module('modules.details',[])
        .config(['$stateProvider',routeConfig])
        .controller('infoDetailsCtrl', ['$scope','$stateParams','$ionicHistory','$state',infoDetailsCtrl]);
})();
