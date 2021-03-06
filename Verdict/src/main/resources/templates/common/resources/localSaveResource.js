/**
 * Created by Chen Zhen on 12/16/16.
 */

(function(){
  'use strict';

  var LocalSaveResource  = function($q,$timeout,$resource){

    var resource;
    resource = $resource('log/sav',
      {
      },
      {
        sav: {
          method : 'post',
          isArray: false
        }
      }
    );
    return resource;
  };

  var LocalSaveTableResource  = function($q,$timeout,$resource){

    var resource;
    resource = $resource('log/savTable',
      {
      },
      {
        sav: {
          method : 'post',
          isArray: false
        }
      }
    );
    return resource;
  };


  var OntInfoLogSaveResource = function($q,$timeout,$resource){
    var OntInfoLogSaveResource = $resource('log/onuInfo/savTable',
        {
        },
        {
          sav:{
            method : 'post',
            isArray : false
          }
        }
    );
    return OntInfoLogSaveResource;
  };

  var saveResource = angular.module('resources.localSaveResource', []);

  saveResource
    .factory('localSaveResource',  ['$q','$timeout','$resource',LocalSaveResource])
    .factory('OntInfoLogSaveResource', ['$q','$timeout','$resource',OntInfoLogSaveResource])
    .factory('localSaveTableResource',  ['$q','$timeout','$resource',LocalSaveTableResource]);

})();
