(function () {
    'use strict';

    angular
        .module('directive.nffFooter', [])
        .directive('nffFooter', nffFooter);


    var nffFooterController=function ctrl($scope,$window,$document) {
        if($window.innerHeight < $document[0].body.clientHeight) {
            $scope.showAfterContent = true;
        } else {
            $scope.showAfterContent = false;
        }
    }

    /* @ngInject */
    function nffFooter() {
        var directive = {
            controller: 'nffFooterController',
            restrict: 'EA',
            scope: {
                year: '@',
                code: '@',
                shortName: '@',
                release: '@',
                buildDate: '@'
            },
            templateUrl: 'common/directives/nffFooter/nffFooter.html'
        };
        return directive;


    }
    angular.module('directive.nffFooter')
           .controller('nffFooterController',['$scope','$window','$document',nffFooterController]);
})();


