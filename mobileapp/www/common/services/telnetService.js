/**
 * Created by jiegc on 8/11/17.
 */

(function(){
    'use strict';

    var TelnetService = function($q,$http,OntTelnetResource,httpUserService,storageService) {
        var i = 0;
        var local = this;

        this.telnetOn = function(ip,userName, password) {
            var deferred = $q.defer();
            var params = {};
            params['ontIP'] = ip;
            params['user'] = userName;
            params['password'] = password;
            params['telnetType'] = 'on';
            OntTelnetResource.operation(params,function(data) {
                console.log(data);
                deferred.resolve(data);
            },function(err) {
                console.log(err);
                deferred.reject(err);
            });
            /*$http.get('http://135.251.204.233/system.cgi?p=nE7jA%5m&u=telecomadmin&tl=on',{})
                .success(function(res) {
                    console.log(res);
                    deferred.resolve(res);
                }).error(function(data) {
                    deferred.reject(data);
                });*/
            return deferred.promise;
        };
        this.telnetOff =  function() {
            var deferred = $q.defer();
            var params = {};
            params['ontIP'] = storageService.getIPFromLocalStorage();
            params['user'] = storageService.getUserNameFromLocalStorage();
            params['password'] = storageService.getPasswordFromLocalStorage();
            params['telnetType'] = 'off';
            OntTelnetResource.operation(params,function(data){
                console.log(data);
                deferred.resolve(data);
            },function(err){
                console.log(err);
            });
            return deferred.promise;
        };

        this.useCorrectUserAndPassTurnOnTelent = function(ip,user,password,arr,def) {
            var deferred = def || $q.defer();
            if(user === "" || password === "" || user === undefined || password === undefined) {
                getUserPassAndTryTurnOnTelent(ip,deferred);
            } else {
                local.telnetOn(ip,user,password).then(function(data) {
                    console.log(data);
                    //storageService.saveLoginInfo(ip,user,password);
                    data['user'] = user;
                    data['password'] = password;
                    deferred.resolve(data);
                },function(err) {
                    console.log(err);
                    if(arr.length === 0) {
                        getUserPassAndTryTurnOnTelent(ip,deferred);
                    } else if(arr.length == i+1) {
                        deferred.reject(err);
                    } else {
                        ++i;
                        local.useCorrectUserAndPassTurnOnTelent(ip,arr[i].httpUser,arr[i].httpPass,arr,deferred);
                    }
                });
            }
            return deferred.promise;
        };

        function getUserPassAndTryTurnOnTelent(ip,def) {
            httpUserService.getUserPassArr().then(function(data) {
                console.log(data);
                i = 0;
                local.useCorrectUserAndPassTurnOnTelent(ip,data[i].httpUser,data[i].httpPass,data,def);
            });
        }
    }

    angular.module('services.telnetService',[])
        .service('telnetService',['$q','$http','OntTelnetResource','httpUserService','storageService',TelnetService]);
})();