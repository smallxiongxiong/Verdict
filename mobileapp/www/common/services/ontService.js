/**
 * Created by jiegc on 08/14/17.
 */

(function(){
  'use strict';

  var OntService  = function($q,$http,OntAliveResource,storageService,httpUserService){
    var nffOperationErr = "Nff Operation Error";
    var i = 0;
    var local = this;

    this.nffOperationOff = function() {
      var deferred = $q.defer();
      var params = {};
      params['ip'] = storageService.getIPFromLocalStorage();
      params['u'] = storageService.getUserNameFromLocalStorage();
      params['p'] = storageService.getPasswordFromLocalStorage();
      params['Enable'] = off;
      $http.get('http://' + params['ip'] +'/nff.cgi?Enable=' + params['Enable'] +'&p=' +
      params['p'] +'&u=' + params['u'],{})
          .success(function(res) {
            console.log(res);
            deferred.resolve(res);
          }).error(function(error) {
            console.log('error: ' + error);
            var msg = nffOperationErr;
            deferred.reject(msg);
          });
      return deferred.promise;
    };

    this.nffOperationsOn = function(ip,userName, password) {
      var deferred = $q.defer();
      var params = {};
      params['ip'] = ip;
      params['u'] = userName;
      params['p'] = password;
      params['Enable'] = true;
      //$http.get('http://135.251.204.233/nff.cgi?Enable=false&p=nE7jA%5m&u=telecomadmin',{})
      $http.get('http://' + params['ip'] +'/nff.cgi?Enable=' + params['Enable'] +'&p=' +
      params['p'] +'&u=' + params['u'],{})
          .success(function(res) {
            console.log(res);
            if(res.indexOf("error") == -1) {
              deferred.resolve(res);
            } else {
              deferred.reject(res);
            }
          }).error(function(error) {
            console.log('error: ' + error);
            var msg = nffOperationErr;
            deferred.reject(msg);
          });
      return deferred.promise;
    };

    this.useCorrectUserAndPassTurnOnNff = function(ip,user,password,arr,def) {
      var deferred = def || $q.defer();
      if(user === "" || password === "" || user === undefined || password === undefined) {
        getUserPassAndTryTurnOnNff(ip,deferred);
      } else {
        local.nffOperationsOn(ip,user,password).then(function(data) {
          //console.log(data);
          deferred.resolve(data);
        },function(err) {
          //console.log(err);
          if(arr.length === 0) {
            getUserPassAndTryTurnOnNff(ip,deferred);
          } else if(arr.length == i+1) {
            deferred.reject(err);
          } else {
            ++i;
            local.useCorrectUserAndPassTurnOnNff(ip,arr[i].httpUser,arr[i].httpPass,arr,deferred);
          }
        });
      }
      return deferred.promise;
    };

    function getUserPassAndTryTurnOnNff(ip,def) {
      httpUserService.getUserPassArr().then(function(data) {
        console.log(data);
        i = 0;
        local.useCorrectUserAndPassTurnOnNff(ip,data[i].httpUser,data[i].httpPass,data,def);
      });
    }

    this.checkOntAlive = function(ip) {
      var deferred = $q.defer();
      var params = {};
      params['ontIP'] = ip;
      OntAliveResource.operation(params,function(data) {
        console.log(data);
        deferred.resolve(data);
      },function(err) {
        console.log(err);
        deferred.reject(err);
      });
      return deferred.promise;
    };

  }

  angular.module('services.ontService', [])
      .service('ontService', ['$q','$http','OntAliveResource','storageService','httpUserService',OntService]);

})();
