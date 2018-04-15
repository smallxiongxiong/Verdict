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

  function ConnectorCtrl($rootScope,$scope,$state,storageService,UserResource,toaster) {
      $scope.telUserName = storageService.getUserNameFromLocalStorage();
      $scope.telPassword = storageService.getPasswordFromLocalStorage();
      $scope.signIn = function (userName,userPwd) {
          $state.go('home');
          // UserResource.login({
          //     userName:userName,
          //     userPwd:userPwd
          //     },
          //     function(data){
          //       if(data.userName!=null){
          //           toaster.pop({ type: 'success', body:'login success'});
          //           $state.go('home');
          //       }else{
          //           toaster.pop({ type: 'fail', body:"login fail"});
          //           $state.go('home');
          //       }
          //     },
          //     function(err){
          //         toaster.pop({ type: 'error', body: msg});
          //     }
          // );
      }



  }


  angular.module('modules.connector',[])
    .config(['$stateProvider',routeConfig])
    .controller('ConnectorCtrl',  ['$rootScope','$scope','$state',
          'storageService','UserResource','toaster',ConnectorCtrl]);

})();
