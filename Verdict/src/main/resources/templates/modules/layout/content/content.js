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
    $stateProvider.state('content', {
      url: '/content',
      views: {
        '@': {
          controller: 'ContentCtrl',
          templateUrl: 'modules/layout/content/content.html'
        }
      }
    });
  }

  function ContentCtrl() {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }

  angular.module('module.layout.content',[])
    .config(routeConfig)
    .controller('ContentCtrl', ContentCtrl);


})();
