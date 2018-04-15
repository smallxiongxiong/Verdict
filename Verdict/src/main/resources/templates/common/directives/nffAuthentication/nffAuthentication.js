/**
 * Created by jiegc on 1/25/17.
 */

(function() {
    'use strict';

    var nffAuthenticationController = function($q,$scope,$translate,LocalIpResource,AuthenticationLOIDResource,AuthenticationPASSWORDResource, storageService,toaster) {

        $scope.getParams = function() {
                $scope.params = ['LOID', 'PASSWORD'];
        };
        
        
        $scope.startTestSelectedItems = function() {
            if($scope.inputValueForItem==undefined){
                toaster.pop({ type: 'error', body: $translate.instant('module.msg.inputNotnull')});	
            	return $scope.inputValueForItem;
           	}else{
           		var selectedItem = $scope.paramSelected;
	            var parameters = {};
	            var inputValueForItem = '\"' + $scope.inputValueForItem + '\"';
	            
	            parameters['ip'] = storageService.getIP();
           		
	            if ( selectedItem == 'LOID' )
	            {
	                parameters['Value'] = inputValueForItem;
	
	                AuthenticationLOIDResource.testLoid(parameters, function(data) {
	                    if(data && data.Status && data.Status == "success"){
	                        toaster.pop({ type: 'success', body: $translate.instant('module.msg.testSuccessful')});
	                    }else{
	                        toaster.pop({ type: 'error', body: $translate.instant('module.msg.testFail')});
	                    }
	                },function(err){
	                    console.log(err);
	                });
	            }else if ( selectedItem == 'PASSWORD' )
	            {
	                parameters['Value'] = inputValueForItem;
	
	                AuthenticationPASSWORDResource.testPassword(parameters, function(data) {
	                    if(data && data.Status && data.Status == "success"){
	                        toaster.pop({ type: 'success', body: $translate.instant('module.msg.testSuccessful')});
	                    }else{
	                        toaster.pop({ type: 'error', body: $translate.instant('module.msg.testFail')});
	                    }
	                });
	            }else{
	                toaster.pop({ type: 'error', body: $translate.instant('module.msg.selectCertification')});
	            }
	        }
        };
    };

    /* @ngInject */
    function nffAuthentication() {
        var directive = {
            controller : 'nffAuthenticationController',
            replace : true,
            restrict : 'E',
            templateUrl : "common/directives/nffAuthentication/nffAuthentication.html"
        };
        return directive;
    };

    angular.module('directive.nffAuthentication',[])
        .directive('nffAuthentication',nffAuthentication)
        .controller('nffAuthenticationController',['$q','$scope','$translate','LocalIpResource','AuthenticationLOIDResource',
            'AuthenticationPASSWORDResource', 'storageService','toaster',nffAuthenticationController]);
})();