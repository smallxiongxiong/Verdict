/**
 * Created by Lens on 1/11/17.
 */
(function () {
    'use strict';


    var nffAlertController = function($scope,$timeout) {

        $scope.showError = false;
        $scope.doFade = false;


        $scope.showAlert = function(errorMessage){
            $scope.showError = false;
            $scope.doFade = false;

            $scope.showError = true;

            $scope.errorMessage = errorMessage;


            $timeout(function(){
                $scope.doFade = true;
            },6000);
        }

    }



    function nffAlert() {
        var directive = {
            controller: 'nffAlertController',
            restrict: 'EA',
            templateUrl: 'common/directives/nffAlert/nffAlert.html'
        }

        return directive;
    }


    angular
        .module('directive.nffAlert', [])
        .directive('nffAlert', nffAlert)
        .controller('nffAlertController',nffAlertController);
})();