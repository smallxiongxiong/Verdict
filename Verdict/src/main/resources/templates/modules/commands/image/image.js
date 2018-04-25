(function () {
  'use strict';


  function routeConfig($stateProvider) {
    $stateProvider.state('home.imageInfo', {
      url: '/imageCommand',
      views: {
        'ontView': {
          controller: 'ImageCtrl',
          templateUrl: 'modules/commands/image/image.html'
        }
      }
    });
  }

  function ImageCtrl($scope,WenshujiansuoGuiResource,QueryByConditionResource,blockUI) {
      var partBlock = blockUI.instances.get('partBlock');
      $scope.check = new Array();
      WenshujiansuoGuiResource.query({},function(data){
        $scope.guiArr = data;
          angular.forEach($scope.guiArr, function(guiItem){
              angular.forEach(guiItem.item, function(itemName){
                  $scope.check[guiItem.name+itemName] = false;
              });
          });
      });

      $scope.hideFlag = function (flag) {
          $scope.check[flag] = false;
      }
      $scope.showFlag = function (flag) {
          $scope.check[flag] = true;
      }
      $scope.queryByConditions = function () {
          var conditions = new Object();
          if(!$scope.contextCondition)$scope.contextCondition=true;
          if($scope.contextCondition)conditions["caseName"] = $scope.inputCondition;
          if($scope.titleCondition)conditions["appellor"] = $scope.inputCondition;
          // if($scope.causeCondition)conditions["cause"] = $scope.inputCondition;
          // if($scope.keyCondition)conditions["key"] = $scope.inputCondition;
          angular.forEach($scope.guiArr, function(guiItem){
              angular.forEach(guiItem.item, function(itemName){
                  //if($scope.check[guiItem.name+itemName])conditions[guiItem.name] = itemName;
              });
          });
          partBlock.start();
          QueryByConditionResource.getQuery({
                  conditions :JSON.stringify(conditions)
          },
          function (data) {
              $scope.dataResult = data;
              partBlock.stop();
          },function (err) {
              console.log(err);
              partBlock.stop();
          });
      }
  }

  angular.module('module.commands.image',[])
    .config(['$stateProvider',routeConfig])
    .controller('ImageCtrl', ['$scope','WenshujiansuoGuiResource','QueryByConditionResource','blockUI',ImageCtrl]);


})();
