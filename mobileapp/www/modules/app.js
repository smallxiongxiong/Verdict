(function () {


  'use strict';

  /**
   * @ngdoc overview
   * @name Lens
   * @description
   * # nffApp
   *
   * Main module of the application.
   */

  angular.module('nff.services', [
    'services.storageService',
    
    'services.logoutService',
    'services.authService',
    'services.usersService',
    'services.cartService',
    'services.httpTokenAuthService',
    'services.articlesService',

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
    'directive.diagnosisTable'
  ]);

  angular.module('nff.modules', [

    'modules.connector',
    'module.diagnosis.offline',
    'module.system.reboot',
    'module.system.recovery',
    'module.setting',
    'modules.home',
    'modules.details'


  ]);

  angular.module('nff.resources', [
    'resources.localSaveResource',
    'resources.ontResource',
    'resources.ontInstallResource',
    'resources.diagnosisResource',
    'resources.managementResource',
    'resources.ontVersionsResource'
  ]);

  angular
    .module('nff', [
      'ngAnimate',
      'ngResource',
      'ngRoute',
      'ngSanitize',
      'ngTouch',
      'ui.router',
      'pascalprecht.translate',
      'LocalStorageModule',
      'ngMaterial',
      'blockUI',
      'toaster',
      'ngStorage',
      'duScroll',
       'ngFileUpload',
        'ngCookies',
      'nff.modules',
      'nff.resources',
      'nff.directives',
      'nff.services',
      'nff.filters',
          'ionic'
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
        //blockUIConfig.delay=  0;
        //blockUIConfig.templateUrl = 'modules/component/nffLoading/nffLoading.html';
        //blockUIConfig.autoInjectBodyBlock = false;
        blockUIConfig.autoBlock = false;
      }])

    .config(['$urlRouterProvider', function ($urlRouterProvider) {
        $urlRouterProvider.otherwise('/');
    }])
    .config(['$locationProvider', function ($locationProvider) {
      $locationProvider.html5Mode(false);
    }])


    .config(['$translateProvider',function($translateProvider){
      $translateProvider.useStaticFilesLoader({
        prefix: 'assets/locales/',
        suffix: '.json'
      }).preferredLanguage('chinese');
    }])

  .config(['$ionicConfigProvider', function($ionicConfigProvider) {

    $ionicConfigProvider.tabs.position('bottom'); // other values: top

  }]);



})
();
