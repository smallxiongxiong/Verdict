/**
 * Created by jiegc on 11/17/16.
 */
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
        $stateProvider.state('home.ontInfo', {
                 url: '/ontInfo',
                 views: {
                   'ontView': {
                     controller: 'ontInfoCtrl',
                     templateUrl: 'modules/info/ontInfo.html'
                   }
                 }
        });
    };


    function ontInfoCtrl($q, $scope, $rootScope,OntInfoResource,OntInfoLogSaveResource,storageService,blockUI) {
        var wholeBlock = blockUI.instances.get('wholeBlock');
        var partBlock = blockUI.instances.get('partBlock');
        
        

        if(partBlock.state().blocking) {
            partBlock.stop();
        }
        $scope.ontInfoQuery = function () {
            wholeBlock.start();
            OntInfoResource.query({
              ip : storageService.getIP()
            }, function (data) {
                wholeBlock.stop();
                var ontInfo = data;
                var interfaceObj = ontInfo.Interface;
                var imageValue = ontInfo.Interface.Image;
                var interfaceStr = "";
                for(var key in interfaceObj) {
                	if(key!="Image"){
                    	if(interfaceStr === "") {
                            interfaceStr = interfaceStr + key + "=" + interfaceObj[key];
	                    } else {
	                        interfaceStr = interfaceStr + "," + key + "=" + interfaceObj[key];
	                    }
                    }
                }
                var SLID = ontInfo.SLID;
                if(SLID == '44454641554C54'){
                    ontInfo.SLID = "";
                }
                
                var imagStr="";
        		imagStr=imageValue;
                ontInfo.ImageStr=imagStr;
                ontInfo.InterfaceStr = interfaceStr;
                ontInfo.Version = storageService.getVersion();
                $scope.ontInfo = ontInfo;

                $scope.$emit('getCmdMenu', $scope.ontInfo.Type.trim());

                if(storageService.get("ont") === undefined) {
                    console.log("first login");
                    var saveOnuInfoArr = [];
                    saveOnuInfoArr.push("GPONSN,Type,Code,Software,Hardware,LOID,Password,SLID,MAC,Serial,OperatorID,Date,Interface,Image"+"\n");
                    saveOnuInfoArr = saveOnuInfoArr.concat(ontInfo);
                    var params = {};
                    params['SN'] = ontInfo['GPONSN'];
                    params['category'] = 'ontInfo';
                    params['content'] = saveOnuInfoArr;
                    OntInfoLogSaveResource.sav(params,function(res) {
                        console.log("save success");
                    });
                }

                storageService.setObject("ont",ontInfo);
            },function (err) {
                console.log(err);
                wholeBlock.stop();
            });
        };

        $scope.ontInfoQuery();
    }



    angular.module('modules.ontInfo',[])
        .config(['$stateProvider',routeConfig])
        .controller('ontInfoCtrl', ['$q','$scope','$rootScope','OntInfoResource','OntInfoLogSaveResource','storageService','blockUI',ontInfoCtrl]);
})();
