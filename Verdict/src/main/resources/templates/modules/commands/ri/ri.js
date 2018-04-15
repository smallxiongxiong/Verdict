(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name webappApp.controller:AboutCtrl
   * @description
   * # AboutCtrl
   * Controller of the nff
   */


  function routeConfig($stateProvider) {
    $stateProvider.state('home.riInfo', {
      url: '/riInfoCommand',
      views: {
        'ontView': {
          controller: 'RiInfoCtrl',
          templateUrl: 'modules/commands/ri/ri.html'
        }
      }
    });
  }

  function RiInfoCtrl() {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }

  angular.module('module.commands.ri',[])
    .config(['$stateProvider',routeConfig])
    .controller('RiInfoCtrl', RiInfoCtrl);


})();
