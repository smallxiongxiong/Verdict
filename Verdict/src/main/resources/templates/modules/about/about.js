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
    $stateProvider.state('about', {
      url: '/about',
      views: {
        '@': {
          controller: 'AboutCtrl',
          templateUrl: 'modules/about/about.html'
        }
      }
    });
  }

  function AboutCtrl() {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }

  angular.module('module.about',[])
    .config(['$stateProvider',routeConfig])
    .controller('AboutCtrl', AboutCtrl);


})();
