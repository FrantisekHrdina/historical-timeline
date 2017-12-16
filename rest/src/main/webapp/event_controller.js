let event = angular.module('event', []);

function loadEvents($http, $scope) {
    console.log('loading events');
    $http.get('events').then(function (response) {
        $scope.events = response.data;
    });
}

event.controller('EventsCtrl', function ($scope, $http, $rootScope, $location) {
    loadEvents($http, $scope);

    $scope.createEvent = function (event) {
        console.log('create new event');
        $scope.event = null;
        $location.path('new_event');
    }

    $scope.updateEvent = function (event) {
        console.log('update event with id=' + event.id + ' (' + event.name + ')');
        console.log(event);
        $rootScope.event = event;
        $location.path('new_event');
    }

    $scope.deleteEvent = function (event) {
        console.log('delete event with id=' + event.id + ' (' + event.name + ')');
        $http({
            method: 'DELETE',
            url: 'events/' + event.id
        }).then(function success(response) {
            console.log('deleted event ' + event.name);
            $rootScope.successAlert = 'Event "' + event.name + '" was deleted.';
            loadEvents($http, $scope);
        }, function error(response) {
            console.log('could not delete event ' + event.name);
            $scope.errorAlert = 'Could not delete event.';
        });
    };

});

event.controller('NewEventCtrl', function ($scope, $http, $rootScope, $location) {
    console.log('event form');
    $scope.create = function (event) {
        $http({
            method: 'POST',
            url: 'events/create',
            data: event
        }).then(function success(response) {
            let createdEvent = response.data;
            console.log('created event ' + createdEvent.name)
            $rootScope.successAlert = 'A new event "' + createdEvent.name + '" was created';
            $location.path('events')
        }, function error(response) {
            $scope.errorAlert = 'Cannot create event.';
        })
    };
});