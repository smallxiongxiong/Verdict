

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

  function ConnectorCtrl(authService,$rootScope,$scope,$state,$q,$http,$translate,storageService,ontResource,localSaveResource,localSaveTableResource,ontInstallResource,toaster,blockUI,ontService,$timeout) {

    //$scope.ipAddress = "135.251.201.101";
    $scope.userName = "";
    $scope.password = "";

    var login = function(user){

        authService.logIn(user).then(function(){
            $state.go('home.ontInfo');
        });

    };


    $scope.submitForm = function(userName,password){
        $state.go('home.ontInfo');
        $scope.userName = userName;
        $scope.password = password;
        var user = {username:userName ,password:password};
        login(user);
    };

  }


  angular.module('modules.connector',[])
    .config(['$stateProvider',routeConfig])
    .controller('ConnectorCtrl', ConnectorCtrl);

})();
