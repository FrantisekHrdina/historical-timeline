let group = angular.module('group', []);

function loadGroups($http, $scope) {
	console.log('loading groups');
	$http.get('groups').then(function (response) {
		$scope.groups = response.data;
	});
}

group.controller('GroupsCtrl', function ($scope, $http, $rootScope, $location) {
	loadGroups($http, $scope);
	
	$scope.createGroup = function () {
		console.log('create new group');
		$rootScope.group = null;
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

group.controller('NewGroupCtrl', function ($scope, $http, $rootScope, $location) {
	console.log('group form');
	$scope.create = function (group) {
		if (!group.id) {
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
		} else {
			$http({
				method: 'PUT',
				url: 'groups/' + group.id,
				data: group
			}).then(function success(response) {
				let createdGroup = response.data;
				console.log('updated group ' + createdGroup.name)
				$rootScope.successAlert = 'Seminar group "' + createdGroup.name + '" was updated';
				$location.path('groups')
			}, function error(response) {
				$scope.errorAlert = 'Cannot update group.';
			})
		}
	};
});