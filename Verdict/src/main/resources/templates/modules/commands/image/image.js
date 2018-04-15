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
    $stateProvider.state('home.imageInfo', {
      url: '/imageCommand',
      views: {
        'ontView': {
          controller: 'ImageCtrl',
          templateUrl: 'modules/commands/image/image.html'
        }
      }
    });
  }

  function ImageCtrl() {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  }

  angular.module('module.commands.image',[])
    .config(['$stateProvider',routeConfig])
    .controller('ImageCtrl', ImageCtrl);


})();
