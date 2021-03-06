/**
 * Created by huy on 6/29/2016.
 */
'use strict';

angular.module('Application')
    .factory("ApplicationService", ['CSService', 'ApplicationDetail', '$q', function (CSService, ApplicationDetail, $q) {
        var service = {};
        var currentApplicationDetail;
        var newAppConfig;
        
        service.getApplicationDetail = function (id, artifactId) {
            if (id) {
                return CSService.getApplicationDetail(id, artifactId).then(function (applicationDetail) {
                    currentApplicationDetail = applicationDetail;
                    newAppConfig = false;
                    return currentApplicationDetail;
                });
            } else {
                currentApplicationDetail = new ApplicationDetail();
                newAppConfig = true;
                return $q.when(currentApplicationDetail);
            }
        };

        service.canRemoveThisConfig = function(){
            if(currentApplicationDetail!=null) {

                return typeof currentApplicationDetail.status.seenInTheLastHourCount === "undefined" ||
                    (typeof currentApplicationDetail.status != "undefined" && currentApplicationDetail.status.seenInTheLastHourCount == 0);

            } else {
                return false;
            }
        }

        service.removeApplicationConfig = function () {
            if(typeof currentApplicationDetail.config.id != "undefined" ){
                return CSService.removeApplicationConfig(currentApplicationDetail.id, currentApplicationDetail.config.id);
            } else {
                return CSService.removeApplication(currentApplicationDetail.id);
            }
        }

        service.save = function () {
            var promise = newAppConfig ? CSService.addApplicationConfig(currentApplicationDetail) : CSService.updateApplicationConfig(currentApplicationDetail);
            promise.then(function (data) {
                newAppConfig = false;
            });
            return promise;
        };

        return service;
    }]);
