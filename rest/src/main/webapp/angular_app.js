'use strict';

let historicalTimelineApp = angular.module('historicalTimelineApp', ['ngRoute', 'controllers']);
let controllers = angular.module('controllers', []);

historicalTimelineApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/groups', {templateUrl: 'partials/groups.html', controller: 'GroupsCtrl'}).
        when('/new_group', {templateUrl: 'partials/new_group.html', controller: 'NewGroupCtrl'}).
        when('/events', {templateUrl: 'partials/events.html', controller: 'EventsCtrl'}).
        when('/new_event', {templateUrl: 'partials/new_event.html', controller: 'NewEventCtrl'}).
        when('/timelines', {templateUrl: 'partials/timelines.html', controller: 'TimelinesCtrl'}).
        when('/new_timeline', {templateUrl: 'partials/new_timeline.html', controller: 'NewTimelineCtrl'})
	}]);

controllers.controller('GroupsCtrl', function ($scope, $http) {
	console.log('Loading groups');
	$http.get('groups').then(function (response) {
		$scope.groups = response.data;
	});
});

controllers.controller('NewGroupCtrl', function ($scope, $http, $rootScope, $location) {
	console.log('New group form');
	//set object bound to form fields
	$scope.group = {
		'name': '',
		'timelines': []
	};
	$scope.create = function (group) {
		$http({
			method: 'POST',
			url: 'groups/create',
			data: group
		}).then(function success(response) {
			let createdGroup = response.data;
			$rootScope.successAlert = 'A new seminar group "' + createdGroup.name + '" was created';
			$location.path('groups')
		}, function error(response) {
			$scope.errorAlert = 'Cannot create group.';
		})
	};
});
controllers.controller('EventsCtrl', function ($scope, $http) {
    console.log('Loading events');
    $http.get('events').then(function (response) {
        $scope.events = response.data;
    });

    $scope.deleteEvent = function (event) {
        console.log("deleting event with id=" + event.id + ' (' + event.name + ')');
        $http.delete(event._links.delete.href).then(
            function success(response) {
                console.log('deleted event ' + event.id + ' on server');
                //display confirmation alert
                $rootScope.successAlert = 'Deleted event "' + event.name + '"';
                //load new list of all products
                $location.path('events');
            },
            function error(response) {
                console.log("error when deleting event");
                console.log(response);
                switch (response.data.code) {
                    case 'ResourceNotFoundException':
                        $rootScope.errorAlert = 'Cannot delete non-existent event ! ';
                        break;
                    default:
                        $rootScope.errorAlert = 'Cannot delete event ! Reason given by the server: '+response.data.message;
                        break;
                }
            }
        );
    };
});

controllers.controller('NewEventCtrl', function ($scope, $http, $rootScope, $location) {
    console.log('New Event form');
    //set object bound to form fields
    $scope.event = {
        'name': '',
        'date': '',
        'location': '',
        'description': ''
    };
    $scope.create = function (event) {
        $http({
            method: 'POST',
            url: 'events/create',
            data: event
        }).then(function success(response) {
            let createdEvent = response.data;
            $rootScope.successAlert = 'A new event "' + createdEvent.name + '" was created';
            $location.path('events')
        }, function error(response) {
            $scope.errorAlert = 'Cannot create event.';
        })
    };
});


// TIMELINES
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

    $scope.create = function (timeline) {
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
// END TIMELINES