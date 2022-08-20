angular.module('market-app').controller('productAddController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    $scope.addNewProduct = function () {
        if ($scope.newProduct == null) {
            alert("Fields cannot be empty");
            return;
        }
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function successCallback(response) {
                $scope.newProduct = null;
                alert("Product created successful");
                $location.path('/market');
                }, function failCallback(response) {
                    alert(response.data.message);
                }
            );
    };
});
