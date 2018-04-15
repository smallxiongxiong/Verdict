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
    $stateProvider.state('home.process', {
      url: '/processCommand',
      views: {
        'ontView': {
          controller: 'ProcessCtrl',
          templateUrl: 'modules/commands/process/process.html'
        }
      }
    });
  }

  function ProcessCtrl() {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }

  angular.module('module.commands.process',[])
    .config(['$stateProvider',routeConfig])
    .controller('ProcessCtrl', ProcessCtrl);


})();
