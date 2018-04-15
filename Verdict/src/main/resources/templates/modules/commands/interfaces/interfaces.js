(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name webappApp.controller:InterfacesCtrl
   * @description
   * # InterfacesCtrl
   * Controller of the nff
   */


  function routeConfig($stateProvider) {
    $stateProvider.state('home.interfaces', {
      url: '/interfacesCommand',
      views: {
        'ontView': {
          controller: 'InterfacesCtrl',
          templateUrl: 'modules/commands/interfaces/interfaces.html'
        }
      }
    });
  }

  function InterfacesCtrl() {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }

  angular.module('module.commands.interfaces',[])
    .config(['$stateProvider',routeConfig])
    .controller('InterfacesCtrl', InterfacesCtrl);


})();
