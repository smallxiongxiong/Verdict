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

  function DiagnoseOfflineCtrl($scope,JudgementResource,blockUI) {
      var partBlock = blockUI.instances.get('partBlock');
      $scope.pageNum = 1;
      $scope.pageSize =10;
      partBlock.start();
      JudgementResource.getAll({
              pageNum : $scope.pageNum,
              pageSize: $scope.pageSize
          },
          function (data) {
              console.log(data);
              $scope.judgements = data;
              partBlock.stop();
          },function (err) {
              console.log(err);
              partBlock.stop();
          }
      );
      $scope.getPre = function () {
          if($scope.pageNum<0){
              return;
          }
          partBlock.start();
          $scope.pageNum = $scope.pageNum -1;
          JudgementResource.getAll({
                  pageNum : $scope.pageNum,
                  pageSize: $scope.pageSize
              },
              function (data) {
                  console.log(data);
                  $scope.judgements = data;
                  partBlock.stop();
              },function (err) {
                  console.log(err);
                  partBlock.stop();
              }
          );
      }
      $scope.getNext = function () {
          partBlock.start();
          $scope.pageNum = $scope.pageNum + 1;
          JudgementResource.getAll({
                  pageNum : $scope.pageNum,
                  pageSize: $scope.pageSize
              },
              function (data) {
                  console.log(data);
                  $scope.judgements = data;
                  partBlock.stop();
              },function (err) {
                  console.log(err);
                  partBlock.stop();
              }
          );
      }




  }

  angular.module('module.diagnosis.offline',[])
    .config(['$stateProvider',routeConfig])
    .controller('DiagnoseOfflineCtrl', ['$scope','JudgementResource','blockUI',DiagnoseOfflineCtrl]);


})();
