'use strict';

let historicalTimelineApp = angular.module('historicalTimelineApp', ['ngRoute', 'controllers']);
let controllers = angular.module('controllers', []);

historicalTimelineApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/groups', {templateUrl: 'partials/groups.html', controller: 'GroupsCtrl'}).
        when('/new_group', {templateUrl: 'partials/new_group.html', controller: 'NewGroupCtrl'})
	}]);

function loadGroups($http, $scope) {
	console.log('loading groups');
	$http.get('groups').then(function (response) {
		$scope.groups = response.data;
	});
}

controllers.controller('GroupsCtrl', function ($scope, $http, $rootScope) {
	loadGroups($http, $scope);
	
	$scope.deleteGroup = function (group) {
		console.log('deleting group with id=' + group.id + ' (' + group.name + ')');
		$http({
			method: 'DELETE',
			url: 'groups/' + group.id
		}).then(function success(response) {
			console.log('deleted group ' + group.name);
			$rootScope.successAlert = 'Seminar group "' + group.name + '" was deleted.';
			loadGroups($http, $scope);
		}, function error(response) {
			console.log('could not delete group ' + group.name);
			$scope.errorAlert = 'Could not delete seminar group.';
		});
	};

});

controllers.controller('NewGroupCtrl', function ($scope, $http, $rootScope, $location) {
	console.log('new group form');
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
			console.log('created group ' + createdGroup.name)
			$rootScope.successAlert = 'A new seminar group "' + createdGroup.name + '" was created';
			$location.path('groups')
		}, function error(response) {
			$scope.errorAlert = 'Cannot create group.';
		})
	};
});
