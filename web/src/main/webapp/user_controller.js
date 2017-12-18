let user = angular.module('user', []);

function loadUsers($http, $scope) {
    console.log('loading users');
    $http.get('users').then(function (response) {
        $scope.users = response.data;
    });
}

user.controller('UsersCtrl', function ($scope, $http, $rootScope, $location) {
    loadUsers($http, $scope);
    
    $scope.createUser = function (user) {
        console.log('create new user');
        $scope.user = null;
        $location.path('new_user');
    }

    $scope.updateUser = function (user) {
        console.log('update user with id=' + user.id + ' (' + user.forename + ')');
        console.log(user);
        $rootScope.user = user;
        $location.path('new_user');
    }

    $scope.deleteUser = function (user) {
        console.log('delete user with id=' + user.id + ' (' + user.forename + ')');
        $http({
            method: 'DELETE',
            url: 'users/' + user.id
        }).then(function success(response) {
            console.log('deleted user ' + user.forename);
            $rootScope.successAlert = 'User "' + user.forename + '" was deleted.';
            loadUsers($http, $scope);
        }, function error(response) {
            console.log('could not delete user ' + user.forename);
            $scope.errorAlert = 'Could not delete user.';
        });
    };

});


