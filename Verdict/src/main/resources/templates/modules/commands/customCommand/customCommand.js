(function () {
    'use strict';

    /**
     * @ngdoc function
     * @name webappApp.controller:CustomCommand
     * @description
     * # CustomCommandCtrl
     * Controller of the nff
     */


    function routeConfig($stateProvider) {
        $stateProvider.state('home.customCommand', {
            url: '/customCommand',
            params: {title: null,value: null},
            views: {
                'ontView': {
                    controller: 'CustomCommand',
                    templateUrl: 'modules/commands/customCommand/customCommand.html'
                }
            }
        });
    }

    function CustomCommand($scope,$stateParams) {
        //console.log("$stateParams: " + $stateParams.aa);
        $scope.title = $stateParams.title;
        $scope.value = $stateParams.value;
        /*$scope.queryBtnDisabled = false;

        BatchGuiResource.query({},function(data){
            $scope.commandsArr = data;
        });*/
    }

    angular.module('module.commands.customCommand',[])
        .config(['$stateProvider',routeConfig])
        .controller('CustomCommand', ['$scope','$stateParams',CustomCommand]);


})();
