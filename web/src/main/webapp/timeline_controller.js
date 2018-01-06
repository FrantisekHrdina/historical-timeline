let timeline = angular.module('timeline', []);

function loadTimelines($http, $scope) {
    console.log('Loading timelines');
    $http.get(restInterface + '/timelines').then(function (response) {
        $scope.timelines = response.data;
    });
}

timeline.controller('TimelinesCtrl', function ($scope, $http, $rootScope, $location) {
    loadTimelines($http, $scope);

    $http.get(restInterface + '/groups').then(function (response) {
        $scope.groups = response.data;
    });

    $http.get(restInterface + '/events').then(function (response) {
        $scope.events = response.data;
    });

    $scope.createTimeline = function (timeline) {
        console.log('create new timeline');
        $scope.timeline = null;
        $location.path('new_timeline');
    };

    $scope.changeGroupView = function (timelineId) {
        $rootScope.timelineId = timelineId;
        $location.path('timeline_group_change');
    };

    $scope.changeGroup = function (groupId) {
        $http({
            method: 'PUT',
            url: restInterface + '/timelines/' + $rootScope.timelineId + '/setseminargroup/' + groupId
        }).then(function success(response) {
            console.log('setting new group');
            $rootScope.successAlert = 'Set new group.';
            loadTimelines($http, $scope);
            $location.path('timelines')
        }, function error(response) {
            console.log('could not set group to timeline');
            $rootScope.errorAlert = 'Could not set group to timeline.';
        });
    };

    $scope.addEventsView = function (timelineId) {
        console.log('add event view tml_id' + timelineId);
        $rootScope.timelineId = timelineId;
        $scope.newEvents = {
            ids: []
        };
        $location.path('timeline_add_event');
    };



    $scope.setEvents = function (newEvents) {
        console.log('setEvents ' + $rootScope.timelineId);
        console.log('setEvents ' + newEvents);
        for (i = 0; i < newEvents.length; i++) {
            $http({
                method: 'PUT',
                url: restInterface + '/timelines/' + $rootScope.timelineId + '/addevent/' + newEvents[i]
            }).then(function success(response) {
                console.log('setting new group');
                $rootScope.successAlert = 'Set new event.';
                loadTimelines($http, $scope);
                $location.path('timelines')
            }, function error(response) {
                console.log('could not set event to timeline');
                $rootScope.errorAlert = 'Could not set event to timeline.';
            });
        }
    };

    $scope.deleteTimeline = function (timeline) {
        console.log('delete timeline with id=' + timeline.id);
        $http({
            method: 'DELETE',
            url: restInterface + '/timelines/' + timeline.id
        }).then(function success(response) {
            console.log('deleted timeline ' + timeline.name);
            $rootScope.successAlert = 'Timeline "' + timeline.name + '" was deleted.';
            loadTimelines($http, $scope);
        }, function error(response) {
            console.log('could not delete timeline ' + timeline.name);
            $rootScope.errorAlert = 'Could not delete timeline.';
        });
    };

    $scope.loadEvent = function (event) {
        $rootScope.event = event;
        $location.path('new_event');
    };

    $scope.removeEvent = function (timeline, event) {
        $http({
            method: 'PUT',
            url: restInterface + '/timelines/' + timeline.id + '/removeevent/' + event.id
        }).then(function success(response) {
            console.log('Removed timeline ' + timeline.name);
            $rootScope.successAlert = 'Event "' + event.name + '" removed from timeline "' + timeline.name + '"';
            loadTimelines($http, $scope);
        }, function error(response) {
            console.log('Could not remove event from timeline "' + timeline.name + '".');
            $rootScope.errorAlert = 'Could not remove event from timeline.';
        });
    };

    $scope.viewTimeline = function (timeline) {
        $http({
            method: 'GET',
            url: restInterface + '/timelines/' + timeline.id
        }).then(function success(response){
            $scope.timeline = response.data;

            console.log('View timeline ' + timeline.name);
            $rootScope.successAlert = 'Timeline view loaded ' + timeline.name;
            $location.path('view_timeline')
        }, function error(response) {
        console.log('Could notload timeline "' + timeline.name + '".');
        $scope.errorAlert = 'Could notload timeline.';
    });

    }

    $scope.addCommentView = function (timelineId) {
        console.log(timelineId);
        $rootScope.timelineId = timelineId;
        console.log(timelineId);
        $location.path('new_comment');
    };


    $scope.addComment = function (timelineId, comment) {
        $scope.comment = {
            'comment' :''
        };
        $scope.comment.comment = comment;
        $http({
            method: 'PUT',
            url: restInterface + '/timelines/' + timelineId + '/addcomment',
            data: $scope.comment
        }).then(function success(response) {
            console.log('adding comment');
            $rootScope.successAlert = 'Added comment.';
            loadTimelines($http, $scope);
            $location.path('timelines')
        }, function error(response) {
            console.log('could not add comment to timeline');
            $rootScope.errorAlert = 'Could not add comment to timeline.';
        });
    };

});

timeline.controller('NewTimelineCtrl', function ($scope, $http, $rootScope, $location) {
    console.log('New Timeline form');

    $http.get('events').then(function (response) {
        $scope.events = response.data;
    });

    $http.get('groups').then(function (response) {
        $scope.groups = response.data;
    });

    $scope.timeline = {
        'name': ''//,
        // 'events': {},
        // 'seminarGroup': ''
    };

    $scope.createTimeline = function (timeline) {
        $http({
            method: 'POST',
            url: restInterface + '/timelines/create',
            data: timeline
        }).then(function success(response) {
            var createdTimeline = response.data;
            $rootScope.successAlert = 'A new timeline "' + createdTimeline.name + '" was created';
            $location.path('timelines')
        }, function error(response) {
            $rootScope.errorAlert = 'Cannot create timeline.';
        })
    };
});


timeline.controller('TimelineViewCtrl', ['$scope', '$routeParams', '$http',
    function ($scope, $routeParams, $http) {
        var timelineId = $routeParams.timelineId;
        $http.get(restInterface + '/timelines/' + timelineId).then(function (response) {
            var timeline = response.data;
            $scope.timeline = timeline;
            console.log('AJAX loaded detail of category ' + timeline.name);
            //$location.path('view_timeline')
        });
    }]);

