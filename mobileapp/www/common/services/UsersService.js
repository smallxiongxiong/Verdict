(function(){
    'use strict';

    angular
        .module('services.usersService',[])
        .service('usersService', ['$http', 'httpTokenAuthService', '$window',usersService]);

    function usersService($http, httpTokenAuthService, $window){

        var service = {
            getUserPayload:         getUserPayload,
            getUserId:              getUserId,
            getUserData:            getUserData,
            getUserRole:            getUserRole,
            getUsers:               getUsers,
            //uploadImage:            uploadImage,
            changeUserName:         changeUserName,
            changeUserPhoto:        changeUserPhoto,
            changeUserPassword:     changeUserPassword,
            updateUserInfo:         updateUserInfo
        };

        return service;

        function getUserPayload(){
            var token = httpTokenAuthService.getToken();

            if(token){
                var payload = JSON.parse($window.atob(token.split('.')[1]));

                return payload;
            } else {
                return '';
            }
        }

        function getUserData(){
            var id = getUserId();
            return $http.get('http://47.96.17.143/users/' + id).then(function(response){
                return response;
            }, function(err){
                return err;
            });
        }

        function getUserId(){
            return getUserPayload()._id;
        }


        function getUserRole(){
            var accessLevel = getUserPayload().accessLevel;
            var userRoles = ['User', 'Redactor', 'Administrator'];
            return userRoles[accessLevel];
        }


        function getUsers(){
            return $http.get('http://47.96.17.143/users').then(function(response){
                return response;
            }, function(err){
                return err;
            });
        }


        function uploadImage(file) {
            //return Upload.upload({
            //    url: '/upload/user-pic/',
            //    method: 'POST',
            //    file: file
            //}).then(function(response){
            //    return response.data;
            //}, function(err){
            //    return err.data;
            //});
        }

        function changeUserPhoto(user){
            return $http.put('http://47.96.17.143/users/change-image/' + user._id, user).then(function(response){
                return response.data;
            }, function(err){
                return err.data;
            });
        }

        function changeUserName(user){
            return $http.put('http://47.96.17.143/users/change-name/' + user._id, user).then(function(response){
                return response.data;
            }, function(err){
                return err.data;
            });
        }

        function changeUserPassword(user){
            return $http.put('http://47.96.17.143/users/change-pass/' + user._id, user).then(function(response){
                return response.data;
            }, function(err){
                return err.data;
            });
        }

        function updateUserInfo(user){
            return $http.put('http://47.96.17.143/users/update-info/', user).then(function(response){
                return response.data;
            }, function(err){
                return err.data;
            });
        }

    }
})();