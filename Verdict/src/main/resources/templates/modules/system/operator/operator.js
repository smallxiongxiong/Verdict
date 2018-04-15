(function() {
    'use strict';

    function routeConfig($stateProvider) {
        $stateProvider.state('home.operator', {
            url: '/operator',
            views: {
                'ontView': {
                    controller: 'OperatorCtrl',
                    templateUrl: 'modules/system/operator/operator.html'
                }
            }
        });
    }

    function OperatorCtrl($q, $scope,$http,$filter,$rootScope,$translate,AuthenticationOPERATORIDResource,OntInfoResource,storageService,blockUI,toaster) {
        var wholeBlock = blockUI.instances.get('wholeBlock');
        var onuzPath="assets/data/onuz.json";
	        $rootScope.properld=$scope.properld;//运营商
		    $rootScope.preUrlsld=$scope.preUrlsld;//省
        
        $scope.ontInfoQuery = function () {
            wholeBlock.start();
            OntInfoResource.query({
              ip : storageService.getIP()
            }, function (data) {
                wholeBlock.stop();
                var ontInfo = data;
                ontInfo.Version = storageService.getVersion();
                $scope.ontInfo = ontInfo;
            },function (err) {
                console.log(err);
                wholeBlock.stop();
            });
        };

        $scope.ontInfoQuery();
       
        $http.get(onuzPath).success(function(responce)
        {
    	
	        $scope.myProName=responce.proper;
	        $scope.ProDatas = $scope.myProName; 
	        $scope.hideName=true;
	        $scope.properld=$rootScope.properld;

	        $scope.getProper=function(proName){
                $scope.properld = $filter('nffUpgtade')(proName);
                $rootScope.properld=$scope.properld;
                $scope.hideName=true;
	        } 

	        $scope.changeProper=function(v){
                var newDate=[];  
                angular.forEach($scope.myProName ,function(data,index,array){
                    if(data.indexOf(v)>=0){
                        newDate.unshift(data);
                    }
                });

                $scope.myProName=newDate;
                $scope.hideName=false; 
                if($scope.myProName.length==0 || ''==v){
                    $scope.myProName=$scope.ProDatas;

                }
	        }
		         $scope.myUrlName=responce.preUrls;
		         $scope.perAtas = $scope.myUrlName; 
		         $scope.hideUrl=true;
		         $scope.preUrlsld=$rootScope.preUrlsld;
		         
		    $scope.getPreUrls=function(preName){
                 $scope.preUrlsld = $filter('nffUpgtade')(preName);
                 $rootScope.preUrlsld=$scope.preUrlsld;
                 $scope.hideUrl=true;
	        }
		    
	        $scope.changePreUrls=function(v1){
                var newDate=[];  
                angular.forEach($scope.myUrlName ,function(data,index,array){
                    if(data.indexOf(v1)>=0){
                            newDate.unshift(data);
                    }
                });
                $scope.myUrlName=newDate;
                $scope.hideUrl=false;
                if($scope.myUrlName.length==0 || ''==v1){
                        $scope.myUrlName=$scope.perAtas;

                }
	        }
	        var preId=responce.preId;
	        
	        $scope.updateOerator=function()
	        {   
	            var closeId=responce.operident;
	            var proPreClose=$rootScope.properld+$rootScope.preUrlsld;
	            var parameters = {};
	            parameters['ip'] = storageService.getIP();
	            
	            for(var pId in closeId){
	            	if(closeId[pId].indexOf(proPreClose)!=-1?true:false)
	            	{
		            	$scope.ontInfo.OperatorID=$filter('nffUpgtade')(closeId[pId]);
		            	
		                parameters['Value'] = $scope.ontInfo.OperatorID;
		                
	                    AuthenticationOPERATORIDResource.testOperatorid(parameters,function(data){
                            
	                    	if(data && data.Status && data.Status == "success"){
	                            toaster.pop({ type: 'success', body: $translate.instant('module.msg.testSuccessful')});
		                    }else{
		                        toaster.pop({ type: 'error', body: $translate.instant('module.msg.testFail')});
		                    }
	                    });

	            	}
	            }
            }
        });
        
    }

    angular.module('module.system.operator', [])
        .config(['$stateProvider',routeConfig])
        .controller('OperatorCtrl', ['$q', '$scope','$http','$filter','$rootScope','$translate','AuthenticationOPERATORIDResource',
        'OntInfoResource','storageService','blockUI','toaster',OperatorCtrl]);


        
})();
