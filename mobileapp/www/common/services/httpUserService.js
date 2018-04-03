/**
 * Created by jiegc on 8/14/17.
 */

(function() {
    'use strict';

    var HttpUserService = function($q,OntHttpUsersResource) {
        this.getUserPassArr = function() {
            var deferred = $q.defer();
            OntHttpUsersResource.query({},function(data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
    };

    angular.module('services.httpUserService',[])
        .service('httpUserService',['$q','OntHttpUsersResource',HttpUserService]);
})();