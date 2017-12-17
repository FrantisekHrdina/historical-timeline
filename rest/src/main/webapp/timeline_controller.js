let group = angular.module('group', []);


controllers.controller('TimelinesCtrl', function ($scope, $http) {
    console.log('Loading timelines');
    $http.get('timelines').then(function (response) {
        $scope.timelines = response.data;
    });

    $scope.deleteTimeline = function (timeline) {
        console.log("deleting timelines with id=" + timeline.id);
        $http.delete(timeline._links.delete.href).then(
            function success(response) {
                console.log('deleted timeline ' + timeline.id + ' on server');
                //display confirmation alert
                $rootScope.successAlert = 'Deleted timeline "' + timeline.name + '"';
                //load new list of all products
                $location.path('timelines');
            },
            function error(response) {
                console.log("error when deleting timeline");
                console.log(response);
                switch (response.data.code) {
                    case 'ResourceNotFoundException':
                        $rootScope.errorAlert = 'Cannot delete non-existent timeline!';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot delete timeline! Reason given by the server: ' + response.data.message;
                        break;
                }
            }
        );
    };
});

controllers.controller('NewTimelineCtrl', function ($scope, $http, $rootScope, $location) {
    console.log('New Timeline form');
    //set object bound to form fields

    $http.get('events').then(function (response) {
        $scope.events = response.data;
    });

    $http.get('groups').then(function (response) {
        $scope.groups = response.data;
    });

    $scope.timeline = {
        'name': '',
        'events': {},
        'group': ''
    };

    $scope.createTimeline = function (timeline) {
        $http({
            method: 'POST',
            url: 'timelines/create',
            data: timeline
        }).then(function success(response) {
            var createdTimeline = response.data;
            $rootScope.successAlert = 'A new timeline "' + createdTimeline.name + '" was created';
            $location.path('timelines')
        }, function error(response) {
            $scope.errorAlert = 'Cannot create timeline.';
        })
    };
});