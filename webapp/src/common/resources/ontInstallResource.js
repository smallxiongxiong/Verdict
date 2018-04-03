/**
 * Created by Chen Zhen on 12/16/16.
 */

(function(){
  'use strict';

  var OntInstallResource  = function($q,$timeout,$resource){

    var resource;
    resource = $resource('ont/install',
      {
      },
      {
        install: {
          method : 'post',
          isArray: false
        }
      }
    );
    return resource;
  };


  var installResource = angular.module('resources.ontInstallResource', []);

  installResource
    .factory('ontInstallResource',  ['$q','$timeout','$resource',OntInstallResource]);

})();
