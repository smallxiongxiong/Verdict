(function() {
    'use strict';

    /**
     * @ngdoc function
     * @name Lens
     * @description
     * # UploadCtrl
     * Controller of the nff
     */


    function routeConfig($stateProvider) {
        $stateProvider.state('home.reboot', {
            url: '/reboot',
            views: {
                'ontView': {
                    controller: 'RebootCtrl',
                    templateUrl: 'modules/system/reboot/reboot.html'
                }
            }
        });
    }

    function RebootCtrl($scope,$translate, ManagementResource, storageService,LogoutService) {
        var factoryRecovery = this;

        $scope.executeCommand = function() {
            //var deferred = $q.defer();
            ManagementResource.query({
                ip: storageService.getIP(),
                item: 'reboot'
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

    angular.module('module.system.reboot', [])
        .config(['$stateProvider',routeConfig])
        .controller('RebootCtrl', ['$scope','$translate','ManagementResource','storageService','LogoutService',RebootCtrl]);


})();
