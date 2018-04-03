/**
 * Created by jiegc on 4/20/17.
 */
(function(){
    'use strict';

    var trustUrl = function($sce){
        return function (url) {
            if(url != "" && url != undefined && url != null)
                return $sce.trustAsResourceUrl(url);
            return "";
        }
    };
    angular.module('filter.trustUrlService',[]).filter('trustUrl',['$sce',trustUrl]);
})();