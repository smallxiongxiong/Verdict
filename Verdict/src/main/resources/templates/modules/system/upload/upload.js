(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name upload.js
   * @description
   * # UploadCtrl
   * Controller of the nff
   * add by jiegc 1/6/2017
   */


  function routeConfig($stateProvider) {
    $stateProvider.state('home.upload', {
      url: '/upload',
      views: {
        'ontView': {
          controller: 'UploadCtrl',
          templateUrl: 'modules/system/upload/upload.html'
        }
      }
    });
  }

  function UploadCtrl($q,$scope,$translate,LocalIpResource,ManagementResource, storageService,toaster) {

    var typeUpload = "upload";
    var typeDownload = "download";
    var ftpServerIP = "";
    var ftpServerPort = "10021";


    $scope.getParams = function() {
      $scope.params = ['/configs', '/logs', '/tmp'];
    };

    $scope.uploadDownload = function(type) {

      getFtpServerIP().then(function(){
        var parameters = {};
        parameters['ip'] = storageService.getIP();
        parameters['item'] = type;
        parameters['Server'] = ftpServerIP;
        parameters['Port'] = ftpServerPort;
        if(type === typeUpload) {
          parameters['OntPath'] = $scope.ontPath;
          parameters['ServerFile'] = $scope.serverFile;
        }else if(type === typeDownload){
          parameters['OntFile'] = $scope.ontFile;
          var fileName = "";
          if($scope.ontFile != undefined) {
            var arr = $scope.ontFile.split("/");
            fileName = arr[arr.length-1];
          }
          parameters['ServerPath'] = $scope.serverPath + "/" + fileName;
        }

        ManagementResource.query(parameters, function(data) {
          if(data && data.Status && data.Status == "success"){
            if(type===typeUpload){
          		 var msg = $translate.instant('module.uploadDownLoad.upload') + $translate.instant('module.msg.success')
               toaster.pop({ type: 'success', body: msg});
          	}else if(type===typeDownload){
          		 var msg = $translate.instant('module.uploadDownLoad.download') + $translate.instant('module.msg.success')
               toaster.pop({ type: 'success', body: msg});
          	}
          }else{
            if(type===typeUpload){
          		 var msg = $translate.instant('module.uploadDownLoad.upload') + $translate.instant('module.msg.fail')
               toaster.pop({ type: 'error', body: msg});
          	}else if(type===typeDownload){
          		 var msg = $translate.instant('module.uploadDownLoad.download') + $translate.instant('module.msg.fail')
               toaster.pop({ type: 'error', body: msg});
          	}
          }

        });
      });
    };

    function getFtpServerIP() {
      var parameters = {};
      parameters['ontIP'] = storageService.getIP();
      var deferred = $q.defer();
      LocalIpResource.localIp(parameters,
          function(data){
            ftpServerIP = data['ftpServerIP'];
            deferred.resolve(ftpServerIP);
          },
          function(err){
            toaster.pop({ type: 'error', body: err});
          }
      );
      return deferred.promise;
    }

}

  angular.module('module.system.upload',[])
    .config(['$stateProvider',routeConfig])
    .controller('UploadCtrl', ['$q','$scope','$translate','LocalIpResource','ManagementResource',
        'storageService','toaster',UploadCtrl]);
})();
