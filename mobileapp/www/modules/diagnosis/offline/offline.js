(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name jiegc
   * @description
   * # DiagnoseOfflineCtrl
   * Controller of the nff
   */


  function routeConfig($stateProvider) {
    $stateProvider.state('home.offline', {
      url: '/diagnoseOffline',
      views: {
        'ontView': {
          controller: 'DiagnoseOfflineCtrl',
          templateUrl: 'modules/diagnosis/offline/offline.html'
        }
      }
    });
  }

  function DiagnoseOfflineCtrl($scope,articlesService,$state) {
    $scope.newArticle={};
    $scope.newArticle.author='xiong';
    $scope.subForm = function () {
      articlesService.addArticle($scope.newArticle).then(function(){
        $state.go('home.ontInfo');
      });
    }
  }

  angular.module('module.diagnosis.offline',[])
    .config(['$stateProvider',routeConfig])
    .controller('DiagnoseOfflineCtrl', ['$scope','articlesService','$state',DiagnoseOfflineCtrl]);


})();
