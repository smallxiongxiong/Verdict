/**
 * Created by jiegc on 1/3/17.
 */

(function(){
    'use strict';

    var trustHtml = function($sce){
        return function (input) {
            return $sce.trustAsHtml(input);
        }
    };
    angular.module('filter.trustHtmlService',[]).filter('trustHtml',['$sce',trustHtml]);
})();