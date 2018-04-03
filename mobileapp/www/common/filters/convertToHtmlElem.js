/**
 * Created by jiegc on 1/3/17.
 */

(function(){
    'use strict';

    var convertToHtmlElem = function(){
        var filter = function(input){
            if(input)
              return input.replace(/\n/g,"<\/br>").replace(/ /g,"&nbsp;");
        };
        return filter;
    };
    angular.module('filter.convertToHtmlElemService',[]).filter('convertToHtmlElem',convertToHtmlElem);
})();
