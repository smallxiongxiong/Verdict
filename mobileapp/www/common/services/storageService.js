/**
 * Created by jiegc on 2/22/17.
 */

(function(){
    'use strict';

    var PREFIX = 'nffApp.';
    var StorageService = function($localStorage,$sessionStorage){

        var STATUS = "status";

        var ONT = "ont";

        var IP = "ip";

        var SN = "sn";

        var VERSION = "version";

        var USERNAME = "username";

        var PASSWORD = "password";

        this.checkONT = function(sn){
            var curSN = this.getSN();
            return (sn==curSN);
        },

        this.setToLocalStorage = function(key, value){
            $localStorage[key] = value
        },

        this.getFromLocalStorage = function(key){
            return $localStorage[key];
        },

        this.set = function(key,value){
            $sessionStorage[key] = value;
        },

        this.get = function(key){
            return $sessionStorage[key];
        }

        this.setObject = function(key,value){
            $sessionStorage[key] = angular.toJson(value);
        }

        this.getObject = function(key){
            return JSON.parse($sessionStorage[key]);
        }

        this.setIPToLocalStorage = function(ip){
            $localStorage[IP] = ip;
        },

        this.getIPFromLocalStorage = function(){
            return $localStorage[IP];
        },

        this.setUserNameToLocalStorage = function(username){
            $localStorage[USERNAME] = username;
        },

        this.getUserNameFromLocalStorage = function(){
            return $localStorage[USERNAME];
        },

        this.setPasswordLocalStorage = function(password){
            $localStorage[PASSWORD] = password;
        },

        this.getPasswordFromLocalStorage = function(){
            return $localStorage[PASSWORD];
        },

        this.setIP = function(ip){
            $sessionStorage[IP] = ip;
        },

        this.getIP = function(){
            return $sessionStorage[IP];
        },

        this.setSN = function(sn){
            $sessionStorage[SN] = sn;
        },

        this.getSN = function(){
            return $sessionStorage[SN];
        },

        this.setStatus = function(status){
            $sessionStorage[STATUS] = status;
        },

        this.getStatus = function(){
            return $sessionStorage[STATUS];
        }

        this.setVersion = function(version){
            $sessionStorage[VERSION] = version;
        },

        this.getVersion = function(){
            return $sessionStorage[VERSION];
        }

        this.saveLoginInfo = function(ip,username,password){
            this.setIPToLocalStorage(ip);
            this.setUserNameToLocalStorage(username);
            this.setPasswordLocalStorage(password);
        }

        this.login = function(ip,username,password,sn,status,version){
            this.saveLoginInfo(ip,username,password);
            this.setIP(ip);
            this.setSN(sn);
            this.setStatus(status);
            this.setVersion(version);
        },

        this.logout = function(){
            delete $sessionStorage[IP];
            delete $sessionStorage[SN];
            delete $sessionStorage[ONT];
            delete $sessionStorage[STATUS];
            delete $sessionStorage[VERSION];
        }
    }

    angular.module('services.storageService',['ngStorage'])
        .config(['$localStorageProvider','$sessionStorageProvider',
            function($localStorageProvider,$sessionStorageProvider){
                $localStorageProvider.setKeyPrefix(PREFIX);
                $sessionStorageProvider.setKeyPrefix(PREFIX);
            }])
        .service('storageService',['$localStorage','$sessionStorage',StorageService]);
})();