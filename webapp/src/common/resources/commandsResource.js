/**
 * Created by Chen Jie on 1/1/17.
 */

(function(){
    'use strict';

    //var ip = "";

    var CommandResource = function ($resource) {

        var resource =  $resource('http://:ip:8043/onu/command/:item',
            {
                ip : '@ip',
                item : '@item'
            },
            {
                query: {isArray: false}
            }
        );
        return resource;
    };

    var BatchGuiResource = function ($resource) {

        var resource =  $resource('assets/data/onu/commands/batchgui.json',
            {
                query: {isArray: false}
            }
        );
        return resource;
    };


    var CmdMenusResource = function ($resource) {
        var resource =  $resource('/cmd/menu',
            {
            },
            {
                get: {
                    method : 'get',
                    params : {
                        ontType:'@ontType'
                    }
                }
            }
        );
        return resource;
    };

    var CustomCommandResource = function ($resource) {

        var resource =  $resource('http://:ip:8043/onu/command/',
            /*{
                ip : '@ip'
            },
            {
                post: {
                    method : 'post',
                    params : {
                        Command:'@Command'
                    }
                }
            }*/
            {
                ip : '@ip'
            }
        );
        return resource;
    };


    var TelnetCmdResource = function ($resource) {
        var resource =  $resource('/onu/command/',
            {
            }
        );
        return resource;
    };

    var commandsResource = angular.module('resources.commandsResource', []);
    commandsResource
        .factory('CmdMenusResource', ['$resource',CmdMenusResource])
        .factory('CustomCommandResource', ['$resource',CustomCommandResource])
        .factory('CommandResource', ['$resource',CommandResource])
        .factory('TelnetCmdResource', ['$resource',TelnetCmdResource])
        .factory('BatchGuiResource', ['$resource',BatchGuiResource]);

})();
