/**
 * Created by Chen Zhen on 11/17/16.
 */

(function(){
  'use strict';

  var ip = "";

  //var OntResource  = function($q,$timeout,$resource){
  //
  //  var resource = $resource('assets/data/onu.json',{} ,
  //    {
  //      query: {isArray: false}
  //    }
  //  );
  //  return resource;
  //};

  var OntResource  = function($q,$timeout,$resource){

    var resource = $resource('http://:ip:8043/onu',
    //var resource = $resource('http://:url',
      {
        ip : '@ip'
      } ,
      {
        query: {
          method : 'GET',
          isArray: false,
          timeout: 5000
        }
      }
    );
    return resource;
  };

  //var OntInfoResource = function($q,$timeout,$resource){
  //  var OntInfoResource = $resource('assets/data/onu/information.json',{},
  //    {
  //      query: {isArray: false}
  //    }
  //  );
  //  return OntInfoResource;
  //};

  var OntInfoResource = function($q,$timeout,$resource){
    var OntInfoResource = $resource('http://:ip:8043/onu/information',
      {
        ip : '@ip'
      },
      {
        query: {isArray: false}
      }
    );
    return OntInfoResource;
  };

  var ontResource = angular.module('resources.ontResource', []);

  ontResource
    .factory('ontResource', ['$q','$timeout','$resource',OntResource])
    .factory('OntInfoResource', ['$q','$timeout','$resource',OntInfoResource]);

  //var ontResProvider = function($injector){
  //
  //  var baseURL;
  //  var rScope = $injector.get('$rootScope');
  //  rScope.$on('ontChange',function(data){
  //    baseURL = data;
  //    alert(data);
  //  });
  //
  //  var res = {
  //
  //    setURL : function(url){
  //      baseURL = url;
  //    },
  //
  //    $get : OntResource
  //  }
  //
  //  return res;
  //};
  //
  //ontResource
  //  .provider('ontRes',ontResProvider);

})();
