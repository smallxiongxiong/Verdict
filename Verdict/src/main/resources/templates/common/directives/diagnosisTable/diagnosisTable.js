/**
 * Created by jiegc on 12/13/16.
 */

(function() {
    'use strict';

    var DiagnosisTableController = function($scope,$q,$translate,storageService,LineResource,WifiUserPwdResource,localSaveTableResource,$filter,$state,$mdDialog,blockUI,toaster){
        var executeTestItemArr = [];
        var selectedItemArr = [];
        var ontInfo = storageService.getObject("ont");
        var step = 0;
        var str = "";
        var checkRepeat = false;
        var descriptionLevel = 1;
        var prepareStr = 'prepare';
        var finishedStr = 'finished';
        var testingStr = 'testing';
        var failedStr = 'fail';
        var timeoutStr = 'timeOut'
        var diagnosisStr = 'diagnosis';

        var voicePortNumber = ontInfo['Interface']['Voice'];
        var lanPortNumber = ontInfo['Interface']['Lan'];
		var wifiNumber = ontInfo['Interface']['Wifi'];
        var portObj = {};
        portObj['lanTest'] = {'displayPrefix':'LanPort','portNums':lanPortNumber};
        portObj['voiceTest'] = {'displayPrefix':'VoicePort','portNums':voicePortNumber};
		portObj['wifiTest'] = {'displayPrefix':'','portNums':wifiNumber};
        portObj['wifisoc'] = {'displayPrefix':'','portNums':wifiNumber};

        $scope.selfDiagnosisResult = "PASS";

        var partBlock = blockUI.instances.get('partBlock');
        if(partBlock.state().blocking) {
            partBlock.stop();
        }

        $scope.generateDataFormat = function(diagnosisItemsData) {
            var testItems = [];
            angular.forEach(diagnosisItemsData, function (testCategory) {
                var category = {};
                category['category'] = testCategory['category'];
                var ports = [];
                var portNums = 1;
                var obj = {};
                var portDetail = getPortDetail(testCategory.category);
                if(portDetail !== undefined) {
                    $scope.showPortCol = true;
                    portNums = portDetail['portNums'];
                    for(var i=1; i<=portNums; i++) {
                        obj = {};
                        obj['port'] = portDetail['displayPrefix'] != '' ? portDetail['displayPrefix'] : '';
                        obj['portNum'] = portDetail['displayPrefix'] != '' ? i : '';
                        obj = createItemObj(obj,testCategory,category,i);
                        ports.push(obj);
                    }
                } else {
                    obj['port'] = '';
                    obj = createItemObj(obj,testCategory,category,'');
                    ports.push(obj);
                }
                category['ports'] = ports;

                category['rowspanValue'] = (testCategory['items'].length) * portNums + 1;
                if(portNums > 0){
                    testItems.push(category);
                }
            });
            //console.log(testItems);
            $scope.testItems = testItems;
            $scope.selectAllTestItem = true;
            $scope.saveBtnDisabled = true;
            $scope.testBtnDisabled = false;
        };

        function createItemObj(obj,testCategory,category,portNum) {
            var testItem;
            obj['items'] = [];
            angular.forEach(testCategory['items'], function (data) {
                if(data['autoCfm'] === false) {
                    testItem = new ManualCfmItem(category['category'],data['item'],data['autoCfm'],testCategory['progress'],testCategory['testType'],data['path'],obj['port'],portNum,true,data['suggestion'],true,data['uriParameter'],data['showCheckbox'],data['commentKey'],true);
                } else {
                    testItem = new AutoCfmItem(category['category'],data['item'],data['autoCfm'],testCategory['progress'],testCategory['testType'],data['path'],obj['port'],portNum,true,data['suggestion'],true,data['uriParameter'],data['showCheckbox'],data['commentKey']);
                }
                var itemFlag = getPortDetail(data['item']);
                if(itemFlag === undefined || (itemFlag != undefined && portObj[data['item']]['portNums'] !== 0)) {
                    obj['items'].push(testItem);
                    selectedItemArr.push(testItem);
                }
            });
            return obj;
        }

        function getPortDetail(category) {
            for(var key in portObj) {
                if(category === key) {
                    return portObj[key];
                }
            }
        }

        function parseDiscription(discription,signle) {
            for(var key in discription){
                console.log(key+" "+discription[key])
                if(typeof(discription[key]) === "object") {
                    if(discription.length === undefined && descriptionLevel >= 3 && checkRepeat) {
                        checkRepeat = false;
                        //console.log("<br/>" + "&nbsp;&nbsp;&nbsp;&nbsp;");
                        //str = str + "<br/>" + "&nbsp;&nbsp;&nbsp;&nbsp;";
                        str = str + "\n" + "    ";
                    } else if(descriptionLevel >= 2 && checkRepeat) {
                        checkRepeat = false;
                        //console.log("<br/>");
                        //str = str + "<br/>";
                        str = str + "\n";
                    }
                    parseDiscription(discription[key],"<br/>",++descriptionLevel);
                    //--descriptionLevel;
                } else if(signle != "<br/>"){
                    checkRepeat = true;
                    if(discription[key] === null || discription[key] === undefined) {
                        str = str + discription[key];
                    } else {
                        str = str + "," + discription[key];
                    }
                    //console.log("discription " + discription[key]);
                    signle = "";
                } else {
                    checkRepeat = true;
                    str = str + discription[key];
                    signle = "";
                }
            }
            return str;
        }

        function initData() {
            selectedItemArr = [];
            executeTestItemArr = [];
            $scope.testBtnDisabled = true;
            $scope.saveBtnDisabled = true;
            step = -1;
            angular.forEach($scope.testItems, function (testCategory) {
                angular.forEach(testCategory.ports,function(testPort){
                    angular.forEach(testPort.items, function (testItem) {
                        testItem.progress = prepareStr;
                        testItem.btnDisabled = true;
                        testItem.btnStartDisabled = true;
                        testItem.comment = '';
                        testItem.result = '';
                        testItem.firstSendDiagnosisReq = true;
                        testItem.suggestionPrefix = "";
                        if(testItem.checkValue || $scope.selectAllTestItem) {
                            executeTestItemArr.push(testItem);
                        }
                    });
                });
            });
        }

        function removeDataInArr(dataArr,testItem) {
            var index = getIndexOfArr(dataArr, testItem);
            if(index > -1) {
                dataArr.splice(index, 1);
            }
        }

        function getIndexOfArr(dataArr,testItem) {
            for(var i = 0; i <  dataArr.length; i++) {
                if(dataArr[i].item == testItem.item)
                    return i;
            }
            return -1;
        }

        function doNextStep() {
            step++;
            if(executeTestItemArr.length > step && executeTestItemArr[step].item === 'wifi' && executeTestItemArr[step].testType === 'offline') {
                getWifiUserAndPsw(executeTestItemArr[step]);
                executeTestItemArr[step].btnStartDisabled = false;
            } else if(executeTestItemArr.length > step && executeTestItemArr[step]['suggestion']) {
                executeTestItemArr[step].btnStartDisabled = false;
            } else if(executeTestItemArr.length > step ) {
                processTest(executeTestItemArr[step]);
            } else if(step >= 1){
                $scope.testBtnDisabled = false;
                $scope.saveResult();
            } else {
                $scope.saveBtnDisabled = false;
                $scope.testBtnDisabled = false;
            }
        }

        function processTest(selectedItem) {
            str = "";
            descriptionLevel = 1;
            checkRepeat = false;
            selectedItem.progress = testingStr;

            var valueObj = {};
            if(!selectedItem.showCheckbox || selectedItem.showCheckbox && selectedItem.shine && selectedItem.firstSendDiagnosisReq) {
                valueObj['onValue'] = 1;
            }

            partBlock.start();
            sendDiagnosisReq(selectedItem,valueObj).then(
                function (data) {
                    partBlock.stop();
                    if(selectedItem.showCheckbox && selectedItem.shine && selectedItem.firstSendDiagnosisReq) {
                        selectedItem.firstSendDiagnosisReq = false;
                        processTest(selectedItem);
                    } else {
                        parseResultAndDoNextStep(selectedItem,data);
                    }
                },function (err) {
                    console.log("Request took too long, cancelled. " + err);
                    partBlock.stop();
                    if(selectedItem.showCheckbox && selectedItem.shine && selectedItem.firstSendDiagnosisReq) {
                        selectedItem.firstSendDiagnosisReq = false;
                        processTest(selectedItem);
                    } else {
                        parseResultAndDoNextStep(selectedItem,null);
                    }
                }
            );
        }

        function parseResultAndDoNextStep(selectedItem,data){
            //console.log(data.Discription);
            if(data != null && selectedItem.commentKey !== undefined) {
                selectedItem.comment = selectedItem.commentKey + ":" + data.Discription[selectedItem.commentKey];
            } else if(data != null && data.Discription !== null &&  data.Discription !== undefined) {
                selectedItem.comment = parseDiscription(data.Discription,"<br/>");
            }

            if(!selectedItem.autoCfm && data != null) {
                selectedItem.btnDisabled = false;
                if(selectedItem.result === failedStr) {
                    selectedItem.progress = finishedStr;
                    selectedItem.comment = data.Status;
                    doNextStep();
                }
            } else if(selectedItem.autoCfm && data != null) {
                selectedItem.progress = finishedStr;
                selectedItem.result = data.Status;
                doNextStep();
            } else if(selectedItem.autoCfm && data == null) {
                selectedItem.progress = finishedStr;
                selectedItem.result = timeoutStr;
                doNextStep();
            } else if(!selectedItem.autoCfm && data == null) {
                selectedItem.progress = finishedStr;
                selectedItem.comment = $translate.instant('module.diagnosis.timeOut');
                selectedItem.result = timeoutStr;
                doNextStep();
            }
            console.log(data);
        }

        function sendDiagnosisReq(selectedItem,valueObj) {
            var parameters = {};
            parameters['ip'] = storageService.getIP();
            parameters['type'] = selectedItem.testType;
            parameters['item'] = selectedItem.path;
            var onValue = valueObj['onValue'];
            /*if(selectedItem.portNum != null && selectedItem.portNum != "") {
             parameters[selectedItem.uriParameter[0]] = selectedItem.portNum;
             }*/
            if(selectedItem.uriParameter != undefined && selectedItem.uriParameter[0] != "") {
                parameters[selectedItem.uriParameter[0]] = selectedItem.portNum;
            }
            if(selectedItem.uriParameter != undefined && selectedItem.uriParameter[1] != "" && onValue!= undefined) {
                parameters[selectedItem.uriParameter[1]] = onValue;
            }
            if(selectedItem.uriParameter != undefined && selectedItem.uriParameter[2] != "") {
                //parameters[selectedItem.uriParameter[2].key] = selectedItem.uriParameter[2].value;
                for(var key in selectedItem.uriParameter[2]) {
                    parameters[key] = selectedItem.uriParameter[2][key];
                }
            }
            return LineResource.get(parameters).$promise;
        }

        function getWifiUserAndPsw(selectedItem) {
            partBlock.start();
            var parameters = {};
            parameters['ip'] = storageService.getIP();
            WifiUserPwdResource.get(parameters,
                function (data) {
                    console.log(data);
                    var wlanArr = data.Discription.Wlan;
                    if(wlanArr != undefined && wlanArr.length > 0) {
                        //console.log(wlanArr[0].Password);
                        //console.log(wlanArr[0].SSID);
                        selectedItem.suggestionPrefix = $translate.instant('module.diagnosis.ssid') + ":" + wlanArr[0].SSID +
                                                     "," + $translate.instant('module.diagnosis.password') + ":" + wlanArr[0].Password;
                    }
                    partBlock.stop();
                },function (err) {
                    console.log(err);
                    partBlock.stop();
                }
            );
        }

        //button action start
        $scope.startTestSelectedItems = function() {
            initData();
            console.log("start test!");
            //start items test
            doNextStep();
        };

        $scope.startTestCurrentItem = function() {
            executeTestItemArr[step].btnStartDisabled = true;
            processTest(executeTestItemArr[step]);
        }


        $scope.confirmResult = function(selectedItem,result) {
            selectedItem.progress = finishedStr;
            if(selectedItem.comment === '' || selectedItem.comment === undefined) {
                selectedItem.comment = result;
            }
            selectedItem.result = result;
            selectedItem.btnDisabled = true;

            if(selectedItem.uriParameter != undefined && selectedItem.uriParameter[1] != "" &&
                (!selectedItem.showCheckbox || selectedItem.showCheckbox && selectedItem.shine)) {
                partBlock.start();
                var valueObj = {};
                valueObj['onValue'] = 0;
                sendDiagnosisReq(selectedItem,valueObj).then(
                    function (data) {
                        partBlock.stop();
                        doNextStep();
                    },function (err) {
                        console.log("Request took too long, cancelled. " + error);
                        //console.log(err);
                        partBlock.stop();
                        doNextStep();
                    }
                );
            } else {
                doNextStep();
            }
        };

        $scope.saveResult = function() {
            var saveTestItemArr = [];
            saveTestItemArr.push("category,port,item,progress,autoConfirm,comment,results"+"\n");
            saveTestItemArr = saveTestItemArr.concat(executeTestItemArr);

            var params = {};
            params['SN'] = ontInfo['GPONSN'];
            params['category'] = diagnosisStr + "_" + saveTestItemArr[saveTestItemArr.length-1].testType;
            params['content'] = saveTestItemArr;
            if($scope.type === 'offline') {
                params['selfDiagnosis'] = "Self Diagnosis:" + $scope.selfDiagnosisResult;
            }

            localSaveTableResource.sav(params,
                function(data){
                    //alert("Save File Successful!");
                    //toaster.pop({ type: 'success', body: $translate.instant('module.msg.saveFileSuccessfully')});
                },
                function(err){
                    //alert("Get err = " + err);
                    var msg = $translate.instant('module.msg.saveFileWithError') + err;
                    toaster.pop({ type: 'error', body: msg});
                }
            );

        };

        $scope.chkAll = function() {
            selectedItemArr = [];
            angular.forEach($scope.testItems, function (testCategory) {
                angular.forEach(testCategory.ports,function(testPort){
                    angular.forEach(testPort.items, function (testItem) {
                        testItem.checkValue = $scope.selectAllTestItem;
                        if(testItem.checkValue) {
                            selectedItemArr.push(testItem);
                        }
                    });
                });
            });
            //console.log(selectedItemArr);
        };

        $scope.chk = function(testItem,checkValue) {
            if(testItem.checkValue) {
                selectedItemArr.push(testItem);
            } else {
                $scope.selectAllTestItem = false;
                removeDataInArr(selectedItemArr, testItem);
            }
            console.log(selectedItemArr);
        };

        $scope.changeStatus = function() {
            if($scope.selfDiagnosisResult === 'PASS') {
                $scope.selfDiagnosisResult = 'NO PASS';
            } else {
                $scope.selfDiagnosisResult = 'PASS';
            }
        }

        //button action end
        //Item Object
        var AutoCfmItem = function(category,item,autoCfm,progress,testType,path,portStr,portNum,checkValue,suggestion,btnStartDisabled,uriParameter,showCheckbox,commentKey) {
            this.category = category;
            this.item = item;
            this.autoCfm = autoCfm;
            this.progress = progress;
            this.testType = testType;
            this.path = path;
            this.portStr = portStr;
            this.portNum = portNum;
            this.checkValue = checkValue;
            this.suggestion = suggestion;
            this.btnStartDisabled = btnStartDisabled;
            this.uriParameter = uriParameter;
            this.showCheckbox = showCheckbox;
            this.commentKey = commentKey;
        };

        var ManualCfmItem = function(category,item,autoCfm,progress,testType,path,portStr,portNum,checkValue,suggestion,btnStartDisabled,uriParameter,showCheckbox,commentKey,btnDisabled){
            AutoCfmItem.call(this,category,item,autoCfm,progress,testType,path,portStr,portNum,checkValue,suggestion,btnStartDisabled,uriParameter,showCheckbox,commentKey);
            this.btnDisabled = btnDisabled;
        };

    };

    /* @ngInject */
    function diagnosisTable() {
        var directive = {
            controller : 'diagnosisTableController',
            replace: true,
            restrict: 'EA',
            scope: {
              mytitle: '@',
              type: '@',
              diagnosisItemsData: '='
            },
            templateUrl: 'common/directives/diagnosisTable/diagnosisTable.html',
            link: function(scope,elem,attrs){
                scope.$watch('diagnosisItemsData', function(){
                    if(scope.diagnosisItemsData != undefined &&  scope.diagnosisItemsData != ''){
                        scope.generateDataFormat(scope.diagnosisItemsData);
                    }
                });
            }
        };
        return directive;
    };

    angular.module('directive.diagnosisTable',[])
        .directive('diagnosisTable', diagnosisTable)
        .controller('diagnosisTableController',['$scope','$q','$translate','storageService','LineResource',
            'WifiUserPwdResource','localSaveTableResource','$filter','$state','$mdDialog','blockUI','toaster',DiagnosisTableController]);
})();
