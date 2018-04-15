/**
 * Created by jiegc on 12/13/16.
 */

(function() {
    'use strict';

    var commandResultController = function($q,$scope,$translate,storageService,CommandResource,CustomCommandResource,localSaveResource,toaster,blockUI) {

        //var itemNo = 1;
        var ontInfo = storageService.getObject("ont");

        var partBlock = blockUI.instances.get('partBlock');
        if(partBlock.state().blocking) {
            partBlock.stop();
        }

        $scope.clearCommandResult = function() {
            //itemNo = 1;
            $scope.executeCommandsResult = "";
        }

        /*$scope.saveCommandResult = function() {
            localSaveResource.sav(
                {
                    SN: ontInfo['GPONSN'],
                    category : "commands" + "_" + $scope.command,
                    content : $scope.executeCommandsResult
                },
                function(data){
                    var msg = "Save File Successfully!"
                    toaster.pop({ type: 'success', body: msg});
                },
                function(err){
                    var msg = "Save file with error = " + err;
                    toaster.pop({ type: 'error', body: msg});
                }
            );
        }*/

        function saveCommandResult(command) {
            localSaveResource.sav(
                {
                    SN: ontInfo['GPONSN'],
                    category : "commands" + "_" + command,
                    content : $scope.executeCommandsResult
                },
                function(data){
                    //toaster.pop({ type: 'success', body: $translate.instant('module.msg.saveFileSuccessfully')});                                  
                },
                function(err){
                    var msg = $translate.instant('module.msg.saveFileWithError') + err;
                    toaster.pop({ type: 'error', body: msg});
                }
            );
        }

        $scope.getCommandResult = function() {
            partBlock.start();

            console.log("$scope.value: " + $scope.value);
            if($scope.command != undefined && $scope.command != null) {
                var parameters = {};
                parameters['ip'] = storageService.getIP();
                parameters['item'] = $scope.command;
                CommandResource.get(parameters, function(data) {
                    partBlock.stop();
                    if (data.Discription !== undefined) {
                        //console.log("status: " + data.Status);
                        //console.log("data: " + data);
                        $scope.executeCommandsResult = ($scope.executeCommandsResult===undefined?"":$scope.executeCommandsResult)
                        + data.Discription + "\n";
                        saveCommandResult($scope.command);
                    }
                }, function(err){
                    console.log(err);
                    partBlock.stop();
                });
            } else if($scope.value != undefined && $scope.value != null) {
                var commandArr = $scope.value.split(",");

                executeCommandArr(commandArr);
            }
        }

        function executeCommandArr(commandArr) {
            commandArr.reduce(function(p,command) {
                return p.then(function() {
                    return executeCommand(command);
                });
            },$q.when(true)).then(function(finalResult) {
                console.log("finished execute command");
                saveCommandResult($scope.title);
                partBlock.stop();
            }, function(err) {
                partBlock.stop();
                // error here
            })
        }

        function executeCommand(command) {
            var deferred = $q.defer();
            var parameters = {};
            parameters['ip'] = storageService.getIP();
            var body = {Command:command + "\n"};
            var promise = CustomCommandResource.save(parameters, body).$promise;
            promise.then(function(data) {
                    console.log("status: " + data.Status);
                    console.log("Discription: " + data.Discription);
                    //$scope.executeCommandsResult = ($scope.executeCommandsResult===undefined?"":$scope.executeCommandsResult) + itemNo + ". " + command.name + ":" + "\n";
                    if(data.Discription !== undefined) {
                        $scope.executeCommandsResult = ($scope.executeCommandsResult===undefined?"":$scope.executeCommandsResult)
                        + data.Discription + "\n";
                    }
                    //itemNo++;
                    deferred.resolve();
            });
            return deferred.promise;
        }

        $scope.executeBatchCommands = function(){
            $scope.title = "batch";
            var selectedCommandArr = [];
            $scope.commandsArr.forEach(function(command){
                if(command.selected || $scope.selectAllCommand) {
                    //selectedCommandArr.push(command.value);
                    var commandValueArr = command.value.split(",");
                    commandValueArr.forEach(function(commandValue){
                        selectedCommandArr.push(commandValue);
                    })
                }
            });

            partBlock.start();
            executeCommandArr(selectedCommandArr);
        };

        $scope.chkAll = function() {
            angular.forEach($scope.commandsArr, function (command) {
                command.selected = $scope.selectAllCommand;
            });
        }

        $scope.chk = function(command,selected) {
            if(!command.selected) {
                $scope.selectAllCommand = false;
            }
        }
    }


    /* @ngInject */
    function commandResult() {
        var directive = {
            controller: 'commandResultController',
            replace: true,
            restrict: 'EA',
            templateUrl: 'common/directives/commandResult/commandResult.html'
        };
        return directive;
    };

    angular.module('directive.commandResult',[])
        .directive('commandResult', commandResult)
        .controller('commandResultController', ['$q','$scope','$translate','storageService','CommandResource',
            'CustomCommandResource','localSaveResource','toaster','blockUI',commandResultController]);
})();
