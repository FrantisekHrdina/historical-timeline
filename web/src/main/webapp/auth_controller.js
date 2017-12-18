let auth = angular.module('auth', []);

auth.controller('LoginController', function ($scope, $rootScope, $http) {
  $scope.credentials = {
    login: '',
    password: ''
  };
  
  $scope.login = function (credentials) {
	  $http({
          method: 'POST',
          url: restInterface + '/auth',
          data: credentials
      }).then(function success(response) {
          console.log('signed in user using ' + credentials.login);
          $rootScope.userRole = response.data;
          console.log('user role', $rootScope.userRole);
      }, function error(response) {
          console.log('error signing in user ' + credentials.login);
          $scope.errorAlert = 'Could not sign in. Please check username and password.';
      });
	  
  };
})