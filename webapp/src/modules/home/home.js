(function () {
  'use strict';



  function routeConfig($stateProvider) {
    $stateProvider.state('home', {
      url: '/home',
      views: {
        //'menutree': {
        //  controller: 'MenuTreeCtrl',
        //  templateUrl: 'modules/layout/menutree/menutree.html'
        //},
        '': {
          controller: 'HomeCtrl',
          templateUrl: 'modules/home/home.html'
        }
      }
    });
  }


  function HomeCtrl(ontResource) {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
    var res = ontResource;

    //MessageService.setMessage('登录成功！', 'success');

  }


  angular.module('modules.home',[
    'modules.ontInfo'
  ])
    .config(['$stateProvider',routeConfig])
    .controller('HomeCtrl', ['ontResource',HomeCtrl]);

})();
