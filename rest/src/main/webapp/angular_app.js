'use strict';

let historicalTimelineApp = angular.module('historicalTimelineApp', ['ngRoute', 'controllers']);
let controllers = angular.module('controllers', []);

historicalTimelineApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/groups', {templateUrl: 'partials/groups.html', controller: 'GroupsCtrl'}).
        when('/new_group', {templateUrl: 'partials/new_group.html', controller: 'NewGroupCtrl'})
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