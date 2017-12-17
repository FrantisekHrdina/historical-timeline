let timeline = angular.module('timeline', []);

function loadTimelines($http, $scope) {
    console.log('Loading timelines');
    $http.get('timelines').then(function (response) {
        $scope.timelines = response.data;
    });
}

timeline.controller('TimelinesCtrl', function ($scope, $http, $rootScope, $location) {
    loadTimelines($http, $scope);

    $scope.createTimeline = function (timeline) {
        console.log('create new timeline');
        $scope.timeline = null;
        $location.path('new_timeline');
    }

    $scope.updateTimeline = function (timeline) {
        console.log('update timeline with id=' + timeline.id);
        console.log(timeline);
        $rootScope.timeline = timeline;
        $location.path('new_timeline');
    }

    $scope.deleteTimeline = function (timeline) {
        console.log('delete timeline with id=' + timeline.id);
        $http({
            method: 'DELETE',
            url: 'timelines/' + timeline.id
        }).then(function success(response) {
            console.log('deleted timeline ' + timeline.name);
            $rootScope.successAlert = 'Timeline "' + timeline.name + '" was deleted.';
            loadTimelines($http, $scope);
        }, function error(response) {
            console.log('could not delete timeline ' + timeline.name);
            $scope.errorAlert = 'Could not delete timeline.';
        });
    };

    $scope.loadEvent = function (event) {
        $location.path('events/' + event.id);
    }

    $scope.removeEvent = function (timeline, event) {
        $http({
            method: 'DELETE',
            url: 'timelines/' + timeline.id + '/removeevent/' + event.id
        }).then(function success(response) {
            console.log('Removed timeline ' + timeline.name);
            $rootScope.successAlert = 'Event "' + event.name + '" removed from timeline "' + timeline.name + '"';
            loadTimelines($http, $scope);
        }, function error(response) {
            console.log('Could not remove event from timeline "' + timeline.name + '".');
            $scope.errorAlert = 'Could not remove event from timeline.';
        });
    }

});

timeline.controller('NewTimelineCtrl', function ($scope, $http, $rootScope, $location) {
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
        'seminarGroup': ''
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