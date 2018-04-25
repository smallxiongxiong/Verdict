/**
 * Created by Chen Jie on 12/8/16.
 */

(function(){
  'use strict';

  //var ip = "";

  var LineResource = function ($resource,$rootScope) {

    var resource =  $resource('http://:ip:8043/onu/diagnosis/:type/:item',
        {
            ip : '@ip',
            type : '@type',
            item : '@item'
        },
        {
          get: {
              timeout: $rootScope.timeout
          }
        }
    );
    return resource;
  };


    var WifiUserPwdResource = function ($resource) {

        var resource =  $resource('http://:ip:8043/onu/diagnosis/wifi_key',
            {
                ip : '@ip'
            },
            {
                query: {isArray: false}
            }
        );
        return resource;
    };

    var AuthenticationLOIDResource = function ($resource) {

        var resource =  $resource('http://:ip:8043/onu/diagnosis/loid',
            {
                ip : '@ip'
            },
            {
                testLoid: {
                    method : 'put',
                    params : {
                        Value:'@Value'
                    }

                }
            }
        );
        return resource;
    };
    var AuthenticationOPERATORIDResource=function($resource){
    	var resource=$resource('http://:ip:8043/onu/diagnosis/operatorid',
	    	{
	    		ip : '@ip'
	    	},
	    	{
	    		 testOperatorid: {
                    method : 'put',
                    params : {
                        Value:'@Value'
                    }

                }
	    		
	    	}
        );
        return resource;
    };
    var AuthenticationPASSWORDResource = function ($resource) {

        var resource =  $resource('http://:ip:8043/onu/diagnosis/passwd',
            {
                ip : '@ip'
            },
            {
                testPassword: {
                    method : 'put',
                    params : {
                        Value:'@Value'
                    }
                }
            }
        );
        return resource;
    };

    var OffLineGuiResource = function ($resource) {

        var resource =  $resource('assets/data/onu/diagnosis/offline/gui.json',
            {
                query: {isArray: false}
            }
        );
        return resource;
    };

    var OnlineGuiResource = function ($resource) {

        var resource =  $resource('assets/data/onu/diagnosis/online/gui.json',
            {
                query: {isArray: false}
            }
        );
        return resource;
    };


    var JudgementResource = function ($resource) {

        var resource =  $resource('/judgements/:pageNum/:pageSize',
            {
                pageNum : '@pageNum',
                pageSize:'@pageSize'
            },
            {
                getAll: {isArray: true}
            }
        );
        return resource;
    };

    var JudgementResource1 = function ($resource) {

        var resource =  $resource('/query/:idType/:idValue',
            {
                idType:'@idType',
                idValue : '@idValue'
            },
            {
                getMixResults: {isArray: true}
            }
        );
        return resource;
    };
    var JudgementResource2 = function ($resource) {

        var resource =  $resource('/article/:docId',
            {
                pageNum : '@docId'
            },
            {
                getArticle: {isArray: false}
            }
        );
        return resource;
    };

  var diagnosisResource = angular.module('resources.diagnosisResource', []);
  diagnosisResource
      .factory('JudgementResource', ['$resource','$rootScope',JudgementResource])
      .factory('JudgementResource1', ['$resource','$rootScope',JudgementResource1])
      .factory('JudgementResource2', ['$resource','$rootScope',JudgementResource2])
      .factory('LineResource', ['$resource','$rootScope',LineResource])
      .factory('AuthenticationLOIDResource', ['$resource',AuthenticationLOIDResource])
      .factory('AuthenticationOPERATORIDResource', ['$resource',AuthenticationOPERATORIDResource])
      .factory('AuthenticationPASSWORDResource', ['$resource',AuthenticationPASSWORDResource])
      .factory('OffLineGuiResource', ['$resource',OffLineGuiResource])
      .factory('WifiUserPwdResource', ['$resource',WifiUserPwdResource])
      .factory('OnlineGuiResource', ['$resource',OnlineGuiResource]);

})();
