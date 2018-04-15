(function () {
  'use strict';

  function routeConfig($stateProvider) {
    $stateProvider.state('home.debug', {
      url: '/debug',
      views: {
        'ontView': {
          controller: 'DebugCtrl',
          templateUrl: 'modules/system/debug/debug.html'
        }
      }
    });
  }

  function DebugCtrl($scope,$window,$state,storageService) {


  }

  angular.module('module.system.debug',[])
    .config(['$stateProvider',routeConfig])
    .controller('DebugCtrl', ['$scope','$window','$state','storageService',DebugCtrl]);


})();
