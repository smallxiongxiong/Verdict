/**
 * Created by jiegc on 2/28/17.
 */
(function(){
    'use strict';

    var convertToDate = function(){
        var filter = function(input){
            var dateStr = "";
            if(input && input != "000000" && input.length == 6){
                dateStr = input.substr(2,2) + "," + input.substr(4,2) + "," + "20" + input.substr(0,2)
                return dateStr;
            }
            return dateStr;
        };
        return filter;
    };

    angular.module('filter.convertToDateService',[]).filter('convertToDate',convertToDate);
})();