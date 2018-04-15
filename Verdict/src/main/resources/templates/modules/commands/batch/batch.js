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

  function BatchCommand($scope,BatchGuiResource,$stateParams) {
    console.log("$stateParams: " + $stateParams.value);
    $scope.queryBtnDisabled = false;
    $scope.commandsArr = $stateParams.value;
    /*BatchGuiResource.query({},function(data){
      $scope.commandsArr = data;
    });*/

  }

  angular.module('module.commands.batch',[])
    .config(['$stateProvider',routeConfig])
    .controller('BatchCommand', ['$scope','BatchGuiResource','$stateParams',BatchCommand]);


})();
