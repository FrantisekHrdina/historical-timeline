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

controllers.controller('GroupsCtrl', function ($scope, $http, $rootScope, $location) {
	loadGroups($http, $scope);
	
	$scope.createGroup = function (group) {
		console.log('create new group');
		$scope.group = null;
		$location.path('new_group');
	}
	
	$scope.updateGroup = function (group) {
		console.log('update group with id=' + group.id + ' (' + group.name + ')');
		console.log(group);
		$rootScope.group = group;
		$location.path('new_group');
	}
	
	$scope.deleteGroup = function (group) {
		console.log('delete group with id=' + group.id + ' (' + group.name + ')');
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
	console.log('group form');
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