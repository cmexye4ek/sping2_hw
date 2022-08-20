angular.module('market-app').controller('productEditorController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    $scope.prepareProductForUpdate = function () {
        $http.get(contextPath + '/products/' + $routeParams.productId)
            .then(function successCallback(response) {
                    $scope.updated_product = response.data;
                }, function failCallback(response) {
                    alert(response.data.message);
                    $location.path('/market');
                }
            );
    }

    $scope.updateProduct = function () {
        $http.put(contextPath + '/products/', $scope.updated_product)
            .then(function successCallback(response) {
                    $scope.updated_product = null;
                    alert("Product updated successful");
                    $location.path('/market');
                }, function failCallback(response) {
                    alert(response.data.message);
                }
            );
    }

});
