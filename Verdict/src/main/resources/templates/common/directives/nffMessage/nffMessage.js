/**
 * Created by ems on 12/29/16.
 */
(function () {
    'use strict';



    var nffMessageController=function ctrl($scope, $rootScope,MessageService) {

        $rootScope.messageService=MessageService;

    }

    /* @ngInject */
    function nffMessage() {
        return {
            controller: 'nffMessageController',
            restrict: 'EA',
            templateUrl: 'common/directives/nffMessage/nffMessage.html',
            scope : {
                message : "=",
                type : "="
            },
            link: function(scope, element, attrs){

                scope.hideAlert = function() {
                    scope.message = null;
                    scope.type = null;
                };

            }
        };
    }
    angular.module('directive.nffMessage',['ngAnimate'])
        .directive('nffMessage', nffMessage)
        .controller('nffMessageController',nffMessageController)
        //.animation('.message-fade',function () {
        //    return {
        //        beforeAddClass : function(element, className, done) {
        //            if (className === 'ng-show') {
        //                element.animate({
        //                    opacity: 0
        //                },500, done);
        //            } else {
        //                done();
        //            }
        //        },
        //        removeClass : function(element, className, done) {
        //            if (className === 'ng-show') {
        //                element.css('opacity',0);
        //                element.animate({
        //                    opacity: 1
        //                }, 500, done);
        //            } else {
        //                done();
        //            }
        //        }
        //    };
        //});
})();
