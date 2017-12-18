'use strict';

let historicalTimelineApp = angular.module('historicalTimelineApp', [
	'ngRoute',
	'group',
	'event',
        'user',
    'timeline'
]);

historicalTimelineApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/groups', {templateUrl: 'partials/groups.html', controller: 'GroupsCtrl'}).
        when('/new_group', {templateUrl: 'partials/new_group.html', controller: 'NewGroupCtrl'}).
        when('/events', {templateUrl: 'partials/events.html', controller: 'EventsCtrl'}).
        when('/new_event', {templateUrl: 'partials/new_event.html', controller: 'NewEventCtrl'}).
        when('/timelines', {templateUrl: 'partials/timelines.html', controller: 'TimelinesCtrl'}).
        when('/new_timeline', {templateUrl: 'partials/new_timeline.html', controller: 'NewTimelineCtrl'}).
        when('/new_comment', {templateUrl: 'partials/new_comment.html', controller: 'TimelinesCtrl'}).
        when('/users', {templateUrl: 'partials/users.html', controller: 'UsersCtrl'})
	}]);

