/**
 * Created by Chen Zhen on 12/16/16.
 */

(function(){
  'use strict';

  var OntInstallResource  = function($q,$timeout,$resource){

    var resource;
    resource = $resource('http://47.96.17.143/brands',
      {
        st : '@st',
        pageNo : '@pageNo',
        searchKey : '@searchKey'
      },
      {
        query: {
          method : 'get',
          isArray: true
        }
      }
    );
    return resource;
  };


  var installResource = angular.module('resources.ontInstallResource', []);

  installResource
    .factory('ontInstallResource',  ['$q','$timeout','$resource',OntInstallResource]);

})();
