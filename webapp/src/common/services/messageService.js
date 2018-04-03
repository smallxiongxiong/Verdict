

(function(){
    'use strict';



    angular.module('services.messageService',[])
        .factory('MessageService',['$timeout', function ($timeout) {
            return {
                message : null,
                type : null,
                setMessage : function(msg,type){

                    this.message = msg;
                    this.type = type;

                    //3
                    var _self = this;
                    $timeout(function(){
                        _self.clear();
                    },5000);
                },
                clear : function(){
                    this.message = null;
                    this.type = null;
                }
            };
        }
        ]);
})();

