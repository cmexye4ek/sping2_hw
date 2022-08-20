angular.module('market-app').controller('registrationController', function ($scope, $http, $rootScope, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    $scope.registerNewUser = function () {
        if ($scope.newUser == null) {
            alert("Fields cannot be empty");
            return;
        }
        $http.post(contextPath + '/register', $scope.newUser)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webMarketUser = {username: $scope.newUser.username, token: response.data.token};
                    $scope.newUser = null;
                }
                alert("Registration successful");
                    $location.path('/');
                }, function failCallback(response) {
                    alert(response.data.message);
                }
            );
    }

});
