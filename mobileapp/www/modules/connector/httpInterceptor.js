/**
 * Created by jiegc on 1/23/17.
 */
(function(){
    var httpInterceptor = function ($provide, $httpProvider) {
        $provide.factory('httpInterceptor', function ($q, $window,LogoutService,storageService) {
            return {
                response: function (response) {
                    console.log("response");
                    var reqUrl = response.config.url.toLowerCase();
                    var urlStr = "http://" + storageService.getIP() + ":";
                    if(reqUrl.indexOf(urlStr) != -1 && reqUrl.indexOf("onu") != -1 && !response.config.ignoreInterceptor){
                        var sn = storageService.getSN();
                        if(sn != response.data.SN){
                            LogoutService.logout();
                        }
                    }
                    return response || $q.when(response);
                },
                responseError: function (rejection) {
                    console.log("responseError");
                    if(rejection.status === 0 && !rejection.config.ignoreInterceptor) {
                        LogoutService.logout();
                    }
                    return $q.reject(rejection);
                }
            };
        });
        $httpProvider.interceptors.push('httpInterceptor');
    };
    angular.module("nff").config(['$provide','$httpProvider',httpInterceptor]);
}());
