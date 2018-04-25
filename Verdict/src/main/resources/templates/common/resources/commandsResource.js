(function(){
    'use strict';

    //var ip = "";

    var CommandResource = function ($resource) {

        var resource =  $resource('/dicts/:pageNum/:pageSize',
            {
                pageNum : '@pageNum',
                pageSize : '@pageSize'
            },
            {
                getQuery: {
                    method : 'get',
                    isArray: false
                }
            }
        );
        return resource;
    };

    var DictsResource = function ($resource) {

        var resource =  $resource('/dicts/:pageNum/:pageSize',
            {
                pageNum : '@pageNum',
                pageSize : '@pageSize'
            },
            {
                getQuery: {
                    method : 'get',
                    isArray: true
                }
            }
        );
        return resource;
    };

    var deleteDictsResource = function ($resource) {

        var resource =  $resource('/dict/del/:id',
            {
               id:'@id'
            },
            {
                delDict: {
                    method : 'get'
                }
            }
        );
        return resource;
    };

    var BatchGuiResource = function ($resource) {

        var resource =  $resource('/segment/test',
            {
                source:'@source',
            },
            {
                get: {
                    method : 'get',
                    isArray: true
                }
            }
        );
        return resource;
    };


    var CmdMenusResource = function ($resource) {
        var resource =  $resource('/segment/train',
            {
                words:'@words'
            },
            {
                train: {
                    method : 'post'
                }
            }
        );
        return resource;
    };

    var CustomCommandResource = function ($resource) {

        var resource =  $resource('/dict/add',
            {
            },
            {
                post: {
                    method : 'post',
                    params : {
                        key:'@key',
                        label:'@label',
                        frequency:'@frequency',
                        enable:'@enable'
                    }
                }
            }
        );
        return resource;
    };
    var WenshujiansuoGuiResource = function ($resource) {

        var resource =  $resource('assets/data/wenshujiansuo.json',
            {
                query: {isArray: false}
            }
        );
        return resource;
    };
    var QueryByConditionResource = function ($resource) {
        var resource =  $resource('/es/query',
            {

            },
            {
                getQuery: {
                    method : 'post',
                    isArray: false,
                    params : {
                        conditions:'@conditions'
                    }
                }
            }
        );
        return resource;
    };

    var commandsResource = angular.module('resources.commandsResource', []);
    commandsResource
        .factory('CmdMenusResource', ['$resource',CmdMenusResource])
        .factory('CustomCommandResource', ['$resource',CustomCommandResource])
        .factory('DictsResource', ['$resource',DictsResource])
        .factory('deleteDictsResource', ['$resource',deleteDictsResource])
        .factory('CommandResource', ['$resource',CommandResource])
        .factory('QueryByConditionResource', ['$resource',QueryByConditionResource])
        .factory('BatchGuiResource', ['$resource',BatchGuiResource])
        .factory('WenshujiansuoGuiResource', ['$resource',WenshujiansuoGuiResource]);

})();
