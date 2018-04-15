(function() {
    'use strict';

    /**
     * @ngdoc function
     * @name Lens
     * @description
     * # DiagnoseOnlineCtrl
     * Controller of the nff
     */


    function routeConfig($stateProvider) {
        $stateProvider.state('home.online', {
            url: '/diagnoseOnline',
            views: {
                'ontView': {
                    controller: 'DiagnoseOnlineCtrl',
                    templateUrl: 'modules/diagnosis/online/online.html'
                }
            }
        });
    }

    function DiagnoseOnlineCtrl($scope, OnlineGuiResource) {
        OnlineGuiResource.query({}, function(data) {
            $scope.diagnosisItemsData = data;
            $scope.showColumnLabel = true;
        });
    }

    angular.module('module.diagnosis.online', [])
        .config(['$stateProvider',routeConfig])
        .controller('DiagnoseOnlineCtrl', ['$scope','OnlineGuiResource',DiagnoseOnlineCtrl]);


})();
