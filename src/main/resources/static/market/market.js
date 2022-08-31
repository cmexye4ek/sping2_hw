angular.module('market-app').controller('marketController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/market/api/v1';
    let currentPageIndex = 1;
    var stompClient = null;
    $("#getPriceAlert").hide();
    $("#loading").hide();

    $scope.getProductsPage = function (pageIndex = 1) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                page: pageIndex
            }
        }).then(function (response) {
            console.log(response);
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.nextPage = function () {
        currentPageIndex++;
        if (currentPageIndex > $scope.productsPage.totalPages) {
            currentPageIndex = $scope.productsPage.totalPages;
        }
        $scope.getProductsPage(currentPageIndex);
    }

    $scope.prevPage = function () {
        currentPageIndex--;
        if (currentPageIndex < 1) {
            currentPageIndex = 1;
        }
        $scope.getProductsPage(currentPageIndex);
    }

    $scope.navToProductEditor = function (productId) {
        $location.path('/product_editor/' + productId);
    }

    $scope.addToCart = function (product) {
        $http.post(contextPath + '/cart', product, {params: $localStorage.GuestUuid})
            .then(function successCallback(response) {
                    product = null;
                    alert("Product added to cart successful");
                }, function failCallback(response) {
                    console.log(response);
                    alert(response.data.message);
                }
            );
    }

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/products/' + productId)
            .then(function successCallback(response) {
                    alert("Product deleted successful");
                    $scope.getProductsPage()
                }, function failCallback(response) {
                    console.log(response);
                    alert(response.data.message);
                }
            );
    };

    function isConnected(connected) {
        $("#getPriceButton").prop("disabled", connected);
        if (connected) {
            $("#loading").show();
        } else {
            $("#getPriceButton").hide();
            $("#getPriceAlert").show();
            $("#loading").hide();
        }
    }

    $scope.wsConnect = function () {
        var socket = new SockJS('/market/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            isConnected(true);
            sendPriceRequest();
            console.log('WebSocket connected, price request sent \n' + frame);
            stompClient.subscribe('/topic/price', function (wsMessage) {
                $scope.priceUrl = contextPath + '/files/price/' + JSON.parse(wsMessage.body).content;
                window.open($scope.priceUrl); //убогое решение но сделать изящнее не получилось
                disconnect();
            });
        });
    }

    $scope.subscribe = function () {

    }

    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        isConnected(false);
        console.log("WebSocket Disconnected");
    }

    function sendPriceRequest() {
        stompClient.send("/app/price");
    }

});
