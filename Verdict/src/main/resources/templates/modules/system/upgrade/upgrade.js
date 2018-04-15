(function () {
  'use strict';

  /**
   * @ngdoc function
   * @name upgrade.js
   * @description
   * # UpgradeCtrl
   * Controller of the nff
   *
   * add by jiegc 1/9/2017
   */


  function routeConfig($stateProvider) {
    $stateProvider.state('home.upgrade', {
      url: '/upgrade',
      views: {
        'ontView': {
          templateUrl: 'modules/system/upgrade/upgrade.html'
        }
      }
    });
  }

  angular.module('module.system.upgrade',[])
    .config(['$stateProvider',routeConfig]);

})();
