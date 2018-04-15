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
    $stateProvider.state('bottom', {
      url: '/bottom',
      views: {
        '@': {
          controller: 'BottomCtrl',
          templateUrl: 'modules/layout/bottom/bottom.html'
        }
      }
    });
  }

  function BottomCtrl() {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }

  angular.module('module.layout.bottom',[])
    .config(routeConfig)
    .controller('BottomCtrl', BottomCtrl);


})();
