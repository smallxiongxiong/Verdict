(function() {
    'use strict';

    /**
     * @ngdoc function
     * @name webappApp.controller:AboutCtrl
     * @description
     * # AboutCtrl
     * Controller of the nff
     */


    function routeConfig($stateProvider) {
        $stateProvider.state('home.versionInfo', {
            url: '/versionCommand',
            views: {
                'ontView': {
                    controller: 'VersionCtrl',
                    templateUrl: 'modules/commands/version/version.html'
                }
            }
        });
    }

    function VersionCtrl() {}

    angular.module('module.commands.version', [])
        .config(['$stateProvider',routeConfig])
        .controller('VersionCtrl', VersionCtrl);


})();
