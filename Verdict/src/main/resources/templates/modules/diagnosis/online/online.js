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

    function DiagnoseOnlineCtrl($scope, JudgementResource,JudgementResource1,JudgementResource2,blockUI) {
        var partBlock = blockUI.instances.get('partBlock');

        $scope.findByID = function () {
            $scope.idType;
            partBlock.start();
            JudgementResource1.getMixResults({
                    idType:$scope.idType,
                    idValue : $scope.idValue
                },
                function (data) {
                    $scope.judgement = data[0];
                    $scope.splitRes = data[1];
                    partBlock.stop();
                },function (err) {
                    console.log(err);
                    partBlock.stop();
                }
            );
        }
    }

    angular.module('module.diagnosis.online', [])
        .config(['$stateProvider',routeConfig])
        .controller('DiagnoseOnlineCtrl', ['$scope','JudgementResource','JudgementResource1','JudgementResource2','blockUI',DiagnoseOnlineCtrl]);


})();
