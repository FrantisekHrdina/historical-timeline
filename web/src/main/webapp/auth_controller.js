let auth = angular.module('auth', []);

auth.controller('LoginController', function ($scope, $rootScope, $http, $location, $cookieStore) {
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
          $rootScope.userRole = response.data.role;
          $rootScope.authorizedTimelines = response.data.timelines;
          $rootScope.username = response.data.username;
          $cookieStore.put('userRole', response.data.role);
          $cookieStore.put('authorizedTimelines', response.data.timelines);
          $cookieStore.put('username', response.data.username);
          $location.path('dashboard');
      }, function error(response) {
          console.log('error signing in user ' + credentials.login);
          $rootScope.errorAlert = 'Could not sign in. Please check username and password.';
      });
	  
  };
})