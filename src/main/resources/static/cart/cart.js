angular.module('market-app').controller('cartController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    $scope.getCartList = function () {
        $http.get(contextPath + '/cart')
            .then(function successCallback(response) {
                    $scope.products = response;
                    console.log(response);
                }
            );
    };

    $scope.removeFromCart = function (productId) {
        $http.delete(contextPath + '/cart/' + productId)
            .then(function successCallback(response) {
                    alert("Product successful remove from cart");
                    $scope.getCartList();
                }, function failCallback(response) {
                    alert(response.data.message);
                }
            );
    };

});
