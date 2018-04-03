/**
 * Created by zchen on 11/21/16.
 */


(function(){
  'use strict';

  function routeConfig($stateProvider) {
    $stateProvider.state('connector', {
      url: '/',
      views: {
        '': {
          controller: 'ConnectorCtrl',
          templateUrl: 'modules/connector/connector.html'
        }
      }
    });
  }

  function ConnectorCtrl($rootScope,$scope,$state) {


    $scope.submitForm = function(ipAddress,telUserName,telPassword){
        $state.go('home.ontInfo');

    };



  }


  angular.module('modules.connector',[])
    .config(['$stateProvider',routeConfig])
    .controller('ConnectorCtrl',  ['$rootScope','$scope','$state',
          ConnectorCtrl]);

})();
