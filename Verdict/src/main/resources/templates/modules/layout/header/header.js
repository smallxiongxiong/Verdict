(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name webappApp.controller:AboutCtrl
   * @description
   * # AboutCtrl
   * Controller of the nff
   */


  function stateConfig($stateProvider) {
    $stateProvider.state('home', {
      url: '/header',
      views: {
        'header@': {
          controller: 'HeaderCtrl',
          templateUrl: 'modules/layout/header/header.html'
        }
      }
    });
  }

  function HeaderCtrl() {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }

  angular.module('modules.layout.header',[])
    //.config(stateConfig)
    .controller('HeaderCtrl', HeaderCtrl);


})();
