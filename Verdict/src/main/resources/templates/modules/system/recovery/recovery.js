(function() {
    'use strict';

    /**
     * @ngdoc function
     * @name Lens
     * @description
     * # DiagnoseOfflineCtrl
     * Controller of the nff
     */


    function routeConfig($stateProvider) {
        $stateProvider.state('home.recovery', {
            url: '/factoryRecovery',
            views: {
                'ontView': {
                    controller: 'FactoryRecoveryCtrl',
                    templateUrl: 'modules/system/recovery/recovery.html'
                }
            }
        });
    }

    function FactoryRecoveryCtrl($scope,$translate, ManagementResource, storageService,blockUI) {
        var partBlock = blockUI.instances.get('partBlock');
        var factoryRecovery = this;
        $scope.CRFsentence="";
        $scope.CRFresult="";
        $scope.executeCommand = function(CRFsentence) {
            //var deferred = $q.defer();
            partBlock.start();
            ManagementResource.crf({
                sentence: CRFsentence,
            }, function(data) {
                $scope.CRFresult=data;
                partBlock.stop();
            });
            //return deferred.promise;
        };
    }

    angular.module('module.system.recovery', [])
        .config(['$stateProvider',routeConfig])
        .controller('FactoryRecoveryCtrl', ['$scope','$translate','ManagementResource','storageService','blockUI',FactoryRecoveryCtrl]);


})();
