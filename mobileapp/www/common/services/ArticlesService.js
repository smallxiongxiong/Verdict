(function(){
    'use strict';

    angular
        .module('services.articlesService',[])
        .service('articlesService', ['$http', 'Upload',ArticlesService]);

    function ArticlesService($http, Upload){

        var service = {
            getAll: getAll,
            getArticle: getArticle,
            addArticle: addArticle,
            uploadForFile: uploadForFile,
            updateArticle: updateArticle,
            deleteArticle: deleteArticle,
            filterByCategory: filterByCategory
        };

        return service;

        function uploadForFile(file) {
            return Upload.upload({
                url: '/uploadfile',
                method: 'POST',
                file: file
            }).then(function (response) {
                return response.data;
            });
        }

        function getAll(){
            return $http.get('http://47.96.17.143/articles').then(function(response){
                return response.data;
            });
        }

        function getArticle(id){
            return $http.get('http://47.96.17.143/articles/' + id).then(function(response){
               return response.data;
            });
        }

        function addArticle(article){
            return $http.post('http://47.96.17.143/articles/', article).then(function(response){
               return response.data;
            });
        }

        function uploadImage(file) {
            return Upload.upload({
                url: 'http://47.96.17.143/upload',
                method: 'POST',
                file: file
            }).then(function (response) {
                return response.data;
            });
        }

        function updateArticle(article){
            return $http.put('http://47.96.17.143/articles/', article).then(function(response){
                return response.data;
            });
        }

        function deleteArticle(id){
            return $http.delete('http://47.96.17.143/articles/' + id).then(function(response){
                return response.data;
            });
        }

        function filterByCategory(category){
            return $http.get('http://47.96.17.143/category-articles/' + category).then(function(response){
                return response.data;
            });
        }
    }
})();