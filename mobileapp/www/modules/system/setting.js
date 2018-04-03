
(function () {
    'use strict';

    /**
     * @ngdoc function
     * @name nff.controller:HomeCtrl
     * @description
     * # HomeCtrl
     * Controller of the nff
     */


    function routeConfig($stateProvider) {
        $stateProvider.state('home.setting', {
            url: '/setting',
            views: {
                'ontView': {
                    controller: 'settingCtrl',
                    templateUrl: 'modules/system/setting.html'
                }
            }
        });
    };


    function settingCtrl($q, $scope,storageService,LogoutService) {
        //storageService.set("storeItems",[]);
        $scope.logout = function(){
            //ionic.Platform.ready(function(){
            //    // will execute when device is ready, or immediately if the device is already ready.
            //});

            navigator.app.exitApp();




            //var deviceInformation = ionic.Platform.device();
            //
            //var isWebView = ionic.Platform.isWebView();
            //var isIPad = ionic.Platform.isIPad();
            //var isIOS = ionic.Platform.isIOS();
            //var isAndroid = ionic.Platform.isAndroid();
            //var isWindowsPhone = ionic.Platform.isWindowsPhone();
            //
            //var currentPlatform = ionic.Platform.platform();
            //var currentPlatformVersion = ionic.Platform.version();
            //
            //ionic.Platform.exitApp(); // stops the app
            //LogoutService.logout();
        };

    }



    angular.module('module.setting',[])
        .config(['$stateProvider',routeConfig])
        .controller('settingCtrl', settingCtrl);
})();
