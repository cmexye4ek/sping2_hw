(function () {
    angular
        .module('market-app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/market', {
                templateUrl: 'market/market.html',
                controller: 'marketController'
            })
            .when('/product_editor/:productId', {
                templateUrl: 'product_editor/registration.html',
                controller: 'productEditorController'
            })
            .when('/product_add', {
                templateUrl: 'product_add/product_add.html',
                controller: 'productAddController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.webMarketUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webMarketUser.token;
        }
    }

})();

angular.module('market-app').controller('indexController', function ($rootScope, $scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    $rootScope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    let jwtClaims = response.data.token.split('.')[1];
                    $scope.decodedJwtClaims = window.atob(jwtClaims);
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webMarketUser = {
                        username: $scope.user.username,
                        token: response.data.token
                    };
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
                alert(response.data.messages);
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        return !!$localStorage.webMarketUser;
    };

    $rootScope.isUserAdmin = function () {
        return $rootScope.isUserLoggedIn() && $scope.decodedJwtClaims.includes("ROLE_ADMIN");
    };

    $rootScope.isUserManager = function () {
        return $rootScope.isUserLoggedIn() && $scope.decodedJwtClaims.includes("ROLE_MANAGER");
    };

    $scope.navToRegisterPage = function () {
        $location.path('/registration');
    }
});
