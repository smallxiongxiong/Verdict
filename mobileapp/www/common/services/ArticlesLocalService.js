(function(){
    'use strict';

    angular
        .module('app')
        .factory('ArticlesLocalService', ArticlesLocalService);

    function ArticlesLocalService(){

        var service = {
            makeShortDescriptions: makeShortDescriptions
        };

        return service;

        function makeShortDescriptions(length, article) {
            if (article.content.length > length) {
                article.shortDescription = article.content.substr(0, length - 2) + '..';
            } else {
                article.shortDescription = article.content;
            }
            return article;
        }
    }
})();