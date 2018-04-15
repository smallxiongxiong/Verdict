/**
 * Created by jiegc on 1/25/17.
 */

(function() {
    'use strict';

    var nffUpgradeController = function($q,$scope,$http,$filter,$rootScope,LocalIpResource,ImageFilesResource,ManagementResource,$translate, storageService,toaster,LogoutService) {
        var ftpServerIP = "";
        var ftpServerPort = "10021";
		var filePath = "ont/image/";


        $scope.getImages = function() {
            ImageFilesResource.imageFiles({},function(data) {
                $scope.images = data.images;
            });
        };
        
        

        $scope.upgradeFile = function(type) {
            toaster.pop({ type: 'success', body: $translate.instant('module.msg.upgradeInProgress')});
            getFtpServerIP().then(function(){
                var parameters = {};
                parameters['ip'] = storageService.getIP();
                parameters['item'] = type;
                parameters['Server'] = ftpServerIP;
                parameters['Port'] = ftpServerPort;
                parameters['File'] = filePath + $scope.imageSelected;

                ManagementResource.query(parameters, function(data) {
                    if(data && data.Status && data.Status == "success"){
                        toaster.pop({ type: 'success', body: $translate.instant('module.msg.upgradeSuccess')});
                        LogoutService.logout();
                        toaster.pop({ type: 'success', body: $translate.instant('module.msg.reboot')});
                    }else{
                        toaster.pop({ type: 'error', body: $translate.instant('module.msg.upgradeFailed')});
                    }
                });
            });
        };

        function getFtpServerIP() {
            var parameters = {};
            parameters['ontIP'] = storageService.getIP();
            var deferred = $q.defer();
            LocalIpResource.localIp(parameters,
                function(data){
                    ftpServerIP = data['ftpServerIP'];
                    deferred.resolve(ftpServerIP);
                },
                function(err){
                    var msg = $translate.instant('module.msg.getFtpServerfail') + err;
                    toaster.pop({ type: 'error', body: msg});
                }
            );
            return deferred.promise;
        }
    };

    /* @ngInject */
    function nffUpgrade() {
        var directive = {
            controller : 'nffUpgradeController',
            replace : true,
            restrict : 'E',
            templateUrl : "common/directives/nffUpgrade/nffUpgrade.html"
        };
        return directive;
    };

    angular.module('directive.nffUpgrade',[])
           .directive('nffUpgrade',nffUpgrade)
           .controller('nffUpgradeController',['$q','$scope','$http','$filter','$rootScope','LocalIpResource','ImageFilesResource','ManagementResource',
                '$translate', 'storageService','toaster','LogoutService',nffUpgradeController]);
})();