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
    $stateProvider.state('home.lightPower', {
      url: '/lightPowerCommand',
      views: {
        'ontView': {
          controller: 'LightPowerCtrl',
          templateUrl: 'modules/commands/power/power.html'
        }
      }
    });
  }

  function LightPowerCtrl() {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }

  angular.module('module.commands.lightPower',[])
    .config(['$stateProvider',routeConfig])
    .controller('LightPowerCtrl', LightPowerCtrl);


})();
