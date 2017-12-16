let event = angular.module('event', []);

event.controller('EventsCtrl', function ($scope, $http) {
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

event.controller('NewEventCtrl', function ($scope, $http, $rootScope, $location) {
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