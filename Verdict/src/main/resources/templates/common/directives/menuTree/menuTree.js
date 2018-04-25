/**
 * Created by jiegc on 11/18/16.
 */

(function() {
  'use strict';

  var MenuTreeController = function($scope,$filter,$state,$http,$translate,CmdMenusResource,$window,storageService){
    var tree;
    var apple_selected = function(branch) {
      return $scope.output = "APPLE! : " + branch.label;
    };
    var treedata_avm = [
      {
        label: $filter('translate')('module.menutree.onu.info.title'),
        state: 'home.ontInfo'
      },
        {
            label: $filter('translate')('module.menutree.onu.command.imageInfo'),
            state: 'home.imageInfo'
        },
      {
        label: $filter('translate')('module.menutree.onu.diagnose.offline'),
        state: 'home.offline'
      },
      {
        label: $filter('translate')('module.menutree.onu.diagnose.online'),
        state: 'home.online'
      },
      {
        label: $filter('translate')('module.menutree.onu.command.title'),
          state: 'home.customCommand'
      },
      {
        label: $filter('translate')('module.menutree.onu.batchCommand.title'),
         state: 'home.batch'
      },
      {
        label: $filter('translate')('module.menutree.onu.system.title'),
        children:[
          {
            label: $filter('translate')('module.menutree.onu.system.recovery'),
            state: 'home.recovery'
          },
          {
            label: $filter('translate')('module.menutree.onu.system.upload'),
            //state: 'home.upload'
          },
          {
            label: $filter('translate')('module.menutree.onu.system.operator'),
            //state: 'home.operator'
          },
          {
            label: $filter('translate')('module.menutree.onu.system.upgrade'),
            //state: 'home.upgrade'
          },
          {
            label: $filter('translate')('module.menutree.onu.system.reboot'),
            //state: 'home.reboot'
          }
        ]
      }
    ];
    $scope.my_data = treedata_avm;
    $scope.my_tree = tree = {};

    $scope.generateCmdMenu = function() {
        var parameters = {};
        parameters['onuType'] = $scope.onuType.toUpperCase();
        console.log("parameters: " + parameters['onuType']);
        CmdMenusResource.get(parameters,function(response){
            //console.log(response);

            var cmdMenus = response.cmdMenus;
            var cmdMenuDataArr = [];
            for(var key in cmdMenus) {
                //console.log($translate.use());
                if(cmdMenus[key]['english'] != undefined && cmdMenus[key]['chinese'] != undefined && cmdMenus[key]['command'] != undefined) {
                     console.log(key + "," + cmdMenus[key]);
                     var menuLabel = cmdMenus[key][$translate.use()];

                     var cmdMenuData = {};
                     cmdMenuData['label'] = menuLabel;
                     cmdMenuData['value'] = cmdMenus[key]['command'];
                     cmdMenuData['state'] = 'home.customCommand';
                     cmdMenuDataArr.push(cmdMenuData);
                }
            }

            angular.forEach($scope.my_data,function(data) {
                if(data.label === $filter('translate')('module.menutree.onu.command.title')) {
                    data.children = cmdMenuDataArr;
                } else if(data.label == $filter('translate')('module.menutree.onu.batchCommand.title')) {
                    data.value = cmdMenuDataArr;
                }
            });
        });
    };

    $scope.$on('getCmdMenu', function(e, d) {
      $scope.onuType = d;
      $scope.generateCmdMenu();
    });

    $scope.treeSelected = function(branch){
      if(branch.state != null) {
        $scope.output = branch.state;
        console.log("branch.value: " + branch.value);
        if(branch.state != 'home.debug') {
            $state.go(branch.state,{value:branch.value,title:branch.label});
        } else {
            $window.open('http://' + storageService.getIP() + ':4200','_blank');
        }

      }
    };
  };

  /* @ngInject */
  function menuTree() {
    var directive = {
      controller : 'menuTreeController',
      replace: true,
      restrict: 'EA',
      templateUrl: 'common/directives/menuTree/menuTree.html'
    };
    return directive;
  };

  angular.module('directive.menuTree',[])
    .directive('menuTree', menuTree)
    .controller('menuTreeController',['$scope','$filter','$state','$http','$translate','CmdMenusResource','$window','storageService',MenuTreeController]);
})();
