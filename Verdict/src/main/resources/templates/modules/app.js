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
    'services.messageService',
    'services.logoutService'
  ]);

  angular.module('nff.filters', [
    'filter.trustHtmlService',
    'filter.trustUrlService',
    'filter.convertToHtmlElemService',
    'filter.convertToDateService',
    'filter.nffUpgtadeService'
  ]);

  angular.module('nff.directives', [
    'directive.menuTree',
    'directive.nffHeader',
    'directive.nffFooter',
    'directive.nffMessage',
    'directive.nffAlert',
    'directive.nffUpgrade',
	'directive.nffAuthentication',
    'directive.diagnosisTable',
    'directive.commandResult',
    'directive.commandPage'
  ]);

  angular.module('nff.modules', [
    'modules.connector',

    'module.diagnosis.offline',
    'module.diagnosis.online',

    'module.commands.batch',
    'module.commands.interfaces',
    'module.commands.lightPower',
    'module.commands.process',
    'module.commands.image',
    'module.commands.ri',
    'module.commands.timezone',
    'module.commands.version',
    'module.commands.customCommand',

    'module.system.debug',
    'module.system.reboot',
    'module.system.recovery',
    'module.system.upgrade',
    'module.system.upload',
    'module.system.operator',

    'modules.home'

  ]);

  angular.module('nff.resources', [
    'resources.localSaveResource',
    'resources.ontResource',
    'resources.brandResource',
    'resources.ontInstallResource',
    'resources.diagnosisResource',
    'resources.commandsResource',
    'resources.managementResource',
    'resources.userResource'
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
       //'ui.bootstrap',
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
      //$urlRouterProvider.otherwise('/home');
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
    }]);



  //.controller('AppCtrl',['$scope','$translate',
  //  function ($scope, $translate){
  //    $translate.use(localStorage.NG_TRANSLATE_LANG_KEY);
  //    $scope.mainTitle = "NFF";
  //  }
  //]);


})
();
