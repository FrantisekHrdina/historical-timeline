'use strict';

let historicalTimelineApp = angular.module('historicalTimelineApp', [
    'ngRoute',
    'ngCookies',
	'group',
	'event',
    'user',
    'timeline',
    'auth'
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
        when('/timeline_group_change', {templateUrl: 'partials/timeline_group_change.html', controller: 'TimelinesCtrl'}).
        when('/timeline_add_event', {templateUrl: 'partials/timeline_add_event.html', controller: 'TimelinesCtrl'}).
        when('/users', {templateUrl: 'partials/users.html', controller: 'UsersCtrl'}).
        when('/assign_user', {templateUrl: 'partials/assign_user.html', controller: 'UsersCtrl'}).
        when('/login', {templateUrl: 'partials/login_form.html', controller: 'LoginController'}).
        when('/dashboard', {templateUrl: 'partials/dashboard.html', controller: 'TimelinesCtrl'}).
        when('/view_timeline', {templateUrl: 'partials/view_timeline.html', controller: 'TimelinesCtrl'}).
        when('/timelines/:timelineId', {templateUrl: 'partials/view_timeline.html', controller: 'TimelineViewCtrl'})

	}]);

function authentication($rootScope, $location) {
	if ( !$rootScope.userRole ) {
		// no logged user, redirect to login form
		$location.path( "/login" );
	}
}

historicalTimelineApp.run( function($rootScope, $location, $cookieStore) {
	$rootScope.$location = $location;
	$rootScope.userRole = $cookieStore.get('userRole');
	authentication($rootScope, $location);
    // register listener to watch route changes
    $rootScope.$on('$routeChangeStart', function(event, next, current) {
    	authentication($rootScope, $location);
    	
    	console.log(next, current);
    	console.log(next.$$route.controller, current.$$route.controller);
    	if (current && next.$$route.controller !== current.$$route.controller) {
    		// dismiss alerts
    		$rootScope.errorAlert = '';
    		$rootScope.successAlert = '';
    	}
    });
});