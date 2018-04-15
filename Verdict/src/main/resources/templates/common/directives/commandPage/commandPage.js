(function() {
    'use strict';

    var commandPageController = function($scope) {
        $scope.queryBtnDisabled = true;
    };

    /* @ngInject */
    function commandPage() {
        var directive = {
            controller: 'commandPageController',
            scope: {
                title: "@",
                command: "@"
            },
            replace: true,
            restrict: 'EA',
            templateUrl: 'common/directives/commandPage/commandPage.html'
        };
        return directive;
    };

    angular.module('directive.commandPage', [])
        .directive('commandPage', commandPage)
        .controller('commandPageController', ['$scope',commandPageController]);
})();
