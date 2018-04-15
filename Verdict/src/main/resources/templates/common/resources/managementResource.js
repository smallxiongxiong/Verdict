(function() {
    'use strict';


    var ManagementResource = function($resource) {

        var resource = $resource('http://:ip:8043/onu/management/:item', {
            ip: '@ip',
            item: '@item'
        }, {
            query: {
                isArray: false
            }
        });
        return resource;
    };


    var LocalIpResource = function($resource) {
        var resource = $resource('/local/ip',
            {
            },
            {
                localIp: {
                    method : 'get',
                    isArray: false
                }
            }
        );
        return resource;
    }

    var ImageFilesResource = function($resource) {
        var resource = $resource('/image/upgrade',
            {
            },
            {
                imageFiles: {
                    method : 'get',
                    isArray: false
                }
            }
        );
        return resource;
    }

    var ConfResource = function($resource) {
        var resource =  $resource('/local/conf',
            {
                get: {
                    method : 'get'
                }
            }
        );
        return resource;
    }

    var managementResource = angular.module('resources.managementResource', []);

    managementResource
        .factory('ManagementResource', ['$resource',ManagementResource])
        .factory('LocalIpResource', ['$resource',LocalIpResource])
        .factory('ConfResource', ['$resource',ConfResource])
        .factory('ImageFilesResource', ['$resource',ImageFilesResource]);
})();
