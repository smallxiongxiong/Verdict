/**
 * Created by jiegc on 11/17/16.
 */
(function () {
    'use strict';

    /**
     * @ngdoc function
     * @name nff.controller:HomeCtrl
     * @description
     * # HomeCtrl
     * Controller of the nff
     */


    function routeConfig($stateProvider) {
        $stateProvider.state('home.ontInfo', {
                 url: '/ontInfo',
                 views: {
                   'ontView': {
                     controller: 'ontInfoCtrl',
                     templateUrl: 'modules/info/ontInfo.html'
                   }
                 }
        });
    };


    function ontInfoCtrl($q, $scope, $state,OntInfoResource,OntInfoLogSaveResource,storageService,articlesService) {
        articlesService.getAll().then(function(data){
            $scope.articles = data;
        });

        $scope.gotoDetails = function(id) {
            articlesService.getArticle(id).then(function(data){
                $state.go("home.details",{data:data});
            });
        }
    }



    angular.module('modules.ontInfo',[])
        .config(['$stateProvider',routeConfig])
        .controller('ontInfoCtrl', ['$q','$scope','$state','OntInfoResource','OntInfoLogSaveResource','storageService','articlesService',ontInfoCtrl]);
})();
