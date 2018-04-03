(function () {


  'use strict';


  angular.module('nff.services', [
    'services.storageService',
    'services.messageService',
    'services.logoutService',
    'services.telnetService',
    'services.ontService',
    'services.httpUserService'
  ]);

  angular.module('nff.filters', [
    'filter.trustHtmlService',
    'filter.trustUrlService',
    'filter.convertToHtmlElemService',
    'filter.convertToDateService'
  ]);

  angular.module('nff.directives', [
    'directive.nffHeader',
    'directive.nffFooter',
    'directive.nffMessage'
  ]);

  angular.module('nff.modules', [
    'modules.connector',
    'modules.home'

  ]);

  angular.module('nff.resources', [
    'resources.localSaveResource',
    'resources.ontResource',
    'resources.ontInstallResource',
    'resources.diagnosisResource',
    'resources.commandsResource',
    'resources.managementResource',
    'resources.ontVersionsResource'
  ]);

  angular
    .module('nff', [
      'ngAnimate',
      'ngCookies',
      'ngResource',
      'ngRoute',
      'ngSanitize',
      'ngTouch',
      'ui.router',
      'shagstrom.angular-split-pane',
      'angularBootstrapNavTree',
      'pascalprecht.translate',
      'LocalStorageModule',

      'ngMaterial',
      'blockUI',
      'toaster',
      'ngStorage',
      //'ams-footer',

      'nff.resources',
      'nff.directives',
      'nff.services',
      'nff.filters',
      'nff.modules'

    ])

    .run(['$rootScope','ConfResource',function($rootScope,ConfResource) {
        ConfResource.get({},function(res){
          $rootScope.timeout = res.conf.timeout;
          console.log("$rootScope.timeout: " + $rootScope.timeout);
        })
    }])

    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.defaults.timeout = 1000;
      }])

    .config(['blockUIConfig', function(blockUIConfig) {
        blockUIConfig.delay=  0;
        blockUIConfig.templateUrl = 'modules/component/nffLoading/nffLoading.html';
        blockUIConfig.autoInjectBodyBlock = false;
      }])

    .config(['$urlRouterProvider', function ($urlRouterProvider) {
      $urlRouterProvider.otherwise('/home');
        //$urlRouterProvider.otherwise('/');
    }])
    .config(['$locationProvider', function ($locationProvider) {
      $locationProvider.html5Mode(true);
    }])


    .config(['$translateProvider',function($translateProvider){
      $translateProvider.useStaticFilesLoader({
        prefix: 'assets/locales/',
        suffix: '.json'
      }).preferredLanguage('chinese');
    }]);



  //.controller('AppCtrl',['$scope','$translate',
  //  function ($scope, $translate){
  //    $translate.use(localStorage.NG_TRANSLATE_LANG_KEY);
  //    $scope.mainTitle = "NFF";
  //  }
  //]);


})
();
