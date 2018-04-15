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

    function FactoryRecoveryCtrl($scope,$translate, ManagementResource, storageService, LogoutService) {
        var factoryRecovery = this;

        $scope.executeCommand = function() {
            //var deferred = $q.defer();
            ManagementResource.query({
                ip: storageService.getIP(),
                item: 'reset'
            }, function(data) {
                var executeCommandResult = data.Status;

                if(executeCommandResult=="success"){
                	$scope.executeCommandResult=$translate.instant('module.msg.success');
                }else{
                	$scope.executeCommandResult=$translate.instant('module.msg.fail');
                }
                //deferred.resolve();
                LogoutService.logout();
            });
            //return deferred.promise;
        };
    }

    angular.module('module.system.recovery', [])
        .config(['$stateProvider',routeConfig])
        .controller('FactoryRecoveryCtrl', ['$scope','$translate','ManagementResource','storageService','LogoutService',FactoryRecoveryCtrl]);


})();
