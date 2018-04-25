(function () {
    'use strict';

    function routeConfig($stateProvider) {
        $stateProvider.state('home.customCommand', {
            url: '/customCommand',
            views: {
                'ontView': {
                    controller: 'CustomCommandCtrl',
                    templateUrl: 'modules/commands/customCommand/customCommand.html'
                }
            }
        });
    }

    function CustomCommandCtrl($scope,CustomCommandResource,DictsResource,blockUI,deleteDictsResource) {
        var partBlock = blockUI.instances.get('partBlock');
        getAllDicts();
        $scope.dict = {key:'',label:'',frequency:1,enable:1};
        $scope.mySubmit = function (dict) {
            if(dict.key=='')return;
            partBlock.start();
            CustomCommandResource.post({
                    key:$scope.dict.key,
                    label:$scope.dict.label,
                    frequency:$scope.dict.frequency,
                    enable:$scope.dict.enable
                },
                function (data) {
                    getAllDicts();
                    partBlock.stop();
                },function (err) {
                    console.log(err);
                    partBlock.stop();
                });

        }
        function getAllDicts() {
            DictsResource.getQuery({
                    pageNum:0,
                    pageSize:10
                },
                function (data) {
                    $scope.dicts = data;
                    partBlock.stop();
                },function (err) {
                    console.log(err);
                    partBlock.stop();
                });
        }
        $scope.deleteDict = function (id) {
            deleteDictsResource.delDict({
                   id:id
                },
                function (data) {
                    getAllDicts();
                    partBlock.stop();
                },function (err) {
                    console.log(err);
                    partBlock.stop();
                });
        }
    }

    angular.module('module.commands.customCommand',[])
        .config(['$stateProvider',routeConfig])
        .controller('CustomCommandCtrl', ['$scope','CustomCommandResource','DictsResource','blockUI','deleteDictsResource',CustomCommandCtrl]);


})();
