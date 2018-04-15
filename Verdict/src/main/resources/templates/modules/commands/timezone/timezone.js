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
    $stateProvider.state('home.timeZone', {
      url: '/timezoneCommand',
      views: {
        'ontView': {
          controller: 'TimezoneCtrl',
          templateUrl: 'modules/commands/timezone/timezone.html'
        }
      }
    });
  }

  function TimezoneCtrl() {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }

  angular.module('module.commands.timezone',[])
    .config(['$stateProvider',routeConfig])
    .controller('TimezoneCtrl', TimezoneCtrl);


})();
