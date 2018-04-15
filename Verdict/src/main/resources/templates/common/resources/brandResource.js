//
// (function(){
//   'use strict';
//
//   var ip = "";
//
//
//   var BrandResource  = function($q,$timeout,$resource){
//       var Brands=$resource('http://:localhost:4000/brands',{});
//       return Brands;
//   };
//
//     var searchResource = function ($resource) {
//
//         var resource =  $resource('http://:ip:8043/onu/diagnosis/wifi_key',
//             {
//                 ip : '@ip'
//             },
//             {
//                 query: {isArray: false}
//             }
//         );
//         return resource;
//     };
//   // var brandResource = angular.module('resources.brandResource', []);
//
//     angular.module('resources.brandResource', [])
//         .factory('searchResource', ['$resource',searchResource])
//         .factory('brandResource', ['$q','$timeout','$resource',BrandResource]);
//
// })();


(function(){
    'use strict';

    var BrandGetResource  = function($q,$timeout,$resource){

        var resource;
        resource = $resource('http://localhost:4000/brands',
            {
            },
            {
                install: {
                    method : 'get',
                    isArray: false
                }
            }
        );
        return resource;
    };

    angular.module('resources.brandResource', [])
        .factory('brandGetResource',  ['$q','$timeout','$resource',BrandGetResource]);

})();