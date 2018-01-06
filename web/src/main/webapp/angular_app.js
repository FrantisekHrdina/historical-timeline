'use strict';

let historicalTimelineApp = angular.module('historicalTimelineApp', [
    'ngRoute',
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

historicalTimelineApp.run( function($rootScope, $location) {
    // register listener to watch route changes
    $rootScope.$on( "$routeChangeStart", function(event, next, current) {
      if ( !$rootScope.userRole ) {
        // no logged user, we should be going to #login
        if ( next.templateUrl == "partials/login_form.html" ) {
          // already going to #login, no redirect needed
        } else {
          // not going to #login, we should redirect now
          $location.path( "/login" );
        }
      } else {
    	  if ( $rootScope.userRole != "teacher" && next.templateUrl == "partials/users.html" ) {
    		  $location.path("/login");
    	  }
      }         
    });
});
