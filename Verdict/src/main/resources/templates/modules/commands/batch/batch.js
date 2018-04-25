(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name webappApp.controller:AboutCtrl
   * @description
   * # AboutCtrl
   * Controller of the nff
   */


  function routeConfig($stateProvider) {
    $stateProvider.state('home.batch', {
      url: '/batchCommand',
      params: {value: null},
      views: {
        'ontView': {
          controller: 'BatchCommand',
          templateUrl: 'modules/commands/batch/batch.html'
        }
      }
    });
  }

  function BatchCommand($scope,BatchGuiResource,CmdMenusResource,blockUI) {
      var partBlock = blockUI.instances.get('partBlock');
      $scope.testFC = function () {
          partBlock.start();
          BatchGuiResource.get(
              {
                  source:$scope.source
              },
              function (data) {
                  $scope.result = data;
                  partBlock.stop();
              },function (err) {
                  partBlock.stop();
              }
          );
      }
      $scope.trainFC = function () {
          partBlock.start();
          CmdMenusResource.train(
              {
                  words:$scope.words
              },
              function (data) {
                  $scope.words='';
                  partBlock.stop();
              },function (err) {
                  partBlock.stop();
              }
          );
      }

  }

  angular.module('module.commands.batch',[])
    .config(['$stateProvider',routeConfig])
    .controller('BatchCommand', ['$scope','BatchGuiResource','CmdMenusResource','blockUI',BatchCommand]);


})();
