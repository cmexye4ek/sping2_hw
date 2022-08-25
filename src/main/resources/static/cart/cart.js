angular.module('market-app').controller('cartController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    $scope.getCartList = function () {
        $http.get(contextPath + '/cart', {params: $localStorage.GuestUuid})
            .then(function successCallback(response) {
                    $scope.cart = response.data;
                    console.log(response);
                }, function failCallback(response) {
                    console.log(response.data);
                }
            );
    };

    $scope.removeFromCart = function (productId) {
        $http.delete(contextPath + '/cart/' + productId, {params: $localStorage.GuestUuid})
            .then(function successCallback(response) {
                    alert("Product successful remove from cart");
                    $scope.getCartList();
                }, function failCallback(response) {
                    alert(response.data.message);
                }
            );
    };

    $scope.decrementAmount = function (product) {
        $http.put(contextPath + '/cart', product, {params: $localStorage.GuestUuid})
            .then(function successCallback(response) {
                    $scope.getCartList();
                }, function failCallback(response) {
                    console.log(response.data);
                    alert(response.data.message);
                }
            );
    };

    $scope.incrementAmount = function (product) {
        $http.post(contextPath + '/cart', product, {params: $localStorage.GuestUuid})
            .then(function successCallback(response) {
                    $scope.getCartList();
                    product = null;
                }, function failCallback(response) {
                    console.log(response);
                    alert(response.data.message);
                }
            );
    }


});
